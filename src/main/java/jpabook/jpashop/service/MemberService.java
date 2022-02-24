package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
@RequiredArgsConstructor               // 생성자
public class MemberService {

    /**MemberRepository 주입 (생성자 이용)*/
    private final MemberRepository memberRepository;  // final쓰는 걸 권장 - final쓰면 생성자를 생성해야되므로 생성자 안 쓰는 실수 면할 수 있다.

    /**회원 가입*/
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);  //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }
    //중복 회원 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**회원 전체 조회*/
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    /**회원 id로 한 명 조회*/
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
/**
 * 1.  @Transactional : JPA의 모든 데이터 변경,로직들은 가급적 트랜잭션안에서 실행이되어야함
 *     클래스 레벨에(위에)  @Transactional 을 쓰면 각 public 매서드들은 기본적으로 트랜잭션에 걸려 들어간다.
 *     단순히 조회(읽기)하는 로직이면 그 위에 @Transactional(readOnly=true)하면 JPA가 더 최적화해준다.
 *     조회가 아닌 것은 그냥 @Transactional 쓰면된다. 조회 로직 매서드가 많다면 전체 클래스 위에 @Transactional(readOnly=true) 설정하고
 *     쓰기(등록)인 곳에만  @Transactional로 써주면된다.
 *
 * 2. 필드 주입의 단점: 테스트할시에 변경 못한다. 필드에 private로 정의도어있어서 변경하기 어려움.
 *    setter주입의 단점 : 스프링이 바로 주입하는게 아님, 애플리케이션 돌아가는 시점에 누군가가 변경할 수 있음
 *    setter 주입 장점: 테스트할 시에 설정정가능함.
 *    -> 생성자 주입 권장 : 스프링이 생성할 때 바로 주입. 중간에 set으로 변경될 일도 없고 테스트케이스에서 생성할 때 반드시 주입 부분을 설정해줘야
 *    잘 생성되므로 놓칠 일도 없다.
 *    생성자가 하나일 경우에는 @Autowired 생략 가능. & 롬복의 @AllArgsConstructor또는 @RequiredArgsConstructor을 써서 생성자 생략 가능.
 *    (@AllArgsConstructor- 필드에 정의되어있는 것들 모두 파라미터로하는 생성자)
 *    (@RequiredArgsConstructor - 필드에서 final로 정의되어 있는 것들만 파라미터로 하는 생성자)
 * */
