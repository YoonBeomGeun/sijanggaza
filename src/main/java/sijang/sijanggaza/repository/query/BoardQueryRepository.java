package sijang.sijanggaza.repository.query;

import org.springframework.data.jpa.repository.JpaRepository;
import sijang.sijanggaza.domain.Board;

public interface BoardQueryRepository extends JpaRepository<Board, Integer> {

}
