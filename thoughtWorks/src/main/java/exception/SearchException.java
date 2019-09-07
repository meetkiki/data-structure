package exception;

/**
 * @author tao
 *
 * 搜索路线异常
 */
public class SearchException extends RuntimeException {

    /**
     * 搜索路线异常
     * @param message       info
     */
    public SearchException(String message) {
        super(message);
    }

}
