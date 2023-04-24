package hello.jdbc.repostiory.ex;

// 데이터 중복일 경우에만 예외를 던진다.
public class MyDuplicatedExc extends MyDBException{
    public MyDuplicatedExc() {
    }

    public MyDuplicatedExc(String message) {
        super(message);
    }

    public MyDuplicatedExc(String message, Throwable cause) {
        super(message, cause);
    }

    public MyDuplicatedExc(Throwable cause) {
        super(cause);
    }
}
