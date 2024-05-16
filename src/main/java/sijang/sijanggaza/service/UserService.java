package sijang.sijanggaza.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.domain.UserStatus;
import sijang.sijanggaza.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public SiteUser create(String username, String password, String email) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); //Bean으로 등록한 PasswordEncoder 객체 사용
        user.setEmail(email);
        this.userRepository.save(user);
        return user;
    }
}
