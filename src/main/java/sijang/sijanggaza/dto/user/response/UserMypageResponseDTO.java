package sijang.sijanggaza.dto.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import sijang.sijanggaza.domain.Order;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.dto.order.OrderDto;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserMypageResponseDTO {
    private Long id;
    private String username;
    private String email;
    private List<OrderDto> orderDtoList;

    public UserMypageResponseDTO(Long id, String username, String email, List<OrderDto> orderDtoList) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.orderDtoList = orderDtoList;
    }

}
