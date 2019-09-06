package core;


import entity.DirectedTrip;
import entity.Town;
import utils.GraphUtils;

import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

/**
 * 有向图
 */
public class Digraph extends Graph{

    /**
     * 通过输入流构建有向图
     * @param in 输入流
     */
    public Digraph(InputStream in) {
        super();
        GraphUtils.getInstance().resolveInputStream(in,this);
    }

    /**
     * 创建一个含有N个顶点但不含有边的图
     * @param V 顶点数
     */
    private Digraph(int V) {
        super(V);
    }

    /**
     * 顶点数
     * @return
     */
    @Override
    public int V() {
        return this.V;
    }

    /**
     * 边数
     * @return
     */
    @Override
    public int E() {
        return this.E;
    }

    /**
     * 向图增加一条边 v -> m
     * @param trip 边的信息
     */
    @Override
    public void addEdge(DirectedTrip trip) {
        // 增加起点的边
        this.adj(trip.getFrom()).add(trip);
        // 边数
        E++;
        // 顶点数
        V = this.adjs.size();
    }

    /**
     * 返回所有由v指出的小镇
     * @param v
     * @return
     */
    @Override
    public Collection<DirectedTrip> adj(Town v) {
        // 初始化邻近表的链表
        return this.adjs.computeIfAbsent(v, k -> new LinkedList<>());
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
            list.addAll(this.adj(town));
        }
        return list;
    }
}
