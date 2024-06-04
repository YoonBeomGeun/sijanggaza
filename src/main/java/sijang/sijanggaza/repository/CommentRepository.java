package sijang.sijanggaza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sijang.sijanggaza.domain.Comment;
import sijang.sijanggaza.dto.comment.CommentDto;
import sijang.sijanggaza.dto.item.ItemDto;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    // 게시글과 회원 정보를 모두 fetch join해서 쿼리 최소화
    @Query("select" +
            " c from Comment c" +
            " join fetch c.board b" +
            " join fetch c.author" +
            " where b.id = :boardId")
    List<Comment> findAllComment(@Param("boardId") Long boardId);

    @Query("select" +
            " new sijang.sijanggaza.dto.comment.CommentDto(c.id, c.content, c.author.username, b.id, c.postDate, c.modifyDate)" +
            " from Comment c" +
            " join c.board b" +
            " where b.id in :boardIds")
    List<CommentDto> findAllDtoByBoardIds(@Param("boardIds") List<Long> boardIds);
}
