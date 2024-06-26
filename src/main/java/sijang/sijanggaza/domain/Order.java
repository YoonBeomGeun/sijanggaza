package sijang.sijanggaza.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private int orderPrice;
    private int quantity;
    private LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private SiteUser siteUser;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문 상태(ORDER, CANCEL)


    //==연관관계 메서드==//
    public void setSiteUser(SiteUser siteUser) {
        this.siteUser = siteUser;
        siteUser.getOrderList().add(this);
    }

}
