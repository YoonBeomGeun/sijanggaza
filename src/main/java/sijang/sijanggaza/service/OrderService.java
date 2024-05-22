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
    public void create(String username, Item item, int count) {
        Optional<SiteUser> os = this.userRepository.findByusername(username);
        SiteUser user = os.get();

        Order order = new Order();
        order.setName(item.getName());
        order.setSiteUser(user);
        order.setOrderPrice(item.getPrice()*count); //수정할 수도(form에서 받으면 됨)
        order.setCount(count);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.ORDER);
    }

    public Item selectedItem(Board board, String name) {
        Item item = this.itemRepository.findByBoardAndName(board, name);
        return item;
    }
}
