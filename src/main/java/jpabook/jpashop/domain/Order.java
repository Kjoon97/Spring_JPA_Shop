package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;  //@Entity,@Table,@ManyToOne,@Id,@GeneratedValue,@Column - JPA관련.
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Table(name= "orders")
@Getter @Setter
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)                  //여러개의 주문이 한명의 회원의해 수행됨.
    @JoinColumn(name = "member_id")                   //외래키 이름이 "member_id"으로 된다.
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)   //CascadeType.ALL하면 Order persist할때 자동으로 orderItem도 persist해준다.
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name= "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;  //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status;     //주문 상태

    //==연관관계 편의 매소드==//
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
