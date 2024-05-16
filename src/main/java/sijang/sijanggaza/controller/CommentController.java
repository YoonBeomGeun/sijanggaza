package sijang.sijanggaza.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.service.BoardService;
import sijang.sijanggaza.service.CommentService;
import sijang.sijanggaza.service.UserService;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final BoardService boardService;
    private final CommentService commentService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createComment(@PathVariable("id") Integer id, @Valid CommentForm commentForm,
                                BindingResult result, Principal principal, Model model) {
        Board board = this.boardService.getBoard(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if(result.hasErrors()) {
            model.addAttribute("board", board);
            return "board_detail";
        }
        this.commentService.create(board, commentForm.getContent(), siteUser);
        return String.format("redirect:/board/detail/%s", id);
    }



}
