package sijang.sijanggaza.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sijang.sijanggaza.domain.Board;
import sijang.sijanggaza.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    /*@Query("select "
            + "distinct o"
            + " from Order o"
            + " left outer join SiteUser u1 on o.siteUser.username=u1.username"
            + " where"
            + " o.item.iName like %:kw%")
    Page<Order> findAllByKeyword(@Param("kw") String kw, Pageable pageable);*/

}
