package ted.applespringjpa.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var result = memberRepository.findByUsername(username);
        if(result.isEmpty()) {
            throw new UsernameNotFoundException("그런 아이디 없음");
        }
        var user = result.get();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("일반유저"));
        // if 조건문으로 해당 유저가 어떤 권한을 가지는지 세팅할 수 있음.

        // return new User(user.getUsername(), user.getPassword(), authorities);
        return new CustomUser(user.getUsername(), user.getPassword(), authorities, user.getId(), user.getDisplayName());
    }

    public static class CustomUser extends User {
        @Getter
        final private Long id;
        @Getter
        final private String displayName;
        public CustomUser(String username,
                          String password,
                          Collection<? extends GrantedAuthority> authorities,
                          Long id,
                          String displayName
        ) {
            super(username, password, authorities);
            this.id = id;
            this.displayName = displayName;
        }
    }

}