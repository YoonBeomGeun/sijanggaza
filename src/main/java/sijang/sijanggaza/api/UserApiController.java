package sijang.sijanggaza.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.dto.order.OrderDto;
import sijang.sijanggaza.dto.user.UserDto;
import sijang.sijanggaza.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping("/api/v1/users")
    public List<UserDto> findUserV1() {
        List<SiteUser> userList = userService.findAll();
        List<UserDto> collect = userList.stream()
                .map(u -> new UserDto(u.getId(), u.getUsername(), u.getPassword(), u.getEmail()))
                .collect(toList());
        return collect;
    }
}
