package hello.jdbc.connection;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

class DBConnectionUtilTest {

    @Test
    void connection() {
        Connection con = DBConnectionUtil.getConnection();
        Assertions.assertThat(con).isNotNull();
    }
}
