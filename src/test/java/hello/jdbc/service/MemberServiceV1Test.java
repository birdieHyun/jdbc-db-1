package hello.jdbc.service;

import hello.jdbc.connection.ConnectionConst;
import hello.jdbc.domain.Member;
import hello.jdbc.repostiory.MemberRepositoryV1;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static hello.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceV1Test {

    public static final String Member_A = "member123";
    public static final String Member_B = "member1234";
    public static final String Member_C = "ex";


    private MemberRepositoryV1 memberRepositoryV1;
    private MemberServiceV1 memberServiceV1;

    @BeforeEach
    void before() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);
        memberRepositoryV1 = new MemberRepositoryV1(dataSource);
        memberServiceV1 = new MemberServiceV1(memberRepositoryV1);
    }

    @AfterEach
    void after() {
        memberRepositoryV1.delete(Member_A);
        memberRepositoryV1.delete(Member_B);
        memberRepositoryV1.delete(Member_C);
    }

    @Test
    @DisplayName("정상 이체")
    void accountTranser() throws SQLException {
        //given
        Member memberA = new Member(Member_A, 10000);
        Member memberB = new Member(Member_B, 10000);

        memberRepositoryV1.save(memberA);
        memberRepositoryV1.save(memberB);

        //when
        memberServiceV1.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000);


        //then
        Member fromA = memberRepositoryV1.findById(memberA.getMemberId());
        Member toB = memberRepositoryV1.findById(memberB.getMemberId());
        assertThat(fromA.getMoney()).isEqualTo(8000);
        assertThat(toB.getMoney()).isEqualTo(12000);

    }

    @Test
    @DisplayName("이체중 예외")
    void accountTranserEx() throws SQLException {
        //given
        Member memberA = new Member(Member_A, 10000);
        Member memberB = new Member(Member_C, 10000);

        memberRepositoryV1.save(memberA);
        memberRepositoryV1.save(memberB);

        //when
        assertThatThrownBy(() -> memberServiceV1.accountTransfer(memberA.getMemberId(), memberB.getMemberId(), 2000))
                .isInstanceOf(IllegalStateException.class);


    }
}