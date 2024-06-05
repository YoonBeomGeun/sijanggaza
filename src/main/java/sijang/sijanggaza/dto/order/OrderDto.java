package sijang.sijanggaza.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import sijang.sijanggaza.domain.Order;
import sijang.sijanggaza.domain.OrderStatus;
import sijang.sijanggaza.dto.item.ItemDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class OrderDto {

    private Long id;
    private int orderPrice;
    private int quantity;
    private LocalDateTime orderDate;
    private ItemDto itemDto;
    private OrderStatus status; //주문 상태(ORDER, CANCEL)

    public OrderDto(Order order) {
        id = order.getId();
        orderPrice = order.getOrderPrice();
        quantity = order.getQuantity();
        orderDate = order.getOrderDate();
        itemDto = new ItemDto(order.getItem());
        status = order.getStatus();
    }
}
