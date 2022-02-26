package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //외부에서 OrderItem orderItem = new OrderItem(); orderItem.set()이런 식으로 생성 못하게 막음.
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; //주문 가격
    private int count;     //주문 수량.

//    protected OrderItem(){     //외부에서 OrderItem orderItem = new OrderItem(); orderItem.set()이런 식으로 생성 못하게 막음.
//    }                          // (오직 createOrderItem()을 통해서 생성하기 위함 - 한가지 방법으로해야 유지보수하기 편함) /롬복@NoArgsConstructor(access = AccessLevel.PROTECTED)으로 대체 가능.

    //==생성 매서드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);
        item.removeStock(count);
        return orderItem;
    }


    //==비지니스 로직==//
    public void cancel(){
        getItem().addStock(count);   //재고 수량을 원복
    }

    /**주문 상품 전체 가격 조회*/
    public int getTotalPrice() {
        return getOrderPrice() *getCount();
    }
}
