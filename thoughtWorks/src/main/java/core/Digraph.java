package core;


import entity.DirectedTrip;
import entity.Town;
import utils.GraphUtils;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 有向图
 * @author tao
 */
public class Digraph extends AbstractGraph {

    /**
     * 通过输入流构建有向图
     * @param in 输入流
     */
    public Digraph(InputStream in) {
        super();
        GraphUtils.getInstance().resolve(in,this);
    }

    /**
     * 创建一个含有N个顶点但不含有边的有向图
     * @param V 顶点数
     */
    public Digraph(int V) {
        super(V);
    }

    /**
     * 顶点数
     * @return
     */
    @Override
    public int vertices() {
        return this.vertices;
    }

    /**
     * 边数
     * @return
     */
    @Override
    public int edges() {
        return this.edges;
    }

    /**
     * 向图增加一条边 v -> m
     * @param trip 边的信息
     */
    @Override
    public void addEdge(DirectedTrip trip) {
        // 增加起点的边
        this.adj(trip.getFrom()).put(trip,trip.getDistance());
        // 边数
        this.edges++;
        // 顶点数
        this.vertices = this.adjs.size();
    }

    /**
     * 返回所有由v指出的小镇
     * @param v
     * @return
     */
    @Override
    public Map<DirectedTrip, BigDecimal> adj(Town v) {
        // 根据v town作为key获取链表,如果为空则初始化邻近表的链表
        return this.adjs.computeIfAbsent(v, k -> new ConcurrentHashMap<>());
    }

    /**
     * 根据传入的旅行地点 得出实际路线
     * @param towns     旅行路线 比如 A , B , C 代表 A->B->C
     * @return          存在返回路线，如果不存在则返回空list
     */
    @Override
    public Collection<DirectedTrip> routeTrips(List<Town> towns) {
        if (towns == null || towns.size() == 0)
            return Collections.EMPTY_LIST;
        List<DirectedTrip> result = new LinkedList<>();
        Iterator<Town> it = towns.iterator();
        Town from = it.next();
        while (it.hasNext()){
            Town next = it.next();
            Map<DirectedTrip, BigDecimal> tripsMap = this.adj(from);
            if (tripsMap.isEmpty()){
                return Collections.EMPTY_LIST;
            }
            DirectedTrip directedTrip = DirectedTrip.builder().withFrom(from).withTo(next).build();
            BigDecimal distance = tripsMap.get(directedTrip);
            if (distance == null){
                return Collections.EMPTY_LIST;
            }
            directedTrip.setDistance(distance);
            result.add(directedTrip);
            from = next;
        }
        return result;
    }

    /**
     * 返回所有旅行边
     * @return
     */
    @Override
    public Collection<DirectedTrip> allTrips() {
        LinkedList<DirectedTrip> list = new LinkedList<>();
        // 为保证初始化临近表的链表 统一调用adj方法得到链表对象
        Set<Town> towns = this.adjs.keySet();
        for (Town town : towns) {
            list.addAll(this.adj(town).keySet());
        }
        return list;
    }
}
