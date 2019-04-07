package miniapp.sortassert;

import com.sun.istack.internal.Nullable;

public final class SortAssert {

    public static void isTrue(boolean b) throws RuntimeException {
        isTrue(b,"[Assertion failed] - the object argument must be true");
    }


    public static void isTrue(@Nullable boolean b, String message) throws RuntimeException {
        if (b == false){
            throw new RuntimeException(message);
        }
    }

    /**
     * Assert that an object is {@code null}.
     * <pre class="code">Assert.isNull(value, "The value must be null");</pre>
     *
     * @param object  the object to check
     * @param message the exception message to use if the assertion fails
     * @throws RuntimeException if the object is not {@code null}
     */
    public static void isNull(@Nullable Object object, String message) throws RuntimeException {
        if (object != null) {
            throw new RuntimeException(message);
        }
    }

    public static void isNull(@Nullable Object object) throws RuntimeException {
        isNull(object, "[Assertion failed] - the object argument must be null");
    }

    /**
     * Assert that an object is not {@code null}.
     * <pre class="code">Assert.notNull(clazz, "The class must not be null");</pre>
     *
     * @param object  the object to check
     * @param message the exception message to use if the assertion fails
     * @throws RuntimeException if the object is {@code null}
     */
    public static void notNull(@Nullable Object object, String message) throws RuntimeException {
        if (object == null) {
            throw new RuntimeException(message);
        }
    }


}
