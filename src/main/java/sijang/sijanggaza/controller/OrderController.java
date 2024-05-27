package sijang.sijanggaza.controller;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Item;
import sijang.sijanggaza.domain.Order;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.dto.BoardDTO;
import sijang.sijanggaza.exception.NotEnoughStockException;
import sijang.sijanggaza.service.BoardService;
import sijang.sijanggaza.service.ItemService;
import sijang.sijanggaza.service.OrderService;
import sijang.sijanggaza.service.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final BoardService boardService;
    private final UserService userService;
    private final ItemService itemService;

    /**
     * 주문 생성
     */
    @PostMapping("/create/{id}")
    public String createOrder(@PathVariable("id") Integer id, @Valid OrderForm orderForm, BindingResult orderResult,
                              CommentForm commentForm, Principal principal, Model model, RedirectAttributes redirectAttributes) {
        Board board = this.boardService.getBoard(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        Item item = this.itemService.selectedItem(Math.toIntExact(orderForm.getId()));

        if(orderResult.hasFieldErrors("quantity")) {
            model.addAttribute("errorMessage", "수량은 1 ~ 99 사이의 숫자만 입력 가능합니다.");
            model.addAttribute("board", board);
            return "board_itemDetail";
        } else if (item.getStockQuantity() < orderForm.getQuantity()) {
            model.addAttribute("errorMessage", "재고 수량이 부족합니다.");
            model.addAttribute("board", board);
            return "board_itemDetail";
        }

        this.orderService.create(siteUser, item, orderForm.getQuantity());

        return String.format("redirect:/board/itemDetail/%s", board.getId());
    }


    /**
     * 주문 취소
     */
    @PostMapping("/{id}/cancel")
    public String orderCancel(@PathVariable("id") Integer id) {
        System.out.println("Cancelling order with ID: " + id);
        this.orderService.orderCancel(id);
        return "redirect:/user/mypage";
    }
}
