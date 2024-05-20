package sijang.sijanggaza.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Comment;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.service.BoardService;
import sijang.sijanggaza.service.ItemService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemBoardController {

    private final ItemService itemService;
    private final BoardService boardService;

    /*@PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createComment(@PathVariable("id") Integer id, @Valid CommentForm commentForm,
                                BindingResult result, Principal principal, Model model) {
        Board board = this.boardService.getBoard(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if(result.hasErrors()) {
            model.addAttribute("board", board);
            return "board_detail";
        }
        Comment comment = this.commentService.create(board, commentForm.getContent(), siteUser);
        return String.format("redirect:/board/detail/%s#comment_%s", comment.getBoard().getId(), comment.getId());
    }*/

    /*@PostMapping("/create/{id}")
    public String createComment(@PathVariable("id") Integer id, @Valid ItemBoardForm itemBoardForm,
                                BindingResult result, Principal principal, Model model) {
        Board board = this.boardService.getBoard(id);
        if(result.hasErrors()) {
            model.addAttribute("board", board);
            return "board_itemForm";
        }
        Comment comment = this.commentService.create(board, commentForm.getContent(), siteUser);
        return String.format("redirect:/board/detail/%s#comment_%s", comment.getBoard().getId(), comment.getId());
    }*/

}
