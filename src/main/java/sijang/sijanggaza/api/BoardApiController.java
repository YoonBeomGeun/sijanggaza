package sijang.sijanggaza.api;

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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;
    private final UserService userService;
    private final ItemService itemService;


    /*****************************************소식 및 정보 게시글************************************/

    /**
     * 게시글 목록 조회
     * N+1 문제 해결, 컬렉션 패치 조인으로 페이징 불가할 수 있음
     */
    @GetMapping("/api/v2/userBoards")
    public Page<UserBoardResponseDTO> getBoardListOfUserV2(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam("kw") String kw) {
        Page<UserBoardResponseDTO> boardPage = this.boardService.getListOfUserV2(page, kw);
        return boardPage;
    }

    /**
     * 게시글 목록 조회
     * N+1 문제 해결, 메모리에 map으로 가져온 후, 메모리에서 매칭 후 값 세팅 -> 페이징 가능
     * batch_fetch_size로 N+1 문제 방지
     */
    @GetMapping("/api/v3/userBoards")
    public Page<UserBoardResponseDTO> getBoardListOfUserV3(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam("kw") String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("postDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<UserBoardResponseDTO> boardPage = this.boardService.getListOfUserV3(kw, pageable);
        return boardPage;
    }


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
    public CreateUserBoardResponseDTO createUserBoard(@PathVariable("id") Long id, @RequestBody @Valid BoardForm boardForm){
        SiteUser user = userService.findOne(id);
        Long boardId = boardService.create(user, boardForm);
        Board board = boardService.getBoard(Math.toIntExact(boardId));
        return new CreateUserBoardResponseDTO(board);
    }

    /**
     * 게시글 수정
     */
    @PutMapping("/api/v1/board/{boardId}")
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

    /**
     * 게시글 목록 조회
     * N+1 문제 해결, 컬렉션 패치 조인으로 페이징 불가할 수 있음
     */
    @GetMapping("/api/v2/ceoBoards")
    public Page<CeoBoardResponseDTO> getBoardListOfCeoV2(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam("kw") String kw) {
        Page<CeoBoardResponseDTO> itemBoardPage = this.boardService.getListOfCeoV2(page, kw);

        return itemBoardPage;
    }

    /**
     * 게시글 목록 조회
     * N+1 문제 해결, 메모리에 map으로 가져온 후, 메모리에서 매칭 후 값 세팅 -> 페이징 가능
     * batch_fetch_size로 N+1 문제 방지
     */
    @GetMapping("/api/v3/ceoBoards")
    public Page<CeoBoardResponseDTO> getBoardListOfCeoV3(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam("kw") String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("postDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<CeoBoardResponseDTO> itemBoardPage = this.boardService.getListOfCeoV3(kw, pageable);
        return itemBoardPage;
    }


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
    @PutMapping("/api/v1/itemBoard/{boardId}")
    public ModifyCeoBoardResponseDTO modifyCeoBoard(@PathVariable("boardId") Integer boardId, @RequestBody @Valid ItemBoardForm itemBoardForm){
        Board targetboard = this.boardService.getBoard(boardId);
        this.boardService.modify(targetboard, itemBoardForm.getTitle(), itemBoardForm.getContent());

        List<Item> existingItems = targetboard.getItemList();

        // 업데이트 된 아이템 목록
        List<ItemBoardForm.ItemForm> updatedItems = itemBoardForm.getItems();

        // 기존 아이템과 새로운 아이템 매핑 (아이템 이름과 가격을 조합한 키 사용)
        Map<String, ItemBoardForm.ItemForm> updatedItemsMap = updatedItems.stream()
                .collect(Collectors.toMap(itemForm -> itemForm.getName() + "_" + itemForm.getPrice(), Function.identity()));

        // 기존 아이템 수정 및 삭제 처리
        Iterator<Item> existingItemsIterator = existingItems.iterator();
        while (existingItemsIterator.hasNext()) {
            Item item = existingItemsIterator.next();
            String itemKey = item.getIName() + "_" + item.getPrice();
            ItemBoardForm.ItemForm updatedItemForm = updatedItemsMap.get(itemKey);

            if (updatedItemForm != null) {
                // 아이템 수정
                this.itemService.itemModify(item, updatedItemForm.getName(), updatedItemForm.getPrice(), updatedItemForm.getStockquantity());
                updatedItemsMap.remove(itemKey); // 수정된 아이템은 맵에서 제거
            } else {
                // 아이템 삭제
                this.itemService.itemDelete(item);
                existingItemsIterator.remove(); // 기존 리스트에서 삭제
            }
        }

        // 새로 추가된 아이템 처리
        updatedItemsMap.values().forEach(itemForm -> {
            Item newItem = new Item();
            newItem.setIName(itemForm.getName());
            newItem.setPrice(itemForm.getPrice());
            newItem.setStockQuantity(itemForm.getStockquantity());
            this.itemService.itemCreate(targetboard, newItem.getIName(), newItem.getPrice(), newItem.getStockQuantity());
            targetboard.getItemList().add(newItem);
        });

        return new ModifyCeoBoardResponseDTO(targetboard);
    }
}
