package sijang.sijanggaza.dto.item;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Item;
import sijang.sijanggaza.domain.Order;
import sijang.sijanggaza.dto.board.BoardDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    private Long id;
    private String iName;
    private int price;
    private int stockQuantity;

    public ItemDto(Item item) {
        id = item.getId();
        iName = item.getIName();
        price = item.getPrice();
        stockQuantity = item.getStockQuantity();
    }
}
