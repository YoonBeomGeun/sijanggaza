package sijang.sijanggaza.dto;

import lombok.Data;

@Data
public class ItemDTO {
    private Long id;
    private String iName;
    private int price;
    private int stockQuantity;
}
