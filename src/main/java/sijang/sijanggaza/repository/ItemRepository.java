package sijang.sijanggaza.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sijang.sijanggaza.domain.Item;
import sijang.sijanggaza.dto.item.ItemDto;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    Item findById(int id);

    /*Item findByBoardAndName(Board board, String name);*/

    List<Item> findAllByBoardId(Long boardId);

    @Query("select" +
            " i from Item i" +
            " join i.board b" +
            " where b.id in :boardIds")
    List<Item> findAllByBoardIds(@Param("boardIds") List<Long> boardIds);


    @Modifying(clearAutomatically = true)
    @Query("update" +
            " Item i" +
            " set i.stockQuantity = i.stockQuantity - 1" +
            " where i.id = :id")
    void updateRemoveStockUsingQuery(@Param("id") Long id);


    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from Item i where i.id = :id")
    Item findByPessimisticLock(@Param("id") final Long id);

    @Lock(value = LockModeType.OPTIMISTIC)
    @Query("select i from Item i where i.id = :id")
    Item findByOptimisticLock(@Param("id") Long id);

}
