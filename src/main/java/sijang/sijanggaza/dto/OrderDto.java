package sijang.sijanggaza.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sijang.sijanggaza.domain.Item;
import sijang.sijanggaza.domain.OrderStatus;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.dto.user.UserDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;

    private int orderPrice;
    private int quantity;
    private LocalDateTime orderDate;
    private Item item;
    private UserDto userDto;
    private OrderStatus status; //주문 상태(ORDER, CANCEL)

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
        userDto.getOrderList().add(this);
    }
}
