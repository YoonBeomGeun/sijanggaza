package sijang.sijanggaza.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.dto.OrderDto;
import sijang.sijanggaza.dto.user.UserDto;
import sijang.sijanggaza.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @GetMapping("/api/v1/users")
    public List<UserDto> findUserV1() {
        List<SiteUser> findUsers = userService.findAll();

        List<UserDto> collect = findUsers.stream()
                .map(u -> new UserDto(
                        u.getId(),
                        u.getUsername(),
                        u.getPassword(),
                        u.getEmail(),
                        u.getOrderList().stream()
                                .map(o -> new OrderDto(o.getId(), o.getOrderPrice(), o.getQuantity(), o.getOrderDate(), o.getItem(), o.getStatus()))
                                .collect(Collectors.toList()),
                        u.getStatus()))
                .collect(Collectors.toList());
        return collect;
    }
}
