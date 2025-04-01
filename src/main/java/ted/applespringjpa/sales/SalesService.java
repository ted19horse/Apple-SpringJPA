package ted.applespringjpa.sales;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ted.applespringjpa.item.Item;
import ted.applespringjpa.item.ItemRepository;
import ted.applespringjpa.member.Member;
import ted.applespringjpa.member.MyUserDetailsService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SalesService {
    private final SalesRepository salesRepository;
    private final ItemRepository itemRepository;

    public void order(Authentication auth, Long idx, int count) {
        Sales sales = new Sales();
        Optional<Item> item = itemRepository.findById(idx);
        if(item.isPresent()) {
            sales.setItemName(item.get().getTitle());
            sales.setPrice(item.get().getPrice());
            sales.setCount(count);
            MyUserDetailsService.CustomUser customUser = (MyUserDetailsService.CustomUser) auth.getPrincipal();

            // sales.setMemberId(customUser.getId());
            var member = new Member();
            member.setId(customUser.getId());
            sales.setMember(member);

            salesRepository.save(sales);
        }
    }
}
