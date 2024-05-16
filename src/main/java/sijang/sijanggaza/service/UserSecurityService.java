package sijang.sijanggaza.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.domain.UserRole;
import sijang.sijanggaza.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override //ceo와 일반 user 분리 시 볼 것.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { //UserDetailsService는 loadUserByUsername 메서드를 구현하도록 강제하는 인터페이스
        Optional<SiteUser> _siteUser = this.userRepository.findByusername(username);
        if(_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        SiteUser siteUser = _siteUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
        //스프링 시큐리티는 loadUserByUsername 메서드에 의해 리턴된 User 객체의 비밀번호가 사용자로부터 입력받은 비밀번호와 일치하는지 검사하는 기능을 내부에 가지고 있음.
    }

}
