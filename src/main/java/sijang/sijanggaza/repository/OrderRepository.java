package sijang.sijanggaza.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sijang.sijanggaza.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
