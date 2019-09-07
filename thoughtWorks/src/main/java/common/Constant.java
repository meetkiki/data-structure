package common;

import java.math.BigDecimal;

/**
 * @author ypt
 * @ClassName Constant
 * @Description 常量
 * @date 2019/9/6 17:21
 */
public class Constant {

    /**
     * 距离常量标识
     */
    public static final String DISTANCE = "distance";
    public static final String DURATION = "duration";
    public static final String SHORTEST = "shortest";
    public static final String TRIPS = "trips";

    /**
     * 求取最小值时使用
     * 无法连通的路径 距离为MaxInt 距离默认值
     */
    public static final BigDecimal NoSuch_Max_Dist = new BigDecimal(Double.MAX_VALUE);

    /**
     * 求取最大值时使用
     * 无法连通的路径 距离为MinInt 距离默认值 (仅适合无环图)
     */
    public static final BigDecimal NoSuch_Min_Dist = new BigDecimal(-Double.MIN_VALUE);

    /**
     * 无法连通的路径 提示字符
     */
    public static final String No_Such_Route = "NO SUCH ROUTE";

    
}
