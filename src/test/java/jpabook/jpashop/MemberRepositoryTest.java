package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;


    @Test
    @Transactional              //트랜잭션이 테스트 케이스 안에 있을 때는 테스트 끝나고 항상 롤백해줌.-> 테스트시 데이터가 남아있으면 다른 테스트에 지장을 주므로
    @Rollback(false)             //롤백을 false해주면 데이터가 들어가는 것을 h2의 테이블로 확인할 수 있다.
    public void testMember() {
        //given
        Member member = new Member();
        member.setUsername("memberA");
       //when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);
       //then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }
}