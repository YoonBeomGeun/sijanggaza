package sijang.sijanggaza.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sijang.sijanggaza.domain.Board;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

    Board findByTitle(String title);

    Board findByTitleAndContent(String title, String content);

    List<Board> findByTitleLike(String title);

    Page<Board> findAll(Pageable pageable); //JPA에서 지원

    Page<Board> findAll(Specification<Board> spec, Pageable pageable);

    @Query("select "
            + "distinct b"
            + " from Board b"
            + " left outer join SiteUser u1 on b.author=u1"
            + " where"
            + " u1.status = 'USER'"
            + " and(b.title like %:kw%"
            + " or b.content like %:kw%"
            + " or u1.username like %:kw%)")
    Page<Board> findAllByKeywordOfUserV1(@Param("kw") String kw, Pageable pageable);

    @Query("select" +
            " b from Board b" +
            " left join fetch b.commentList" +
            " join fetch b.author u" +
            " where" +
            " u.status = 'USER'" +
            " and(b.title like %:kw%" +
            " or b.content like %:kw%" +
            " or u.username like %:kw%)")
    Page<Board> findAllByKeywordOfUserV2(@Param("kw") String kw, Pageable pageable);


    @Query("select "
            + "distinct b"
            + " from Board b"
            + " left outer join SiteUser u1 on b.author=u1"
            + " where"
            + " u1.status = 'CEO'"
            + " and(b.title like %:kw%"
            + " or b.content like %:kw%"
            + " or u1.username like %:kw%)")
    Page<Board> findAllByKeywordOfCeoV1(@Param("kw") String kw, Pageable pageable);

    /**
     * N+1 해결
     */
    @Query("select" +
            " b from Board b" +
            " left join fetch b.itemList" +
            " join fetch b.author u" +
            " where" +
            " u.status = 'CEO'" +
            " and(b.title like %:kw%" +
            " or b.content like %:kw%" +
            " or u.username like %:kw%)")
    Page<Board> findAllByKeywordOfCeoV2(@Param("kw") String kw, Pageable pageable);
}
