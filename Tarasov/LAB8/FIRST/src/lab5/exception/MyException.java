package lab5.exception;

public class MyException extends RuntimeException {
    public MyException (String msg) {
        super(msg);

    }
    public MyException () {
        super();
    }
}


