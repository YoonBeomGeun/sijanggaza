package sijang.sijanggaza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {

    Item findById(int id);
    /*Item findByBoardAndName(Board board, String name);*/

}
