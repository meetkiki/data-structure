package graph;

import domain.DirectedTrip;
import domain.Town;
import utils.GraphUtils;

import java.util.*;

/**
 * 图
 * @author Tao
 */
public abstract class Graph {

    /**
     * 顶点数
     */
    protected int V;
    /**
     * 有向边
     */
    protected int E;
    /**
     * 邻近表
     */
    protected Map<Town,LinkedList<DirectedTrip>> adjs;

    public Graph(){
        // 创建邻近表
        adjs = new HashMap<>();
    }
    /**
     * 创建一个含有N个顶点但不含有边的图
     * @param V
     */
    public Graph(int V){
        this();
        // 顶点数
        this.V = V;
        // 边
        this.E = 0;
    }


    /**
     * 顶点数
     * @return
     */
    public abstract int V();

    /**
     * 边数
     * @return
     */
    public abstract int E();

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
     * 对象的字符串表示
     * @return
     */
    @Override
    public String toString(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(V +" vertices, " + E + " edges \n");
        for (Map.Entry<Town, LinkedList<DirectedTrip>> entry : adjs.entrySet()) {
            Town town = entry.getKey();
            LinkedList<DirectedTrip> directedTrips = entry.getValue();
            buffer.append(town + ": ");
            for (DirectedTrip trip : directedTrips) {
                buffer.append( trip + " ");
            }
            buffer.append("\n");
        }
        return buffer.toString();
    }

}
