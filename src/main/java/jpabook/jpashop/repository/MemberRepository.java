package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    /**엔티티 매니저 주입(생성자 이용)*/
    private final EntityManager em;

    /**회원 등록하기 */
    public void save(Member member){
        em.persist(member);                 //Jpa가 member를 저장하는 로직.
    }


    /**id로 조회하기 */
    public Member findOne(Long id){
        return em.find(Member.class,id);      //JPA가 원하는 member를 찾아서 반환
    }


    /**전부 조회하기 */
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class).getResultList();    //직접 JPQL을 작성해서 JPA에서 회원 목록 가져옴
    }
    //sql은 테이블을 대상으로 쿼리를 함.
    //JPQL은 엔티티 객체를 대상으로 쿼리를 한다. "select m from Member m"은 Member 엔티티의 엘리어스를 m으로 지정하고 엔티티 Member를 조회해라라는 뜻임.


    /**이름으로 조회 하기 */
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name",name)
                .getResultList();
    }
}

/**
 * (기본 적으로 @SpringBootApplication이 등록되어있으면 해당 패키지 하위의 모든 @component들을 다 스캔, 스프링 빈으로 자동 등록한다.)
 * (@Controller, @Repository, @Service도 내부적으로 @component가 들어있어 스캔 대상이 되고 빈에 등록된다.)
 * 1. @Repository로 해당 리포지토리 클래스가 스프링 빈으로 등록된다.
 * 2. @PersistContext 를 스프링이 보고 JPA 엔티티 매니저를 해당 객체에 주입해줌.
 *    @PersistenceContext
 *     private EntityManager em;
 *    (다른 방법: 스프링부트가 생성자 주입으로도 주입할 수 있게 지원함.)
 * 3. em.persist(member)하면 영속성 컨텍스트에 member엔티티객체를 넣고 트랜잭션이 커밋 되는 시점에 DB에 반영이된다.( DB에 insert쿼리 날라감)
 *    persist()할때 영속성 컨텍스트에 값을 넣을 때 (key, value)가 정해져야하므로 member객체의 id값도 자동으로 채워준다.
 * 4. em.find(타입, 주요 키)  단건 조회.
 * 5. em.createQuery()로 JPQL 사용.
 * */