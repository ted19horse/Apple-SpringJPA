package ted.applespringjpa.item;

import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()") // 이 컨트롤러의 모든 메서드에 인증 필요
public class ItemController {
    private static final Log log = LogFactory.getLog(ItemController.class);
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final S3Service s3Service;

//    @PreAuthorize("hasRole('일반유저')") // 이 메서드는 '일반유저' 역할이 필요
    @GetMapping("/list")
    public String list(Model model) {
        List<Item> result = itemRepository.findAll();
        model.addAttribute("items", result);

        return "list.html";
    }

    @GetMapping("/write")
    String write(Authentication auth) {
        log.info("User: " + auth.getName());
        log.info("User: " + auth.getPrincipal());
        return "write.html";
    }

    @PostMapping("/add")
    String writePost(Authentication auth, @RequestParam Map<String, Object> formData) {
        String username = auth.getName();
        itemService.saveItem(formData, username);
        return "redirect:/list";
    }

    @GetMapping("/detail/{id}")
    ModelAndView detail(@PathVariable String id) {
        return itemService.detail(id);
    }

    @GetMapping("/modify/{id}")
    ModelAndView modify(@PathVariable String id) {
        return itemService.modify(id);
    }

    @PostMapping("/update")
    ModelAndView updatePost(@RequestParam Map<String, Object> formData) {
        return itemService.update(formData);
    }

    @GetMapping("/list/page/{page}")
    public String getListPage(Model model, @PathVariable int page) {
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(page-1, 5));
        System.out.println(result.getTotalPages());
        System.out.println(result.hasNext());
        model.addAttribute("items", result);
        model.addAttribute("totalPages", result.getTotalPages());

        return "list.html";
    }

    @GetMapping("/search-list")
    String postSearch(Model model, @RequestParam String searchText, @RequestParam int page) {
        // var result = itemRepository.findAllByTitleContains(searchText);
        Page<Item> result = itemRepository.fullTextSearch(searchText, PageRequest.of(page-1, 5));
        System.out.println(result);
        model.addAttribute("items", result);
        model.addAttribute("totalPages", result.getTotalPages());
        model.addAttribute("searchText", searchText);

        return "search-list.html";
    }

    @GetMapping("/presigned-url")
    @ResponseBody
    String getURL(@RequestParam String filename){
        var result = s3Service.createPresignedUrl("test/" + filename);
        return result;
    }
}
