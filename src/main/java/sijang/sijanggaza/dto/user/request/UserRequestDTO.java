package sijang.sijanggaza.dto.user.request;

import lombok.Data;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.domain.UserStatus;
import sijang.sijanggaza.dto.order.OrderDto;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserRequestDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private List<OrderDto> orderList;
    private UserStatus status;

    public UserRequestDTO(SiteUser siteUser) {
        id = siteUser.getId();
        username = siteUser.getUsername();
        password = siteUser.getPassword();
        email = siteUser.getEmail();
        /*orderList = siteUser.getOrderList();*/
        orderList = siteUser.getOrderList().stream()
                .map(order -> new OrderDto(order))
                .collect(Collectors.toList());
        status = siteUser.getStatus();
    }
}
