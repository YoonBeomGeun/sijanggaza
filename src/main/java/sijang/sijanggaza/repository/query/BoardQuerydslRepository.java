package sijang.sijanggaza.repository.query;


import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.UserStatus;

import static sijang.sijanggaza.domain.QBoard.board;
import static sijang.sijanggaza.domain.QSiteUser.siteUser;

@Repository
public class BoardQuerydslRepository {

    private final EntityManager em;
    private final JPAQueryFactory query;

    public BoardQuerydslRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    /*@Query("select" +
            " b from Board b" +
            " join fetch b.author u" +
            " where" +
            " u.status = 'USER'" +
            " and(b.title like %:kw%" +
            " or b.content like %:kw%" +
            " or u.username like %:kw%)")
    Page<Board> findAllByKeywordOfUserV1_5(@Param("kw") String kw, Pageable pageable);*/
    /**
     * Querydsl을 사용한 findAllByKeywordOfUserV1_5(BoardQueryRepository)
     */
    public Page<Board> findAllByKeywordOfUserV1_5WithQuerydsl(@Param("kw") String kw, Pageable pageable) {
        QueryResults<Board> results = query
                .selectFrom(board)
                .innerJoin(board.author, siteUser).fetchJoin()
                .where(siteUser.status.eq(UserStatus.USER)
                        .and(board.title.likeIgnoreCase("%" + kw + "%")
                                .or(board.content.likeIgnoreCase("%" + kw + "%"))
                                .or(siteUser.username.likeIgnoreCase("%" + kw + "%"))))
                .orderBy(board.id.desc())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }
}
