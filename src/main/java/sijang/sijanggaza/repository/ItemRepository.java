package sijang.sijanggaza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Item;
import sijang.sijanggaza.dto.item.ItemDto;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    Item findById(int id);

    /*Item findByBoardAndName(Board board, String name);*/

    List<Item> findAllByBoardId(Long boardId);

    @Query("select" +
            " new sijang.sijanggaza.dto.item.ItemDto(i.id, i.iName, i.board.id, i.price, i.stockQuantity)" +
            " from Item i" +
            " join i.board b" +
            " where b.id in :boardIds")
    List<ItemDto> findAllDtoByBoardIds(@Param("boardIds") List<Long> boardIds);


}
