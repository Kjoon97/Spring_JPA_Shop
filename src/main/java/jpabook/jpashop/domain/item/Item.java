package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
@Getter @Setter
public class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비지니스 로직==/
    /**재고(stock) 증가*/
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }
    /**재고(stock) 감소*/
    public void removeStock(int quantity){
        int restStock = this.stockQuantity-quantity;
        if(restStock<0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

    //도메인 주도 설계 엔티티 자체에 해결할 수 있는 건 엔티티 안에 비지니스 로직을 넣는 것이 좋다. 그래야 가장 응집도가 높기 때문이다. 객체 지향적 설계이다.
    //외부에서 setter로 쓰는 것보다 엔티티에 비지니스 로직을 넣어서 하는 것이 좋다.
}
