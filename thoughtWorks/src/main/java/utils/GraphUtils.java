package utils;

import entity.DirectedTrip;
import entity.Town;
import core.Digraph;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

/**
 * 图解析工具类
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
    public void resolve(InputStream in, Digraph digraph) {
        // 读取文本字符
        String str = readStr(in);
        if (StrUtils.isBlank(str)){
            System.err.println("file is empty !");
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
            // 截取字符数据 前两位为小镇标志
            Town from = Town.builder()
                    .withSign(String.valueOf(edge.charAt(0))).build();
            Town to = Town.builder()
                    .withSign(String.valueOf(edge.charAt(1))).build();
            // 剩下为距离
            BigDecimal distance = new BigDecimal(edge.substring(2));
            DirectedTrip directedTrip = DirectedTrip.builder()
                    .withFrom(from)
                    .withTo(to)
                    .withDistance(distance)
                    .build();
            // 向图增加一条旅行边 directedTrip
            digraph.addEdge(directedTrip);
        }
    }

    /**
     * 读取字符文本
     * @param in 目标文件流
     * @return     字符
     */
    private String readStr(InputStream in) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()){
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            // 将二进制字节转化为UTF-8编码的字符串
            return new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
        } catch (IOException ioe) {
            System.err.println(ioe);
        } finally {
            if (in != null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
