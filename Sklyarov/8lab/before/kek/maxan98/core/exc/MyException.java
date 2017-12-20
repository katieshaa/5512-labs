package kek.maxan98.core.exc;

/**
 * Created by MaksSklyarov on 19.02.17.
 */
public class MyException extends Exception {
    private int errorCode;

    public MyException(String message)
    {
        this(0, message);
    }

    public MyException(int errorCode, String message)
    {
        // Вызываем конструктор предка
        super(message);
        // Добавляем инициализацию своего поля
        this.errorCode = errorCode;
    }

    // Метод для получения кода ошибки
    public int getErrorCode()
    {
        return errorCode;
    }
}
