package jpabook.jpashop;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;              //jpa 관련
import javax.persistence.GeneratedValue;
import javax.persistence.Id;                 //jpa 관련

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String username;
}
