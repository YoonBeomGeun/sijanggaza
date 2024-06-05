package sijang.sijanggaza.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sijang.sijanggaza.domain.Item;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

    private Long id;
    private String iName;
    private Long boardId;
    private int price;
    private int stockQuantity;

    public ItemDto(Item item) {
        id = item.getId();
        iName = item.getIName();
        boardId = item.getBoard() != null ? item.getBoard().getId() : null;
        price = item.getPrice();
        stockQuantity = item.getStockQuantity();
    }
}
