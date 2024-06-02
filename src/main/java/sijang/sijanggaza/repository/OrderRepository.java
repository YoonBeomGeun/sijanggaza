package sijang.sijanggaza.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    /*@Query("select "
            + "distinct o"
            + " from Order o"
            + " left outer join SiteUser u1 on o.siteUser.username=u1.username"
            + " where"
            + " o.item.iName like %:kw%")
    Page<Order> findAllByKeyword(@Param("kw") String kw, Pageable pageable);*/


    /*public List<Order> findAllWithItem() {
        return em.createQuery(
                        "select o from Order o" +
                                " join fetch o.member m" +
                                " join fetch o.delivery d" +
                                " join fetch o.orderItems oi" + //여기서 일대다 fetch join, order의 기준이 틀어져서 페이징 불가
                                " join fetch oi.item i", Order.class)
                .getResultList();
    }*/

    //UserPage API V2 쿼리
    @Query("select"
            + " o from Order o"
            + " join fetch o.siteUser u"
            + " join fetch o.item i"
            + " where u.username = :username")
    List<Order> findAllWithItem(@Param("username") String username);
}
