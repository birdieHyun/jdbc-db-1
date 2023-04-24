package hello.jdbc.exception.basic;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

@Slf4j
public class CheckedTest {


    @Test
    void checked_catch() {

        Service service = new Service();
        service.callCatch();
    }


    @Test
    void no_catch() {
        Service service = new Service();
        Assertions.assertThatThrownBy(() -> service.noCatch())
                .isInstanceOf(MyCheckedTest.class);
    }

    static class MyCheckedTest extends Exception{
        public MyCheckedTest(String message) {
            super(message);
        }
    }

    static class Service{
        Repository repository = new Repository();
        public void callCatch(){
            try {
                repository.call();
            } catch (MyCheckedTest e) {
                log.info("예외 처리 , message = {}", e.getMessage(), e);
            }
        }

        public void noCatch() throws MyCheckedTest {
            repository.call();
        }

    }

    static class Repository{
        public void call() throws MyCheckedTest {
            throw new MyCheckedTest("ex");
        }
    }
}
