package sijang.sijanggaza.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Item;
import sijang.sijanggaza.exception.NotEnoughStockException;
import sijang.sijanggaza.repository.ItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public Item itemCreate(Board board, String name, int price, int stockquantity) {
        Item item = new Item();
        item.setBoard(board);
        item.setIName(name);
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
    public void deleteRemovedItems(List<Item> removedItems) {
        for (Item removedItem : removedItems) {
            this.itemRepository.delete(removedItem);
        }
    }

    @Transactional
    public void itemDelete(Item item) {
        this.itemRepository.delete(item);
    }

    /**
     * stock 증가
     */
    @Transactional
    public void addStock(Item item, int quantity) {
        item.setStockQuantity(item.getStockQuantity()+quantity);
    }

    /**
     * stock 감소
     */
    @Transactional
    public void removeStock(Item item, int quantity) {
        int restStock = item.getStockQuantity()-quantity;
        if(restStock<0) {
            throw new NotEnoughStockException("need more stock");
        }
        item.setStockQuantity(restStock);
    }

}
