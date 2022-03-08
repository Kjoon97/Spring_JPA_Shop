package jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {

    @NotEmpty(message= "회원 이름은 필수입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;
}
/**직접 member엔티티를 쓰기보단 폼 객체 DTO를 만드는 것이 좋다.
 * 화면에서 요구하는 객체와 데이터쪽 엔티티의 요구사항(검증,)이 다를 수 있기 때문이다.
 * **/