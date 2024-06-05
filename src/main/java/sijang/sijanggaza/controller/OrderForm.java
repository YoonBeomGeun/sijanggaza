package sijang.sijanggaza.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderForm {

    @NotNull(message = "상품을 선택해주세요.")
    Long id;

    @NotNull(message = "수량을 입력해주세요.")
    @Min(value = 1, message = "수량을 1개 이상 선택해주세요.")
    @Max(value = 99, message = "최대 99개만 주문 가능합니다.")
    int quantity;
}
