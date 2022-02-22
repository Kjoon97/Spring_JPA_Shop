package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name ="member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")                    //한명의 회원이 여러개의 주문을함. , Order의 member필드에 의해 mapped된것. 즉 연관관계 주인은 Order이라는 것.
    private List<Order> orders = new ArrayList<>();


}
