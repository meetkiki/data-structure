package utils;

import abstraction.Graph;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Tao
 */
public class GraphUtils {

    private static Scanner scanner;

    public static int readInt(InputStream in){
        if (scanner == null){
            scanner = new Scanner(in);
        }
        return scanner.nextInt();
    }


    /**
     * 计算顶点v的度数
     * @param graph
     * @param v
     * @return
     */
    public static int degree(Graph graph, int v){
        int degree = 0;
        for (Integer w : graph.adj(v)) {
            degree++;
        }
        return degree;
    }


    /**
     * 所有顶点的最大度数
     * @param graph
     * @return
     */
    public static int maxDegree(Graph graph){
        int max = 0;
        for (int v = 0; v < graph.V(); v++) {
            Math.max(degree(graph,v),max);
        }
        return max;
    }

    /**
     * 所有顶点的平均度数
     * @param graph
     * @return
     */
    public static double avgDegree(Graph graph){
        return 2.0 * graph.E() / graph.V();
    }


    /**
     * 计算自环的个数
     * @param graph
     * @return
     */
    public static int numberOfSelfLoops(Graph graph){
        int count = 0;
        // 顶点
        for (int v = 0; v < graph.V(); v++) {
            // 每条顶点的度数
            for (Integer w : graph.adj(v)) {
                if (v == w){
                    count++;
                }
            }
        }
        return count / 2;
    }


}
