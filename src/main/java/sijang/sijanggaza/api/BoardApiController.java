package sijang.sijanggaza.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import sijang.sijanggaza.controller.BoardForm;
import sijang.sijanggaza.controller.ItemBoardForm;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Item;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.dto.board.response.*;
import sijang.sijanggaza.service.BoardService;
import sijang.sijanggaza.service.ItemService;
import sijang.sijanggaza.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;
    private final UserService userService;
    private final ItemService itemService;

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

    /**소식 및 정보 게시글**/

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


    /******************************************************************************************************************/

    /**상품 게시글**/

    /**
     * 게시글 상세
     */
    @GetMapping("/api/v1/itemBoard/{boardId}")
    public CeoBoardResponseDTO getCeoBoard(@PathVariable("boardId") Integer boardId) {
        Board board = boardService.getBoard(boardId);
        return new CeoBoardResponseDTO(board);
    }

    /**
     * 게시글 생성
     */
    @PostMapping("/api/v1/{id}/itemBoard")
    public CreateCeoBoardResponseDTO saveBoard(@PathVariable("id") Long id, @RequestBody @Valid ItemBoardForm itemBoardForm){
        SiteUser user = userService.findOne(id);
        Board board = this.boardService.itemBoardcreate(itemBoardForm.getTitle(), itemBoardForm.getContent(), user);
        List<Item> itemList = new ArrayList<>();
        for (ItemBoardForm.ItemForm itemForm : itemBoardForm.getItems()) {
            Item item = new Item();
            item.setIName(itemForm.getName());
            item.setPrice(itemForm.getPrice());
            item.setStockQuantity(itemForm.getStockquantity());
            itemList.add(item);
            this.itemService.itemCreate(board, item.getIName(), item.getPrice(), item.getStockQuantity());
        }
        // Null 체크 후에만 itemList를 board에 설정
        if (board.getItemList() == null) {
            board.setItemList(new ArrayList<>(itemList));
        } else {
            board.getItemList().addAll(itemList);
        }

        return new CreateCeoBoardResponseDTO(board);
    }

    /**
     * 게시글 수정
     */
    /*@PutMapping("/api/v1/itemBoard/{boardId}")
    public ModifyUserBoardResponseDTO saveBoard(@PathVariable("boardId") Integer boardId, @RequestBody @Valid ItemBoardForm itemBoardForm){
        Board targteBoard = this.boardService.getBoard(boardId);
        this.boardService.modify(targteBoard, boardForm.getTitle(), boardForm.getContent());
        return new ModifyUserBoardResponseDTO(targteBoard);
    }*/
}
