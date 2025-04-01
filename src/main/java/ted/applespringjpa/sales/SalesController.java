package ted.applespringjpa.sales;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ted.applespringjpa.item.ItemRepository;
import ted.applespringjpa.member.MemberRepository;

import java.util.List;

@Controller
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
public class SalesController {
    private final SalesRepository salesRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final SalesService salesService;

    @GetMapping("/sales-list")
    public String getSalesList(Model model) {
//        var result = salesRepository.customFindAll();
//        var salesDto = new SalesDTO();
//        salesDto.setItemName(result.get(0).itemName);
//        salesDto.setPrice(result.get(0).price);
//        salesDto.setDisplayName(result.get(0).getMember().getDisplayName());
//        model.addAttribute("salesList", salesList);

//        List<Sales> salesList = salesRepository.customFindAll();
//        List<SalesDTO> salesDTOList = salesList.stream().map(sales -> {
//            SalesDTO dto = new SalesDTO();
//            dto.setItemName(sales.getItemName());
//            dto.setPrice(sales.getPrice());
//            dto.setDisplayName(sales.getMember().getDisplayName());
//            return dto;
//        }).toList();
//        model.addAttribute("salesList", salesDTOList);

        List<SalesDTO> salesDTOList = salesRepository.customFindAll();
        model.addAttribute("salesList", salesDTOList);
        return "sales-list.html";
    }

    @PostMapping("/order/{idx}")
    public String postOrder(Authentication auth, @PathVariable Long idx, @RequestParam int count) {
        salesService.order(auth, idx, count);
        return "redirect:/detail/"+idx;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler() {
        System.out.println("에러를 캐치했음!");
        return ResponseEntity.status(400).body("뭔가 문제 있음!");
    }
}
