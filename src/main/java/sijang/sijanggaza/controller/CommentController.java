package sijang.sijanggaza.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.service.BoardService;
import sijang.sijanggaza.service.CommentService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private final BoardService boardService;

    @Autowired
    private final CommentService commentService;

    @PostMapping("/create/{id}")
    public String createComment(@PathVariable("id") Integer id, @Valid CommentForm commentForm,
                                BindingResult result, Model model) {
        Board board = this.boardService.getBoard(id);
        if(result.hasErrors()) {
            model.addAttribute("board", board);
            return "board_detail";
        }
        this.commentService.create(board, commentForm.getContent());
        return String.format("redirect:/board/detail/%s", id);
    }



}
