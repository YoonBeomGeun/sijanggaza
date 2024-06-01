package sijang.sijanggaza.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import sijang.sijanggaza.controller.BoardForm;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.dto.board.response.CreateUserBoardResponseDTO;
import sijang.sijanggaza.dto.board.response.DeleteUserBoardResponseDTO;
import sijang.sijanggaza.dto.board.response.ModifyUserBoardResponseDTO;
import sijang.sijanggaza.dto.board.response.UserBoardResponseDTO;
import sijang.sijanggaza.service.BoardService;
import sijang.sijanggaza.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;
    private final UserService userService;

    /*@GetMapping("/api/v1/userBoards")
    public List<UserBoardResponseDTO> getBoardListOfUser(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam("kw") String kw) {
        Page<Board> boardListOfUser = this.boardService.getListOfUserV2(page, kw);
        List<UserBoardResponseDTO> userBoards = boardListOfUser.stream()
                .map(board -> new UserBoardResponseDTO(board))
                .collect(Collectors.toList());
        return userBoards;
    }*/

    /*@GetMapping("/api/v1/boards") // 페이징 조회
    public List<UserBoardResponseDTO> getBoardList(@RequestParam(value = "page", defaultValue = "0") int page){
        Page<Board> boards = boardService.getListV2(page);
        List<UserBoardResponseDTO> result= boards.stream()
                .map(b -> new UserBoardResponseDTO(b))
                .collect(Collectors.toList());
        return result;
    }*/

    /**
     * 게시글 상세
     */
    @GetMapping("/api/v1/board/{boardId}")
    public UserBoardResponseDTO getUserBoard(@PathVariable("boardId") Integer boardId) {
        Board board = boardService.getBoard(boardId);
        return new UserBoardResponseDTO(board);
    }

    /**
     * 게시글 생성
     */
    @PostMapping("/api/v1/{id}/board")
    public CreateUserBoardResponseDTO saveBoard(@PathVariable("id") Long id, @RequestBody @Valid BoardForm boardForm){
        SiteUser user = userService.findOne(id);
        Long boardId = boardService.create(user, boardForm);
        Board board = boardService.getBoard(Math.toIntExact(boardId));
        return new CreateUserBoardResponseDTO(board);
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/api/v1/board/{boardId}")
    public ModifyUserBoardResponseDTO saveBoard(@PathVariable("boardId") Integer boardId, @RequestBody @Valid BoardForm boardForm){
        Board targteBoard = this.boardService.getBoard(boardId);
        this.boardService.modify(targteBoard, boardForm.getTitle(), boardForm.getContent());
        return new ModifyUserBoardResponseDTO(targteBoard);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/api/v1/board/{boardId}")
    public DeleteUserBoardResponseDTO saveBoard(@PathVariable("boardId") Integer boardId){
        Board targteBoard = this.boardService.getBoard(boardId);
        this.boardService.delete(targteBoard);
        return new DeleteUserBoardResponseDTO(targteBoard.getId());
    }
}
