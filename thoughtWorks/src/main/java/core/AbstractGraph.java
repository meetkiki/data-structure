package core;

import entity.DirectedTrip;
import entity.Town;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
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
     */
    protected Map<Town, LinkedList<DirectedTrip>> adjs;

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
    public abstract Collection<DirectedTrip> adj(Town v);

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
        buffer.append(vertices + " vertices, " + edges + " edges " + System.lineSeparator());
        for (Map.Entry<Town, LinkedList<DirectedTrip>> entry : adjs.entrySet()) {
            Town town = entry.getKey();
            LinkedList<DirectedTrip> directedTrips = entry.getValue();
            buffer.append(town + ": ");
            for (DirectedTrip trip : directedTrips) {
                buffer.append( trip + " ");
            }
            buffer.append(System.lineSeparator());
        }
        return buffer.toString();
    }

}
