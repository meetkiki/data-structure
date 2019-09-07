package command;


import common.Constant;
import core.AbstractGraph;
import entity.DirectedTrip;
import entity.Town;
import org.junit.Assert;
import utils.CommonUtils;

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
     * @param obj   参数
     * @return
     */
    @Override
    public Object executeAlgorithmInteface(Object obj) {
        // 不为空判断
        Assert.assertFalse(CommonUtils.isEmpty((Collection) obj));
        // 集合类型判断
        Assert.assertEquals(obj.getClass(), List.class);
        // 集合子类型判断
        Assert.assertEquals(CommonUtils.findFirst((Collection) obj).getClass(),Town.class);
        List<Town> towns = (List<Town>) obj;
        Collection<DirectedTrip> trips = graph.routeTrips(towns);
        if (CommonUtils.isEmpty(trips)){
            return Constant.No_Such_Route;
        }
        BigDecimal distance = BigDecimal.ZERO;
        for (DirectedTrip trip : trips) {
            distance = distance.add(trip.getDistance());
        }
        return distance;
    }
}
