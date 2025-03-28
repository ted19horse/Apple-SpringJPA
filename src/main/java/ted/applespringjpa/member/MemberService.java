package ted.applespringjpa.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(Member member) throws Exception {
        if(member.getUsername().length() < 3 || member.getPassword().length() < 4)
            throw new Exception("아이디나 비번 규칙이 안맞음.");

        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
//        try {
//            memberRepository.save(member);
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
    }
}
