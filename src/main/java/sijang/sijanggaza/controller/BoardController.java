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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Item;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.domain.UserStatus;
import sijang.sijanggaza.repository.BoardRepository;
import sijang.sijanggaza.service.BoardService;
import sijang.sijanggaza.service.ItemService;
import sijang.sijanggaza.service.OrderService;
import sijang.sijanggaza.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board") //피리픽스 => url 시작 부분
public class BoardController {

    private final BoardService boardService;
    private final UserService userService;
    private final ItemService itemService;

    //소식 및 정보, 회원 유형 == USER
    @GetMapping("/list")
    public String list(@RequestParam(value ="page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw, Model model) {
        Page<Board> paging = this.boardService.getListOfUser(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "board_list";
    }

    //상품존, 회원 유형 == CEO
    @GetMapping("/itemList")
    public String boardItemList(@RequestParam(value ="page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw, Model model) {
        Page<Board> paging = this.boardService.getListOfCeo(page, kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "board_itemList";
    }

    //회원 유형 == USER, 게시글 보기
    @GetMapping(value = "/detail/{id}")
    public String detail(@PathVariable("id") Integer id, CommentForm commentForm, Model model) {
        Board board = this.boardService.getBoard(id);
        System.out.println(board.toString());
        model.addAttribute("board", board);
        return "board_detail";
    }

    //회원 유형 == CEO, 게시글 보기
    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/itemDetail/{id}")
    public String itemBoardDetail(@PathVariable("id") Integer id, CommentForm commentForm, Model model) {
        Board board = this.boardService.getBoard(id);
        System.out.println(board.toString());
        List<Item> items = this.itemService.getItem(board);
        model.addAttribute("board", board);
        model.addAttribute("items", items);
        return "board_itemDetail";
    }


    //회원 유형 == USER, 게시글 작성
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String boardCreate(BoardForm boardForm) {
        return "board_form";
    }

    //회원 유형 == USER, 게시글 작성
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


    //회원 유형 == CEO, 게시글 작성
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/itemCreate")
    public String boardItemCreate(ItemBoardForm itemBoardForm, Principal principal, RedirectAttributes redirectAttributes) {
        SiteUser siteUser = this.userService.getUser(principal.getName());
        if(siteUser.getStatus() == UserStatus.CEO) {
            return "board_itemForm";
        } else {
            redirectAttributes.addFlashAttribute("alertMessage", "상품존 게시글 작성권한이 없습니다.");
            return "redirect:/board/itemList";
        }
    }

    //회원 유형 == CEO, 게시글 작성
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/itemCreate")
    public String boardItemCreate(@Valid ItemBoardForm itemBoardForm, BindingResult result, Principal principal) {
        if(result.hasErrors()) {
            return "board_itemForm";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        Board board = this.boardService.itemBoardcreate(itemBoardForm.getTitle(), itemBoardForm.getContent(), siteUser);
        for (ItemBoardForm.ItemForm item : itemBoardForm.getItems()) {
            this.itemService.itemCreate(board, item.getName(), item.getPrice(), item.getStockquantity());
        }
        return "redirect:/board/itemList";
    }


    //회원 유형 == USER, 게시글 수정
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


    //회원 유형 == USER, 게시글 수정
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


    //회원 유형 == CEO, 게시글 수정
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/itemModify/{id}")
    public String boardItemModify(ItemBoardForm itemBoardForm, @PathVariable("id") Integer id, Principal principal) {
        Board board = this.boardService.getBoard(id);
        if(!board.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        itemBoardForm.setTitle(board.getTitle());
        itemBoardForm.setContent(board.getContent());
        List<Item> items = board.getItemList();
        System.out.println("아이템 리스트의 크기 = " + items.size());
        itemBoardForm.setItems(ItemBoardForm.fromItems(items));

        return "board_itemForm";
    }


    //회원 유형 == CEO, 게시글 수정
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/itemModify/{id}")
    public String boardItemModify(@Valid ItemBoardForm itemBoardForm, @PathVariable("id") Integer id,
                                  BindingResult result, Principal principal) {
        if(result.hasErrors()) {
            return "board_form";
        }
        Board board = this.boardService.getBoard(id);
        if(!board.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.boardService.modify(board, itemBoardForm.getTitle(), itemBoardForm.getContent());

        List<Item> existingItems = this.itemService.getItem(board);  // 기존 아이템 리스트 가져오기
        List<Item> updatedItems = new ArrayList<>();

        int size = Math.max(existingItems.size(), itemBoardForm.getItems().size());

        for(int i=0; i<size; i++) {
            if (i < itemBoardForm.getItems().size()) {
                ItemBoardForm.ItemForm itemForm = itemBoardForm.getItems().get(i);
                System.out.println("저장된 아이템 = " + itemBoardForm.getItems().get(i).getName());

                if (i < existingItems.size()) {
                    // 기존 아이템 업데이트
                    Item item = existingItems.get(i);
                    this.itemService.itemModify(item, itemForm.getName(), itemForm.getPrice(), itemForm.getStockquantity());
                    updatedItems.add(item);
                } else {
                    // 새로운 아이템 추가
                    Item item = this.itemService.itemCreate(board, itemForm.getName(), itemForm.getPrice(), itemForm.getStockquantity());
                    updatedItems.add(item);
                }
            } else {
                // 기존 아이템 삭제
                Item item = existingItems.get(i);
                this.itemService.itemDelete(item);
            }
        }
        board.setItemList(updatedItems);
        this.boardService.save(board);

        return String.format("redirect:/board/itemDetail/%s", id);
    }


    //회원 유형 == USER, 게시글 삭제
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


    //회원 유형 == CEO, 게시글 삭제
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/itemDelete/{id}")
    public String boardItemDelete(@PathVariable("id") Integer id, Principal principal) {
        Board board = this.boardService.getBoard(id);
        if(!board.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.boardService.delete(board);
        return "redirect:/board/itemList";
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
