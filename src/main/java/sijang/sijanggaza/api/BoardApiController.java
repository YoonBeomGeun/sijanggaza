package sijang.sijanggaza.api;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import java.util.*;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;
    private final UserService userService;
    private final ItemService itemService;


    /*****************************************소식 및 정보 게시글************************************/

    /**
     * batch_fetch_size 미 설정 시 댓글에 대한 N+1 문제 발생
     */
    @Operation(summary = "User 게시판 목록 조회", description = "파라미터로 받은 페이지를 불러옵니다.")
    @GetMapping("/api/v1_5/userBoards")
    public Page<UserBoardResponseDTO> getBoardListOfUserV1_5(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<UserBoardResponseDTO> boardPage = this.boardService.getListOfUserV1_5(page, kw);
        return boardPage;
    }

    /**
     * 게시글 목록 조회
     * N+1 문제 해결, 컬렉션 패치 조인으로 페이징 불가할 수 있음
     */
    @GetMapping("/api/v2/userBoards")
    public Page<UserBoardResponseDTO> getBoardListOfUserV2(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<UserBoardResponseDTO> boardPage = this.boardService.getListOfUserV2(page, kw);
        return boardPage;
    }

    /**
     * 게시글 목록 조회
     * N+1 문제 해결, 메모리에 map으로 가져온 후, 메모리에서 매칭 후 값 세팅 -> 페이징 가능
     * batch_fetch_size로 N+1 문제 방지
     */
    @GetMapping("/api/v3/userBoards")
    public Page<UserBoardResponseDTO> getBoardListOfUserV3(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("postDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<UserBoardResponseDTO> boardPage = this.boardService.getListOfUserV3(kw, pageable);
        return boardPage;
    }


    /**
     * 게시글 상세
     */
    @GetMapping("/api/v1/userBoard/{boardId}")
    public UserBoardResponseDTO getUserBoard(@PathVariable("boardId") Integer boardId) {
        Board board = boardService.getBoard(boardId);
        return new UserBoardResponseDTO(board);
    }

    /**
     * 게시글 생성
     */
    @PostMapping("/api/v1/{id}/userBoard")
    public CreateUserBoardResponseDTO createUserBoard(@PathVariable("id") Long id, @RequestBody @Valid BoardForm boardForm){
        SiteUser user = userService.findOne(id);
        Long boardId = boardService.create(user, boardForm);
        Board board = boardService.getBoard(Math.toIntExact(boardId));
        return new CreateUserBoardResponseDTO(board);
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/api/v1/userBoard/{boardId}")
    public ModifyUserBoardResponseDTO modifyUserBoard(@PathVariable("boardId") Integer boardId, @RequestBody @Valid BoardForm boardForm){
        Board targteBoard = this.boardService.getBoard(boardId);
        this.boardService.modify(targteBoard, boardForm.getTitle(), boardForm.getContent());
        return new ModifyUserBoardResponseDTO(targteBoard);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/api/v1/board/{boardId}")
    public DeleteUserBoardResponseDTO deleteBoard(@PathVariable("boardId") Integer boardId){
        Board targteBoard = this.boardService.getBoard(boardId);
        this.boardService.delete(targteBoard);
        return new DeleteUserBoardResponseDTO(targteBoard.getId());
    }


    /******************************************************상품 게시글************************************************************/

    @GetMapping("/api/v1_5/ceoBoards")
    public Page<CeoBoardResponseDTO> getBoardListOfCeoV1_5(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<CeoBoardResponseDTO> itemBoardPage = this.boardService.getListOfCeoV1_5(page, kw);

        return itemBoardPage;
    }

    /**
     * 게시글 목록 조회
     * N+1 문제 해결, 컬렉션 패치 조인으로 페이징 불가할 수 있음
     */
    @GetMapping("/api/v2/ceoBoards")
    public Page<CeoBoardResponseDTO> getBoardListOfCeoV2(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<CeoBoardResponseDTO> itemBoardPage = this.boardService.getListOfCeoV2(page, kw);

        return itemBoardPage;
    }

    /**
     * 게시글 목록 조회
     * N+1 문제 해결, 메모리에 map으로 가져온 후, 메모리에서 매칭 후 값 세팅 -> 페이징 가능
     * batch_fetch_size로 N+1 문제 방지
     */
    @GetMapping("/api/v3/ceoBoards")
    public Page<CeoBoardResponseDTO> getBoardListOfCeoV3(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "kw", defaultValue = "") String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("postDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<CeoBoardResponseDTO> itemBoardPage = this.boardService.getListOfCeoV3(kw, pageable);
        return itemBoardPage;
    }

    /**
     * 게시글 상세
     */
    @GetMapping("/api/v1/ceoBoard/{boardId}")
    public CeoBoardResponseDTO getCeoBoardV1(@PathVariable("boardId") Integer boardId) {
        Board board = boardService.getBoard(boardId);
        return new CeoBoardResponseDTO(board);
    }

    @GetMapping("/api/v2/ceoBoard/{boardId}")
    public CeoBoardResponseDTO getCeoBoardV2(@PathVariable("boardId") Integer boardId) {
        Board board = boardService.getBoardV2(boardId);
        return new CeoBoardResponseDTO(board);
    }

    /**
     * 게시글 생성
     */
    @PostMapping("/api/v1/{id}/ceoBoard")
    public CreateCeoBoardResponseDTO createCeoBoard(@PathVariable("id") Long id, @RequestBody @Valid ItemBoardForm itemBoardForm){
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
        board.setItemList(itemList);

        return new CreateCeoBoardResponseDTO(board);
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/api/v1.5/ceoBoard/{boardId}")
    public ModifyCeoBoardResponseDTO modifyCeoBoard(@PathVariable("boardId") Integer boardId, @RequestBody @Valid ItemBoardForm itemBoardForm){
        Board targetboard = this.boardService.getBoard(boardId);
        this.boardService.modify(targetboard, itemBoardForm.getTitle(), itemBoardForm.getContent());

        List<Item> existingItems = targetboard.getItemList();
        System.out.println("수정 전: "+existingItems);

        // 업데이트된 아이템 목록
        List<ItemBoardForm.ItemForm> updatedItems = itemBoardForm.getItems();
        System.out.println("업데이트 내용: "+updatedItems);

        List<Item> deleteBox = new ArrayList<>();

        if (updatedItems.size() == 0) {
            this.itemService.deleteRemovedItems(existingItems);
        } else {
            // 업데이트된 아이템이 있는 경우
            for (int i = 0; i < Math.max(existingItems.size(), updatedItems.size()); i++) {
                if (i >= updatedItems.size()) {
                    // 업데이트된 아이템 목록의 크기를 넘어선 경우, 기존 아이템 삭제
                    deleteBox.add(existingItems.get(i));
                } else {
                    // 업데이트된 아이템이 있는 경우, 아이템 수정 또는 생성
                    ItemBoardForm.ItemForm updatedItemForm = updatedItems.get(i);
                    Item existingItem = existingItems.size() > i ? existingItems.get(i) : null;
                    if (existingItem == null) {
                        // 기존 아이템이 없으면 새로 생성
                        Item newItem = new Item();
                        newItem.setIName(updatedItemForm.getName());
                        newItem.setBoard(targetboard);
                        newItem.setPrice(updatedItemForm.getPrice());
                        newItem.setStockQuantity(updatedItemForm.getStockquantity());
                        existingItems.add(this.itemService.itemCreate(targetboard, newItem.getIName(), newItem.getPrice(), newItem.getStockQuantity()));
                    } else {
                        this.itemService.itemModify(existingItem, updatedItemForm.getName(), updatedItemForm.getPrice(), updatedItemForm.getStockquantity());
                    }
                }
            }
        }
        System.out.println("수정 후: "+existingItems);

        // 삭제할 아이템 삭제
        existingItems.removeAll(deleteBox);
        System.out.println(deleteBox);

        //existingItems에서 deleteBox 뺸 다음 set
        targetboard.setItemList(existingItems);
        this.boardService.save(targetboard);

        return new ModifyCeoBoardResponseDTO(targetboard);
    }
}
