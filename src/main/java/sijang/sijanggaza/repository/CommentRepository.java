package sijang.sijanggaza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sijang.sijanggaza.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    // 게시글과 회원 정보를 모두 fetch join해서 쿼리 최소화
    @Query("select" +
            " c from Comment c" +
            " join fetch c.board b" +
            " join fetch c.author" +
            " where b.id = :boardId")
    List<Comment> findAllComment(@Param("boardId") Long boardId);
}
