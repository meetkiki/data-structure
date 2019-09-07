package command;


import common.Constant;
import core.AbstractGraph;
import entity.DirectedTrip;
import entity.Town;
import org.junit.Assert;
import utils.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * @author ypt
 * 执行距离运算
 */
public class DistanceCommand extends AbstractCommand implements Command {

    /**
     * 构造
     * @param graph     执行运算的图对象
     */
    public DistanceCommand(AbstractGraph graph) {
        super(graph);
    }

    /**
     * 执行距离计算
     * @param obj   路线，对应list<Town>小镇
     * @return      如果不存在这样的路径，则输出 NO SUCH ROUTE {@link Constant#No_Such_Route}
     *              否则输出路线的距离
     * @see Town
     */
    @Override
    public Object executeAlgorithmInteface(Object obj) {
        Assert.assertNotNull(obj);
        // 集合类型判断
        Assert.assertTrue(obj instanceof List);
        // 不为空判断
        Assert.assertFalse(CollectionUtils.isEmpty((Collection) obj));
        // 集合子类型判断
        Assert.assertEquals(CollectionUtils.findFirst((Collection) obj).getClass(),Town.class);
        List<Town> towns = (List<Town>) obj;
        // 计算路线
        Collection<DirectedTrip> trips = graph.routeTrips(towns);
        if (CollectionUtils.isEmpty(trips)){
            return Constant.No_Such_Route;
        }
        BigDecimal distance = BigDecimal.ZERO;
        for (DirectedTrip trip : trips) {
            distance = distance.add(trip.getDistance());
        }
        return distance;
    }
}
