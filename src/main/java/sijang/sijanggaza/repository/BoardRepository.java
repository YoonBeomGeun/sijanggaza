package sijang.sijanggaza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sijang.sijanggaza.domain.Board;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    Board findByTitle(String title);

    Board findByTitleAndContent(String title, String content);

    List<Board> findByTitleLike(String title);

}
