package sijang.sijanggaza.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sijang.sijanggaza.domain.*;
import sijang.sijanggaza.dto.order.OrderDto;
import sijang.sijanggaza.repository.ItemRepository;
import sijang.sijanggaza.repository.OrderRepository;
import sijang.sijanggaza.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;

    @Transactional
    public void create(SiteUser siteUser, Item item, int quantity) {
        Optional<SiteUser> os = this.userRepository.findByusername(siteUser.getUsername());
        SiteUser user = os.get();

        Order orders = new Order();
        orders.setOrderPrice(item.getPrice()*quantity); //수정할 수도(form에서 받으면 됨)
        orders.setQuantity(quantity);
        orders.setOrderDate(LocalDateTime.now());
        orders.setSiteUser(user);
        orders.setItem(item);
        orders.setStatus(OrderStatus.ORDER);
        this.itemService.removeStockV2(item, quantity);

        this.orderRepository.save(orders);
    }

    @Transactional
    public void orderCancel(Integer id) {
        Optional<Order> oo = this.orderRepository.findById(id);
        Order order = oo.get();
        this.itemService.addStock(order.getItem(), order.getQuantity());
        order.setStatus(OrderStatus.CANCEL);
        this.orderRepository.save(order);
    }

    /*@Transactional
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
    }*/

    /*public Item selectedItem(Board board, String name) {
        Item item = this.itemRepository.findByBoardAndName(board, name);
        return item;
    }*/

    /**
     * 마이페이지용
     * 주문 내역
     */
    /*public Page<Order> getList(int page, String kw) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("orderDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        *//*Specification<Board> spec = search(kw); // Specification 방법으로 검색
        return this.boardRepository.findAll(spec, pageable);*//*
        System.out.println("결과는 : " + this.orderRepository.findAllByKeyword(kw, pageable));
        return this.orderRepository.findAllByKeyword(kw, pageable);
    }*/

    // userMypageV2
    public List<OrderDto> userMypageForV2(String username) {
        List<Order> orderList = this.orderRepository.findAllWithItem(username);
        return orderList.stream()
                .map(OrderDto::new)
                .collect(Collectors.toList());
    }

}
