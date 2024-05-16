package sijang.sijanggaza.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.repository.BoardRepository;
import sijang.sijanggaza.service.BoardService;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board") //피리픽스 => url 시작 부분
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String list(@RequestParam(value ="page", defaultValue = "0") int page, Model model) {
        Page<Board> paging = this.boardService.getList(page);
        model.addAttribute("paging", paging);
        return "board_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(@PathVariable("id") Integer id, CommentForm commentForm, Model model) {
        Board board = this.boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board_detail";
    }

    @GetMapping("/create")
    public String boardCreate(BoardForm boardForm) {
        return "board_form";
    }

    @PostMapping("/create")
    public String boardCreate(@Valid BoardForm boardForm, BindingResult result) {
        if(result.hasErrors()) {
            return "board_form";
        }
        this.boardService.create(boardForm.getTitle(), boardForm.getContent());
        return "redirect:/board/list";
    }




}
