package sijang.sijanggaza.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Comment;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.domain.UserStatus;
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

    /**
     * 댓글 생성 - 자유
     */
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
        Comment comment = this.commentService.create(board, commentForm.getContent(), siteUser);
        return String.format("redirect:/board/detail/%s#comment_%s", comment.getBoard().getId(), comment.getId());
    }

    /**
     * 댓글 생성 - 상품존
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/iBoadrdCreate/{id}")
    public String createItemComment(@PathVariable("id") Integer id, @Valid CommentForm commentForm,
                                    BindingResult commentResult, OrderForm orderForm, Principal principal, Model model) {
        Board board = this.boardService.getBoard(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if(commentResult.hasErrors()) {
            model.addAttribute("board", board);
            return "board_itemDetail";
        }
        Comment comment = this.commentService.create(board, commentForm.getContent(), siteUser);
        return String.format("redirect:/board/itemDetail/%s#comment_%s", comment.getBoard().getId(), comment.getId());
    }


    /**
     * 댓글 수정
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String modifyComment(CommentForm commentForm, @PathVariable("id") Integer id, Principal principal) {
        Comment comment = this.commentService.getComment(id);
        if(!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        commentForm.setContent(comment.getContent());
        return "comment_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String modifyComment(@Valid CommentForm commentForm, @PathVariable("id") Integer id,
                                BindingResult result, Principal principal) {
        if(result.hasErrors()) {
            return "comment_form";
        }
        Comment comment = this.commentService.getComment(id);
        Board board = this.boardService.getBoard(Math.toIntExact(comment.getBoard().getId()));
        if(!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.commentService.modify(comment, commentForm.getContent());
        
        if(board.getAuthor().getStatus() == UserStatus.CEO) {
            return String.format("redirect:/board/itemDetail/%s#comment_%s", comment.getBoard().getId(), comment.getId());
        } else {
            return String.format("redirect:/board/detail/%s#comment_%s", comment.getBoard().getId(), comment.getId());
        }
    }

    /**
     * 댓글 삭제
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable("id") Integer id, Principal principal) {
        Comment comment = this.commentService.getComment(id);
        Board board = this.boardService.getBoard(Math.toIntExact(comment.getBoard().getId()));
        if(!comment.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.commentService.delete(comment);
        
        if(board.getAuthor().getStatus() == UserStatus.CEO) {
            return String.format("redirect:/board/itemDetail/%s", comment.getBoard().getId());
        } else {
            return String.format("redirect:/board/detail/%s", comment.getBoard().getId());
        }
    }

    /**
     * 댓글 추천
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/ddabong/{id}")
    public String commentDdabong(@PathVariable("id") Integer id, Principal principal) {
        Comment comment = this.commentService.getComment(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        Board board = this.boardService.getBoard(Math.toIntExact(comment.getBoard().getId()));
        this.commentService.ddabong(comment, siteUser);
        
        if(board.getAuthor().getStatus() == UserStatus.CEO) {
            return String.format("redirect:/board/itemDetail/%s#comment_%s", comment.getBoard().getId(), comment.getId());
        } else {
            return String.format("redirect:/board/detail/%s#comment_%s", comment.getBoard().getId(), comment.getId());
        }
    }

}
