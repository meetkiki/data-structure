package utils;

import org.junit.Assert;

import java.util.Arrays;
import java.util.List;

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

    /**
     * 将str对象转化为List
     * @param src      String 对象
     * @param regex    分隔符
     * @return
     */
    public static List<String> splitStr(String src,String regex){
        Assert.assertFalse(isBlank(src));
        String[] split = src.split(regex);
        return Arrays.asList(split);
    }

}
