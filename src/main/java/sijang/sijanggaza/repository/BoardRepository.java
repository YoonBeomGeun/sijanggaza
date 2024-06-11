package sijang.sijanggaza.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.dto.board.response.CeoBoardResponseDTO;
import sijang.sijanggaza.dto.board.response.UserBoardResponseDTO;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    Board findByTitle(String title);

    Board findByTitleAndContent(String title, String content);

    List<Board> findByTitleLike(String title);

    /*@Query("select" +
            " count(b.ddabong) from Board b" +
            " where b = :board")
    int countDdabongByBoard(@Param("board") Board board);*/

    /*@Query("SELECT b FROM Board b LEFT JOIN FETCH b.ddabong WHERE b.id = :boardId")
    Board findBoardWithDdabongById(@Param("boardId") Long boardId);*/

    @Query("select count(d) from Board b join b.ddabong d where b = :board")
    int countDdabongByBoard(@Param("board") Board board);

    Page<Board> findAll(Pageable pageable); //JPA에서 지원

    Page<Board> findAll(Specification<Board> spec, Pageable pageable);

}
