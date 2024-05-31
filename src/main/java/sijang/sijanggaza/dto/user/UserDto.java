package sijang.sijanggaza.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sijang.sijanggaza.domain.Order;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.domain.UserStatus;
import sijang.sijanggaza.dto.order.OrderDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
}
