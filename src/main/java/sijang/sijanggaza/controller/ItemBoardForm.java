package sijang.sijanggaza.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import sijang.sijanggaza.domain.Item;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ItemBoardForm {

    @NotEmpty(message="제목은 필수입니다.")
    @Size(max=200)
    private String title;

    @NotEmpty(message="내용은 필수입니다.")
    private String content;

    @Valid
    private List<ItemForm> items = new ArrayList<>();

    @Getter
    @Setter
    public static class ItemForm {
        @NotEmpty(message="상품 이름은 필수입니다.")
        @Size(max=200)
        private String name;

        @NotNull(message="가격은 필수입니다.")
        private int price;

        @NotNull(message="수량은 필수입니다.")
        @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
        private int stockquantity;

        public static ItemForm fromItem(Item item) {
            ItemForm form = new ItemForm();
            form.setName(item.getName());
            form.setPrice(item.getPrice());
            form.setStockquantity(item.getStockQuantity());
            return form;
        }
    }
    public static List<ItemForm> fromItems(List<Item> items) {
        List<ItemForm> itemForms = new ArrayList<>();
        for (Item item : items) {
            itemForms.add(ItemForm.fromItem(item));
        }
        return itemForms;
    }

}
