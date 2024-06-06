package sijang.sijanggaza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sijang.sijanggaza.domain.Item;
import sijang.sijanggaza.dto.item.ItemDto;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    Item findById(int id);

    /*Item findByBoardAndName(Board board, String name);*/

    List<Item> findAllByBoardId(Long boardId);

    @Query("select" +
            " new sijang.sijanggaza.dto.item.ItemDto(i.id, i.iName, b.id, i.price, i.stockQuantity)" +
            " from Item i" +
            " join i.board b" +
            " where b.id in :boardIds")
    List<ItemDto> findAllDtoByBoardIds(@Param("boardIds") List<Long> boardIds);


    @Modifying(clearAutomatically = true)
    @Query("update" +
            " Item i" +
            " set i.stockQuantity = i.stockQuantity - 1" +
            " where i.id = :id")
    void updateRemoveStockUsingQuery(@Param("id") Long id);

}
