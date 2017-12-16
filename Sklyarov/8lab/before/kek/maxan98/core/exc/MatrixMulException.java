package kek.maxan98.core.exc;

/**
 * Created by MaksSklyarov on 22.03.17.
 */
public class MatrixMulException extends Exception{
        private int errorCode;

        public MatrixMulException()
        {
            this(0, "Ошибка при умножении матриц");
        }

        public MatrixMulException(int errorCode, String message)
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
