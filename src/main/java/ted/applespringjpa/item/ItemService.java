package ted.applespringjpa.item;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import ted.applespringjpa.comment.Comment;
import ted.applespringjpa.comment.CommentRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;

    public void saveItem(Map<String, Object> formData, String username) {
        Item newItem = new Item();
        newItem.setUsername(username);
        newItem.setTitle((String) formData.get("title"));
        newItem.setPrice(Integer.parseInt((String) formData.get("price")));
        newItem.setImgUrl((String) formData.get("img_url"));
        itemRepository.save(newItem);
    }

    public ModelAndView detail(String id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Item> result = itemRepository.findById(Long.parseLong(id));
        if (result.isPresent()) {
            System.out.println(result.get());
            List<Comment> comments = commentRepository.findByParentId(Long.parseLong(id));
            modelAndView.addObject("item", result.get());
            modelAndView.addObject("comments", comments);
            modelAndView.setViewName("detail.html");
        } else {
            modelAndView.setViewName("redirect:/list");
        }
        return modelAndView;
    }

    public ModelAndView modify(String id) {
        ModelAndView modelAndView = new ModelAndView();
        Optional<Item> result = itemRepository.findById(Long.parseLong(id));
        if (result.isPresent()) {
            modelAndView.addObject("item", result.get());
            modelAndView.setViewName("modify.html");
        } else {
            modelAndView.setViewName("redirect:/list");
        }
        return modelAndView;
    }

    public ModelAndView update(Map<String, Object> formData) {
        ModelAndView modelAndView = new ModelAndView();

        Long id = (Long) formData.get("id");
        String title = (String) formData.get("title");
        int price = Integer.parseInt((String) formData.get("price"));

        boolean check = true;
        if (title.length() > 100)
            check = false;
        else if (price < 0)
            check = false;

        if (!check)
            modelAndView.setViewName("redirect:/list");
        else {
            Item item = itemRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Item Not Found"));
            item.setTitle(title);
            item.setPrice(price);
            itemRepository.save(item);

            modelAndView.addObject("item", item);
            modelAndView.setViewName("redirect:/detail/" + item.getId());
        }

        return modelAndView;
    }
}
