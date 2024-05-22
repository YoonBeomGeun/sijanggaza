package sijang.sijanggaza.controller;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderForm {

    @NotEmpty(message = "상품을 선택해주세요.")
    String name;

    @NotNull(message = "수량을 입력해주세요.")
    @Min(value = 0)
    int count;
}