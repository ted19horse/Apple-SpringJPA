package ted.applespringjpa.member;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/sign-in")
    public String signin(Authentication auth) {
        System.out.println("checking sign-in");
        if(auth.isAuthenticated())
            return "redirect:/list";
        return "sign-in.html";
    }

    @PostMapping("/join")
    public ModelAndView join(Member member) throws Exception {
        ModelAndView mav = new ModelAndView("redirect:/list");
        memberService.join(member);
        return mav;
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/my-page")
    public String myPage(Authentication auth) {
        /* System.out.println(auth);
         * UsernamePasswordAuthenticationToken [
         *   Principal=org.springframework.security.core.userdetails.User [
         *       Username=ted,
         *       Password=[PROTECTED],
         *       Enabled=true,
         *       AccountNonExpired=true,
         *       CredentialsNonExpired=true,
         *       AccountNonLocked=true,
         *       Granted Authorities=[일반유저]
         *   ],
         *   Credentials=[PROTECTED],
         *   Authenticated=true,
         *   Details=WebAuthenticationDetails [
         *       RemoteIpAddress=127.0.0.1,
         *       SessionId=75BAEF0B16CD259D24FE1C60895CF01A
         *   ],
         *   Granted Authorities=[일반유저]
         * ]
         */
        System.out.println(auth.getName()); // ted
        System.out.println(auth.getAuthorities()); // [일반유저]
        System.out.println(auth.getAuthorities().contains(
                new SimpleGrantedAuthority("일반유저")
        )); // true

        return "my-page.html";
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler() {
        System.out.println("에러를 캐치했음!");
        return ResponseEntity.status(400).body("뭔가 문제 있음!");
    }
}
