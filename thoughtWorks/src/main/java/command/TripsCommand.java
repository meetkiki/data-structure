package command;

import core.AbstractGraph;
import entity.SearchCondition;
import entity.Trip;
import org.junit.Assert;

import java.util.Collection;

/**
 * @author ypt
 * 执行路线搜索的命令
 */
public class TripsCommand extends AbstractCommand implements Command{

    /**
     * 构造
     * @param graph     执行运算的图对象
     */
    public TripsCommand(AbstractGraph graph) {
        super(graph);
    }

    /**
     * 执行路线搜索运算
     * @param obj      请求参数
     * @return
     */
    @Override
    public Object executeAlgorithmInteface(Object obj) {
        Assert.assertNotNull(obj);
        Assert.assertEquals(obj.getClass(), SearchCondition.class);
        SearchCondition searchCondition = (SearchCondition) obj;
        Collection<Trip> trips = graph.routeTrips(searchCondition);
        return trips.size();
    }
}
