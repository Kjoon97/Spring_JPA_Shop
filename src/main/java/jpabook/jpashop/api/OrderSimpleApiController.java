package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *  xxToOne(ManyToOne, OneToOne) 성능 최적화하기
 * Order
 * Order -> Member
 * Order -> Delivery
 * **/
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();  //order.getMember까지는 프록시로 하지만 .getName()때문에 강제 Lazy 초기화됨.
            order.getDelivery().getAddress();  //Lazy강제 초기화
        }
        return all;
    }
}
