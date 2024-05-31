package sijang.sijanggaza.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.dto.order.OrderDto;
import sijang.sijanggaza.dto.user.UserDto;
import sijang.sijanggaza.dto.user.request.UserRequestDTO;
import sijang.sijanggaza.dto.user.response.CreateUserResponseDTO;
import sijang.sijanggaza.dto.user.response.UserMypageResponseDTO;
import sijang.sijanggaza.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    /**
     * 회원 전체 조회
     */
    @GetMapping("/api/v1/users")
    public List<UserDto> findUserV1() {
        List<SiteUser> userList = userService.findAll();
        List<UserDto> collect = userList.stream()
                .map(u -> new UserDto(u.getId(), u.getUsername(), u.getPassword(), u.getEmail()))
                .collect(toList());
        return collect;
    }

    /**
     * 회원 가입
     */
    @PostMapping("/api/v1/createUser")
    public CreateUserResponseDTO createUser(@RequestBody @Valid UserRequestDTO request){
        SiteUser newUser = new SiteUser();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(request.getPassword());
        newUser.setEmail(request.getEmail());
        newUser.setStatus(request.getStatus());
        SiteUser user = userService.create(newUser.getUsername(), newUser.getPassword(), newUser.getEmail(), newUser.getStatus());
        return new CreateUserResponseDTO(user.getId()); //생성자에 따라 UserRepsonseDto 오버라이딩
    }

    /**
     * 마이페이지
     */
    @GetMapping("/api/v1/userPage")
    public UserMypageResponseDTO userPage(@RequestParam("username") String username) {
        SiteUser user = this.userService.getUser(username);
        List<OrderDto> orderDtoList = user.getOrderList().stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
        return new UserMypageResponseDTO(user.getId(), user.getUsername(), user.getEmail(), orderDtoList);
    }
}
