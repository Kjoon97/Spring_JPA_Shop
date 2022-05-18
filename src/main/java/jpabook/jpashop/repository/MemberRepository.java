package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    //select m from Member m where m.name=?으로 자동으로 스프링 데이터 jpa가 jpql을 만듦.
    List<Member> findByName(String name);
}
