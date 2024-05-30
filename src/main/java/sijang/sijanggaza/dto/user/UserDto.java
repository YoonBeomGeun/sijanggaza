package sijang.sijanggaza.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sijang.sijanggaza.domain.Order;
import sijang.sijanggaza.domain.UserStatus;
import sijang.sijanggaza.dto.OrderDto;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private List<OrderDto> orderList = new ArrayList<>();
    private UserStatus status;
}
