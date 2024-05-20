package sijang.sijanggaza.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemBoardForm {

    @NotEmpty(message="상품이름은 필수입니다.")
    @Size(max=200)
    private String name;

    @NotNull(message="가격은 필수입니다.")
    private int price;

    @NotNull(message="수량은 필수입니다.")
    @Min(value = 1, message = "수량은 최소 1개 이상이어야 합니다.")
    private int stockquantity;

}
