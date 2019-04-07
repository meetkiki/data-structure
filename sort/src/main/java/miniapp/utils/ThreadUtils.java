package miniapp.utils;

public class ThreadUtils {

    public static void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (Exception e) {
            Thread.interrupted();
            e.printStackTrace();
        }
    }


}
