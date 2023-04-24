package hello.jdbc.service;

import hello.jdbc.repostiory.MemberRepositoryV3;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceV3_1Test {

    public static final String MEMBER_A = "memberA";
    public static final String MEMBER_B = "memberB";
    public static final String MEMBER_C = "memberC";

    private MemberRepositoryV3 memberRepository;
    private MemberServiceV3_1 memberServiceV3;

    @BeforeEach
    void before(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        memberRepository = new MemberRepositoryV3(dataSource);

        // datasource를 넘겨주어야 한다. 데이터스소에서 커넥션을 찾아오기 때문이다!
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        memberServiceV3 = new MemberServiceV3_1(transactionManager, memberRepository);
    }

    @AfterEach
    void atfter() {
        memberRepository.delete(MEMBER_A);
        memberRepository.delete(MEMBER_B);
        memberRepository.delete(MEMBER_C);
    }

}