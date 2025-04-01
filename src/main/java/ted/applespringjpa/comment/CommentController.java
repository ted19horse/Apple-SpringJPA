package ted.applespringjpa.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ted.applespringjpa.member.MyUserDetailsService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
@PreAuthorize("isAuthenticated()") // 이 컨트롤러의 모든 메서드에 인증 필요
public class CommentController {
    private final CommentRepository commentRepository;

    @PostMapping("/add")
    public String addComment(@ModelAttribute Comment comment, Authentication auth) {
        MyUserDetailsService.CustomUser result = (MyUserDetailsService.CustomUser) auth.getPrincipal();
        comment.setUsername(auth.getName());
        commentRepository.save(comment);
        return "redirect:/detail/" + comment.getParentId();
    }
}
