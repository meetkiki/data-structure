package core;

import entity.DirectedTrip;
import entity.SearchCondition;
import entity.Town;
import entity.Trip;
import org.junit.Assert;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * 图
 * @author ypt
 */
public abstract class AbstractGraph {

    /**
     * 顶点数
     */
    protected int vertices;
    /**
     * 有向边
     */
    protected int edges;
    /**
     * 邻近表
     * 这里本身应该是数组+链表结构，
     * 这里将基础数据List<value>改造为Map<DirectedTrip,Distance>
     *  方便在出度中查找需要的路线 并得出距离  利用空间换时间思想 将计算指定路径距离复杂度降为O(1)
     */
    protected Map<Town, Map<DirectedTrip, BigDecimal>> adjs;

    public AbstractGraph(){
        // 创建邻近表
        adjs = new ConcurrentHashMap<>();
    }
    /**
     * 创建一个含有N个顶点但不含有边的图
     * @param vertices  顶点数
     */
    public AbstractGraph(int vertices){
        this();
        // 顶点数
        this.vertices = vertices;
        // 边
        this.edges = 0;
    }


    /**
     * 顶点数
     * @return
     */
    public abstract int vertices();

    /**
     * 边数
     * @return
     */
    public abstract int edges();

    /**
     * 向图增加一条边
     */
    public abstract void addEdge(DirectedTrip trip);

    /**
     * @param v 顶点V
     * @return 返回从v指出的边
     */
    public abstract Map<DirectedTrip, BigDecimal> adj(Town v);

    /**
     * 根据传入的旅行地点 得出实际路线
     *  如果不存在则返回空list
     * @param towns     旅行路线 比如 A , B , C 代表 A->B->C
     * @return
     */
    public abstract Collection<DirectedTrip> routeTrips(List<Town> towns);

    /**
     * 返回所有旅行边
     * @return
     */
    public abstract Collection<DirectedTrip> allTrips();

    /**
     * 根据指定条件搜索路线
     *
     * @param searchCondition       搜索条件
     * @return                      旅行路线对象集
     */
    public Collection<Trip> routeTrips(SearchCondition searchCondition){
        Trip trip = new Trip(searchCondition.getFrom());
        // 找到由起始点指出的路线
        Set<DirectedTrip> trips = this.adj(searchCondition.getFrom()).keySet();
        return searchDFS(trip, searchCondition.getTo(), trips, searchCondition.getStopCondition(), searchCondition.getReturnCondition());
    }

    /**
     * 深度优先搜索   根据指定条件搜索路线
     * @param lastTrip               已经进行的旅行
     * @param end                   目标地
     * @param trips                 当前地点可以旅行的路线
     * @param stopCondition         停止搜索条件
     * @param returnCondition       返回数据的条件
     * @return                      旅行路线对象集
     */
    protected Collection<Trip> searchDFS(Trip lastTrip, Town end, Set<DirectedTrip> trips, Predicate<Trip> stopCondition, Predicate<Trip> returnCondition){
        Assert.assertNotNull("stopCondition may not be null",stopCondition);
        Assert.assertNotNull("returnCondition may not be null",returnCondition);
        List<Trip> resultTrips = new ArrayList<>();
        for (DirectedTrip directedTrip : trips) {
            Trip trip = new Trip(lastTrip);
            trip.addTrip(directedTrip);
            // 目标地
            Town tripTo = directedTrip.getTo();
            // 是否达到目标地 且到达条件要求
            if (tripTo.equals(end) && returnCondition.test(trip)) {
                resultTrips.add(trip);
            }
            // 是否终止
            if (stopCondition.test(trip)) {
                continue;
            }
            // 递归搜索
            resultTrips.addAll(searchDFS(trip, end, this.adj(tripTo).keySet(), stopCondition, returnCondition));
        }
        return resultTrips;
    }


    /**
     * 是否存在顶点
     * @param v 目标顶点
     * @return
     */
    public boolean hasTown(Town v){
        return this.adjs.containsKey(v);
    }
    /**
     * 对象的字符串表示
     * @return
     */
    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(vertices() + " vertices, " + edges() + " edges " + System.lineSeparator());
        for (Map.Entry<Town, Map<DirectedTrip,BigDecimal>> entry : adjs.entrySet()) {
            Town town = entry.getKey();
            Set<DirectedTrip> directedTrips = entry.getValue().keySet();
            buffer.append(town + ": ");
            for (DirectedTrip trip : directedTrips) {
                buffer.append( trip + " ");
            }
            buffer.append(System.lineSeparator());
        }
        return buffer.toString();
    }

}
