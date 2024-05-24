package sijang.sijanggaza.domain;

import jakarta.persistence.*;
import lombok.*;
import sijang.sijanggaza.exception.NotEnoughStockException;

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

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

    @Column(length = 100)
    private String iName;

    @OneToMany(mappedBy = "item", fetch = FetchType.LAZY)
    private List<Order> orderList;

    private int price;

    private int stockQuantity;


    /*//==비지니스 로직==//
    *//**
     * stock 증가
     *//*
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    *//**
     * stock 감소
     *//*
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }*/
}
