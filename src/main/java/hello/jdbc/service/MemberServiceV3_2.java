package hello.jdbc.service;

import hello.jdbc.domain.Member;
import hello.jdbc.repostiory.MemberRepositoryV3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;

/**
 * 트랜잭션 템플릿
 */
@Slf4j
public class MemberServiceV3_2 {


    private final TransactionTemplate txTemplate;
    private final MemberRepositoryV3 memberRepository;

    public MemberServiceV3_2(PlatformTransactionManager transactionManager, MemberRepositoryV3 memberRepository) {
        //빈 주입 안하고 여기서 주입하는 이유는 관례, TransactionTemplate은 class이기 때문에 유연성이 없는데 PlatformTransactionManger를 두면 유연성이 조금 생기기 때문!
        this.txTemplate = new TransactionTemplate();
        this.memberRepository = memberRepository;
    }

    public void accountTransfer(String fromId, String toId, int money) {

        txTemplate.executeWithoutResult((status) -> {
            // try catch 로 sql 예외 터져야 하는데 왜 안터졌지?
            // 나중에 쓸 때는, 람다 내부에서 try catch 해주어야 한다.
                bizLogic(fromId, toId, money);
        });

    }

    private void bizLogic(String fromId, String toId, int money) {
        Member fromMember = memberRepository.findById(fromId);
        Member toMember = memberRepository.findById(toId);

        memberRepository.update(fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }
}
