package utils;

import domain.DirectedTrip;
import domain.Town;
import graph.Digraph;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Tao
 */
public class GraphUtils {

    private static volatile GraphUtils graphUtils;

    private GraphUtils(){}

    /**
     * 双重校验锁单例模式
     */
    public GraphUtils getInstance(){
        if (graphUtils == null){
            synchronized (GraphUtils.class){
                if (graphUtils == null){
                    graphUtils = new GraphUtils();
                }
            }
        }
        return graphUtils;
    }

    /**
     * 解析文件数据
     * @param name
     * @param digraph
     * @throws IOException
     */
    public static void resolveFile(String name, Digraph digraph) throws IOException {
        String str = readStr(name);
        String[] edges = str.split(",");
        for (String edge : edges){
            if (StrUtils.isBlank(edge)){
                continue;
            }
            edge = edge.trim();
            if (edge.length() < 3){
                System.err.println(String.format("illegal graph format : %s",edge));
                continue;
            }
            Town from = new Town(String.valueOf(edge.charAt(0)));
            Town to = new Town(String.valueOf(edge.charAt(1)));
            BigDecimal distance = new BigDecimal(edge.substring(2));
            DirectedTrip directedTrip = new DirectedTrip();
            directedTrip.setFrom(from);
            directedTrip.setTo(to);
            directedTrip.setDistance(distance);
            digraph.addEdge(directedTrip);
        }
    }

    /**
     * 读取字符文本
     * @param file 目标文件
     * @return     字符
     * @throws IOException
     */
    public static String readStr(String file) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(file));
        return new String(bytes, StandardCharsets.UTF_8);
    }



}
