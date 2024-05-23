package sijang.sijanggaza.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sijang.sijanggaza.domain.*;
import sijang.sijanggaza.repository.ItemRepository;
import sijang.sijanggaza.repository.OrderRepository;
import sijang.sijanggaza.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public void create(SiteUser author, Item item, int count) {
        Optional<SiteUser> os = this.userRepository.findByusername(String.valueOf(author));
        SiteUser user = os.get();

        Order orders = new Order();
        orders.setName(item.getName());
        orders.setSiteUser(user);
        orders.setOrderPrice(item.getPrice()*count); //수정할 수도(form에서 받으면 됨)
        orders.setCount(count);
        orders.setOrderDate(LocalDateTime.now());
        orders.setStatus(OrderStatus.ORDER);
        this.orderRepository.save(orders);
    }

    @Transactional
    public void testCreate(String username, Item item, int count) {
        Optional<SiteUser> os = this.userRepository.findByusername(String.valueOf(username));
        SiteUser user = os.get();

        Order orders = new Order();
        orders.setName(item.getName());
        orders.setSiteUser(user);
        orders.setOrderPrice(item.getPrice()*count); //수정할 수도(form에서 받으면 됨)
        orders.setCount(count);
        orders.setOrderDate(LocalDateTime.now());
        orders.setStatus(OrderStatus.ORDER);
        this.orderRepository.save(orders);
    }

    public Item selectedItem(Board board, String name) {
        Item item = this.itemRepository.findByBoardAndName(board, name);
        return item;
    }
}
