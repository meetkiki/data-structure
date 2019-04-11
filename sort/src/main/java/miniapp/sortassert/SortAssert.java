package miniapp.sortassert;

import com.sun.istack.internal.Nullable;

public final class SortAssert {

    public static void mustTrue(boolean b) throws RuntimeException {
        mustTrue(b,"[Assertion failed] - the object argument must be true");
    }


    public static void mustTrue(@Nullable boolean b, String message) throws RuntimeException {
        if (b == false){
            throw new RuntimeException(message);
        }
    }

    /**
     * Assert that an object is {@code null}.
     * <pre class="code">Assert.isNotNull(value, "The value must be null");</pre>
     *
     * @param object  the object to check
     * @param message the exception message to use if the assertion fails
     * @throws RuntimeException if the object is not {@code null}
     */
    public static void isNotNull(@Nullable Object object, String message) throws RuntimeException {
        if (object == null) {
            throw new RuntimeException(message);
        }
    }

    public static void isNotNull(@Nullable Object object) throws RuntimeException {
        isNotNull(object, "[Assertion failed] - the object argument must be null");
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
