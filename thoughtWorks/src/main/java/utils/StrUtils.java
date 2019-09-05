package utils;

public class StrUtils {

    /**
     * 判断字符是否为空
     * @param edge
     * @return
     */
    public static boolean isBlank(String edge){
        return edge == null ? true : edge.trim().length() == 0;
    }


}
