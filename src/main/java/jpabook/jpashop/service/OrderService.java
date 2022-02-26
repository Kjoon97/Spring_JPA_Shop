package jpabook.jpashop.service;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**주문*/
    @Transactional
    public Long order(Long MemberId, Long itemId, int count){
        //엔티티 조회
        Member member = memberRepository.findOne(MemberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());  //회원의 주소 정보를 배송지로 한다.

        //주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        //주문 아이디 반환
        return order.getId();
    }
    /**주문 취소*/
    @Transactional
    public void cancelOrder(Long orderId){
        //주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);
        //주문 취소
        order.cancel();
        //엔티티안의 데이터만 바뀌면 jpa가 알아서 변경된 부분들을 더티체킹(감지)하여 데이터베이스에 UPDATE 쿼리를 날린다.(JPA의 강점)
        //cancel()안에서 order의 OrderStatus의 값과 item의 stockQuantity가 변경되므로 Order와 Item에 UDATE쿼리가 날라간다.
        //JPA가 아닌 다른 DB접근 기술은 직접 쿼리 문을 짜야한다.
    }


    //검색
}
/**
 *  원래는 delivery, orderItem 각각 레포지토리 불러와서 save()를 해야되지만
 *  Order클래스에서 delivery와, orderItem에 CascadeType.ALL을 했기 때문에 여기서 Order 엔티티만 persist하면 Delivery, OrderItem엔티티도 같이 persist된다.
 * (주의 Delivery, OrderItem 중요하고 다른데에서 참조를 하면 이런식으로 CascadeType을 안하고 별도로 리포지토리 불러와서하는 것이 좋다.)
 * 이번 경우에는 Order에서만 OrderItem과 Delivery를 사용하고 persist하는 라이프사이클이 같기 때문에 이 2가지 조건 충족으로 CascadeType을 사용했다.
 * 감이 잘 안잡히면 cascade 안 사용하는 것 추천. 일일히 다 리포지토리 사용하는 것 추천.
 * */


