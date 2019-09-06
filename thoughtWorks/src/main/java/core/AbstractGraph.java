package core;

import entity.DirectedTrip;
import entity.Town;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 图
 * @author Tao
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
     *  方便在出度中查找需要的路线 并得出距离  利用空间换时间思想 将计算指定路径复杂度降为O(1)
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
