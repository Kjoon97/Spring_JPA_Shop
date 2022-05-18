package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest    //스프링부트 컨테이너 안에서 테스트하는 것. 없으면 @Autowired 오류 뜸
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;   //다른 곳에서 참조할게 없으니깐 그냥 필드 주입해도된다.
   // @Autowired EntityManager em;

    @Test
    //@Rollback(false)
    public void 회원가입() throws Exception{
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);
        //then
        //  em.flush();
        assertEquals(member,memberRepository.findById(savedId).get());
        //이게 가능한 이유는 @Transactional덕분에 각 jpa안에서 같은 트랜잭션안에서 pk값이 같으면 같은 영속성 컨텍스트에서 똑같은 애로 관리된다.
    }

    @Test
    public void 중복_회원_예외() throws Exception{

        //given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");
        //when
        memberService.join(member1);
        try{
            memberService.join(member2); // 같은 이름이니깐 예외가 발생해야함!!!
        }catch (IllegalStateException e){
            return;
        }
        //then
        fail("예외가 발생해야한다.");
    }

    /**
     @Transactional은 트랜잭션을 커밋이 아니라 롤백해준다. 그래서 join->save->em.persist()를 해서 영속성 컨텍스트에 올라갈 뿐이지
     디비에 반영되지는 않는다(insert문을 날리지 않는다)
     롤백도 해주고 insert문을 날리는 것을 확인해보고 싶으면 EntityManger를 주입하고 em.flush()로 강제로 작동하게 해주면된다.
     그러면 insert문을 날리고 insert문도 롤백해준다.
     테스트 할시에 디비에 반영되는 것을 보고싶으면 잠깐 롤백기능을 해제하면 된다. 해당 매소드 위에 @Rollback(false)를 선언해주면된다.
     */
}