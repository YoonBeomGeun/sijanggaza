package sijang.sijanggaza.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.repository.BoardRepository;
import sijang.sijanggaza.service.BoardService;
import sijang.sijanggaza.service.UserService;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board") //피리픽스 => url 시작 부분
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(@RequestParam(value ="page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw, Model model) {
        Page<Board> paging = this.boardService.getList(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "board_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(@PathVariable("id") Integer id, CommentForm commentForm, Model model) {
        Board board = this.boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String boardCreate(BoardForm boardForm) {
        return "board_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String boardCreate(@Valid BoardForm boardForm, BindingResult result, Principal principal) {
        if(result.hasErrors()) {
            return "board_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.boardService.create(boardForm.getTitle(), boardForm.getContent(), siteUser);
        return "redirect:/board/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String boardModify(BoardForm boardForm, @PathVariable("id") Integer id, Principal principal) {
        Board board = this.boardService.getBoard(id);
        if(!board.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        boardForm.setTitle(board.getTitle());
        boardForm.setContent(board.getContent());
        return "board_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String boardModify(@Valid BoardForm boardForm, @PathVariable("id") Integer id,
                              BindingResult result, Principal principal) {
        if(result.hasErrors()) {
            return "board_form";
        }
        Board board = this.boardService.getBoard(id);
        if(!board.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.boardService.modify(board, boardForm.getTitle(), boardForm.getContent());
        return String.format("redirect:/board/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String boardDelete(@PathVariable("id") Integer id, Principal principal) {
        Board board = this.boardService.getBoard(id);
        if(!board.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.boardService.delete(board);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/ddabong/{id}")
    public String boardDdabong(@PathVariable("id") Integer id, Principal principal) {
        Board board = this.boardService.getBoard(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.boardService.ddabong(board, siteUser);
        return String.format("redirect:/board/detail/%s", id);
    }


}
