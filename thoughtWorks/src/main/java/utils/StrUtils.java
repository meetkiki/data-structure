package utils;

/**
 * 字符串处理工具类
 * @author tao
 */
public class StrUtils {

    /**
     * 判断字符是否为空
     * @param edge
     * @return
     */
    public static boolean isBlank(String edge){
        return edge == null || edge.trim().length() == 0;
    }


}
