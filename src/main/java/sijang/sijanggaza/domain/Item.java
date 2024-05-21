package sijang.sijanggaza.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @ManyToOne
    private Board board;

    @Column(length = 100)
    private String name;

    /*@Column(columnDefinition = "TEXT")
    private String content;*/

    private int price;

    private int stockQuantity;

}
