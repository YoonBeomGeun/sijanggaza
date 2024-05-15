package sijang.sijanggaza.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public String list(Model model) {
        List<Board> boardList = this.boardService.getList();
        model.addAttribute("boardList", boardList);
        return "board_list";
    }

    @GetMapping(value = "/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Board board = this.boardService.getBoard(id);
        model.addAttribute("board", board);
        return "board_detail";
    }

    @GetMapping("/create")
    public String boardCreate() {
        return "board_form";
    }

    @PostMapping("/create")
    public String boardCreate(@RequestParam(value = "title") String title,
                              @RequestParam(value = "content") String content) {
        // TODO 질문을 저장한다.
        return "redirect:/board/list";
    }
}
