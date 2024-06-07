package sijang.sijanggaza.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Item;
import sijang.sijanggaza.exception.NotEnoughStockException;
import sijang.sijanggaza.repository.ItemRepository;

import java.util.List;
import java.util.Optional;

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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void removeStockV1(Item item, int quantity) {
        int restStock = item.getStockQuantity()-quantity;
        if(restStock<0) {
            throw new NotEnoughStockException("need more stock");
        }
        item.setStockQuantity(restStock);
    }

    public synchronized void removeStockV1_5(Item item, int quantity) {
        int restStock = item.getStockQuantity()-quantity;
        if(restStock<0) {
            throw new NotEnoughStockException("need more stock");
        }
        item.setStockQuantity(restStock);
    }

    @Transactional
    public void removeStockV2(Item item, int quantity) {
        Item foundItem = itemRepository.findByPessimisticLock(item.getId());
        foundItem.removeStock(quantity);
        itemRepository.saveAndFlush(foundItem);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void removeStockV3(Long id, int quantity) {
        Item foundItem = itemRepository.findByOptimisticLock(id);
        foundItem.removeStock(quantity);
        itemRepository.saveAndFlush(foundItem);
    }

    @Transactional
    public void removeStockUsingOptimisticLock(Long id, int quantity) throws InterruptedException {
        int maxRetries = 5;  // 재시도 최대 횟수
        int retryCount = 0;
        while (retryCount < maxRetries) {
            try {
                removeStockV3(id, quantity);
                break;
            } catch (OptimisticLockingFailureException e) {  // 구체적인 예외 처리
                retryCount++;
                if (retryCount >= maxRetries) {
                    throw e;  // 최대 재시도 횟수를 초과한 경우 예외를 던집니다.
                }
                Thread.sleep(50);
                System.out.println("동시성 제어중,, 재시도 횟수: " + retryCount);
            } catch (Exception e) {
                // 기타 예외 처리: 예상치 못한 예외는 로깅하거나 별도로 처리합니다.
                System.err.println("예기치 못한 오류 발생: " + e.getMessage());
                throw e;
            }
        }
    }






    /*@Transactional
    public void removeStockV2(Item item, int quantity) {
        int restStock = item.getStockQuantity()-quantity;
        if(restStock<0) {
            throw new NotEnoughStockException("need more stock");
        }
        itemRepository.updateRemoveStockUsingQuery(item.getId());
        item.setStockQuantity(restStock);
    }*/


}
