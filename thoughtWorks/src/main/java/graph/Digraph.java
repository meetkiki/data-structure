package graph;


import domain.DirectedTrip;
import domain.Town;
import utils.GraphUtils;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Set;

/**
 * 有向图
 */
public class Digraph extends Graph{

    /**
     * 通过输入文件构建有向图
     * @param file 输入流
     */
    public Digraph(String file) throws IOException {
        super();
        GraphUtils.resolveFile(file,this);
    }

    /**
     * 创建一个含有N个顶点但不含有边的图
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
        // 顶点数
        E++;
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
        Set<Town> towns = this.adjs.keySet();
        for (Town town : towns) {

        }
        return list;
    }
}
