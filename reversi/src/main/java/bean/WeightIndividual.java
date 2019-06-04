package bean;


import common.Constant;
import utils.BoardUtil;
import utils.CreatNameUtils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static common.Constant.BITVALUE;

/**
 * 个体
 * @author Tao
 */
public class WeightIndividual {

    /**
     * 默认基因编码
     */
    public static final WeightIndividual DEFAULT = new WeightIndividual();

    /**
     * 采用二进制编码
     *  每个估值函数都在[-1,1]之间 将长度255等分
     *  -1,-127/128,-126/128,...,-1/128,0,1/128,...,127/128,1
     *  可以用一个字节表示
     *  -1,-127,0,127,将除以128可以得到[-1,1]的权值
     *  而一个棋局右三个阶段（排除结局和终局一样） 正好需要21组数据
     *     而21组(8位)数据可以认为是一个机器人，直接决定了智能水平的强弱
     *     -128.0 表示1
     *  这里存每一位的8位二进制数据
     *  比如 0000 0001 表示1  0000 1001 表示9
     */
    private byte[] genes = new byte[Constant.GENELENGTH];

    private int[] srcs = new int[Constant.DATALENGTH];
    /**
     * 对应基因编码的格雷编码
     */
    private byte[] grays = new byte[Constant.GENELENGTH];
    /**
     * 个体适应度
     */
    private double fitness = 0.0;
    /**
     * 幸存概率
     */
    private double lucky = 0.0;
    /**
     * 累计概率
     */
    private double clucky = 0.0;
    /**
     * 名称 用作区分
     */
    private String name;


    public WeightIndividual(){
        initIndividual();
    }

    /**
     * / 创建一个随机的 基因个体
     */
    public void initIndividual() {
        for (int i = 0; i < Constant.DATALENGTH; i++) {
            int src = (int) (Math.random() * Constant.GENEMAX);
            srcs[i] = src;
            byte[] byte8 = BoardUtil.byteToByte8(src);
            // 基因组转换
            for (int i1 = (i * BITVALUE),i2 = 0; i2 < byte8.length; i2++) {
                genes[i1 + i2] = byte8[i2];
            }
            // 格雷码
            byte[] byteGray8 = BoardUtil.byteToByte8(BoardUtil.intToGray(src));
            // 基因组转换 转化为格雷码
            for (int i1 = (i * BITVALUE),i2 = 0; i2 < byteGray8.length; i2++) {
                grays[i1 + i2] = byteGray8[i2];
            }
        }
        this.name = CreatNameUtils.getChineseName();
    }

    /**
     * 人工编码
     * @param grays
     */
    public WeightIndividual(byte[] grays) {
        this.grays = grays;
        // 转换编码
        BoardUtil.graysToGens(grays,srcs,genes);
        this.name = CreatNameUtils.getChineseName();
    }

    public byte[] getGrays() {
        return grays;
    }

    public void setGrays(byte[] grays) {
        this.grays = grays;
    }

    @Override
    public String toString() {
        return "WeightIndividual{" +
                "genes=" + Arrays.toString(genes) +
                ", srcs=" + Arrays.toString(srcs) +
                ", grays=" + Arrays.toString(grays) +
                ", fitness=" + fitness +
                ", name='" + name + '\'' +
                '}';
    }

    public int[] getSrcs() {
        return srcs;
    }

    public void setSrcs(int[] srcs) {
        this.srcs = srcs;
    }

    public byte[] getGenes() {
        return genes;
    }

    public void setGenes(byte[] genes) {
        this.genes = genes;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getLucky() {
        return lucky;
    }

    public void setLucky(double lucky) {
        this.lucky = lucky;
    }

    public double getClucky() {
        return clucky;
    }

    public void setClucky(double clucky) {
        this.clucky = clucky;
    }

    public static void main(String[] args) {
        double max = Integer.MIN_VALUE,min =Integer.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            byte v = (byte) (Math.random() * Constant.GENEMAX);
            max = Double.valueOf(Math.max(max,v));
            min = Double.valueOf(Math.min(min,v));
            System.out.println("v " + v);
        }
        System.out.println("max " + max);
        System.out.println("min " + min);

        WeightIndividual individual = new WeightIndividual();
        System.out.println(individual);
        System.out.println(Arrays.toString(individual.getGrays()));
        System.out.println(Arrays.toString(individual.getSrcs()));
        System.out.println(Arrays.toString(individual.getGenes()));
        byte[] genes = new byte[Constant.GENELENGTH];
        int[] srcs = new int[Constant.DATALENGTH];
        BoardUtil.graysToGens(individual.getGrays(),srcs,genes);
        System.out.println(Arrays.toString(genes));
        System.out.println(Arrays.toString(srcs));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeightIndividual that = (WeightIndividual) o;
        return Arrays.equals(genes, that.genes);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(genes);
    }


    public static String printAllName(List<WeightIndividual> individualList){
        Iterator<WeightIndividual> iterator = individualList.iterator();
        StringBuffer buffer = new StringBuffer();
        if (iterator.hasNext()) buffer.append("[");
        while (iterator.hasNext()){
            WeightIndividual individual = iterator.next();
            buffer.append(individual.getName());
            if (!iterator.hasNext()) buffer.append("]");
            else buffer.append(" ,");
        }
        return buffer.toString();
    }
}
