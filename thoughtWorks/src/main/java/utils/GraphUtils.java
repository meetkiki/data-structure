package utils;

import domain.DirectedTrip;
import domain.Town;
import graph.Digraph;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

/**
 * @author Tao
 */
public class GraphUtils {

    private static volatile GraphUtils graphUtils;

    private GraphUtils(){}

    /**
     * 双重校验锁单例模式
     */
    public static GraphUtils getInstance(){
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
     * @param in        输入流
     * @param digraph   有向图对象
     */
    public void resolveInputStream(InputStream in, Digraph digraph) {
        String str = readStr(in);
        if (StrUtils.isBlank(str)){
            System.err.println(String.format("file is empty !"));
            return;
        }
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
     * @param in 目标文件流
     * @return     字符
     * @throws IOException
     */
    private String readStr(InputStream in) {
        ByteArrayOutputStream os = null;
        try {
            os = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = -1;
            while ((len = in.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            os.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new String(os.toByteArray(), StandardCharsets.UTF_8);
    }



}
