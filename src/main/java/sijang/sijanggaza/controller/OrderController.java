package sijang.sijanggaza.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Item;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.service.BoardService;
import sijang.sijanggaza.service.OrderService;
import sijang.sijanggaza.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final BoardService boardService;
    private final UserService userService;

    @PostMapping("/createOrder/{id}")
    public String createOrder(@PathVariable("id") Integer id, @Valid OrderForm orderForm,
                              BindingResult result, Principal principal) {
        if(result.hasErrors()) {
            return "board_itemDetail";
        }
        Board board = this.boardService.getBoard(id);
        Item item = this.orderService.selectedItem(board, orderForm.getName());
        this.orderService.create(principal.getName(), item, orderForm.getCount());
        return "board_itemDetail";
    }
}
