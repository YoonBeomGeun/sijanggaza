package sijang.sijanggaza.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sijang.sijanggaza.exception.DataNotFoundException;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.domain.UserStatus;
import sijang.sijanggaza.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SiteUser create(String username, String password, String email, UserStatus status) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); //Bean으로 등록한 PasswordEncoder 객체 사용
        user.setEmail(email);
        user.setStatus(status);
        this.userRepository.save(user);
        return user;
    }

    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = this.userRepository.findByusername(username);
        if(siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteuser not found.");
        }
    }

    public SiteUser findOne(Long id) {
        Optional<SiteUser> os = userRepository.findById(id);
        if(os.isPresent()) {
            return os.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
    }

    public List<SiteUser> findAll() {
        return this.userRepository.findAll();
    }
}
