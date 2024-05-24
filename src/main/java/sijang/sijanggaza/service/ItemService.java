package sijang.sijanggaza.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sijang.sijanggaza.DataNotFoundException;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Comment;
import sijang.sijanggaza.domain.Item;
import sijang.sijanggaza.domain.SiteUser;
import sijang.sijanggaza.repository.ItemRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    /*@Transactional
    public void create(String title, String content, SiteUser user) {
        Board b = new Board();
        b.setTitle(title);
        b.setContent(content);
        b.setPostDate(LocalDateTime.now());
        b.setAuthor(user);
        this.boardRepository.save(b);
    }*/
    /*@Transactional
    public Comment create(Board board, String content, SiteUser author) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setPostDate(LocalDateTime.now());
        comment.setBoard(board);
        comment.setAuthor(author);
        this.commentRepository.save(comment);
        return comment;
    }*/

    @Transactional
    public Item itemCreate(Board board, String name, int price, int stockquantity) {
        Item item = new Item();
        item.setIName(name);
        item.setBoard(board);
        item.setPrice(price);
        item.setStockQuantity(stockquantity);
        this.itemRepository.save(item);
        return item;
    }

    public List<Item> getItem(Board board) {
        List<Item> items = board.getItemList();
        return items;
    }

    public Item selectedItem(int id) {
        Item item = this.itemRepository.findById(id);
        return item;
    }

    @Transactional
    public Item itemModify(Item item, String name, int price, int stockquantity) {
        item.setIName(name);
        item.setPrice(price);
        item.setStockQuantity(stockquantity);
        Item updatedItem = this.itemRepository.save(item);
        return updatedItem;
    }

    @Transactional
    public void itemDelete(Item item) {
        this.itemRepository.delete(item);
    }

    /**
     * stock 증가
     */
    public void addStock(int quantity) {

    }

    /**
     * stock 감소
     */
    public void removeStock(int quantity) {

    }
}
