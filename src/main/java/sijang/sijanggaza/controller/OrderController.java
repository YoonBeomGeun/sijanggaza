package sijang.sijanggaza.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Item;
import sijang.sijanggaza.domain.Order;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.exception.NotEnoughStockException;
import sijang.sijanggaza.service.BoardService;
import sijang.sijanggaza.service.ItemService;
import sijang.sijanggaza.service.OrderService;
import sijang.sijanggaza.service.UserService;

import java.security.Principal;
import java.util.List;

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
                              CommentForm commentForm, Principal principal, Model model) {
        Board board = this.boardService.getBoard(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if(orderResult.hasErrors()) {
            model.addAttribute("board", board);
            return "board_itemDetail";
        }

        try {
            Item item = this.itemService.selectedItem(Math.toIntExact(orderForm.getId()));
            this.orderService.create(siteUser, item, orderForm.getQuantity());
        } catch (NotEnoughStockException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("board", board);
            return "board_itemDetail";
        }

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
