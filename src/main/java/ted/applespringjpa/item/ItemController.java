package ted.applespringjpa.item;

import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private static final Log log = LogFactory.getLog(ItemController.class);
    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @GetMapping("/list")
    public String list(Model model) {
        List<Item> result = itemRepository.findAll();
        model.addAttribute("items", result);

        return "list.html";
    }

    @PreAuthorize("isAuthenticated()")
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
}
