package exception;


/**
 * @author tao
 * 自定义参数异常
 */
public class DataParseException extends RuntimeException {

    /**
     * 参数解析异常
     * @param message       info
     * @param cause         Exception
     */
    public DataParseException(String message,Throwable cause) {
        super(message,cause);
    }
}