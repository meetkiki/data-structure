package bean;


import common.Constant;

import java.util.Arrays;

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
     *     而21组数据可以认为是一个机器人，直接决定了智能水平的强弱
     *     -128.0 表示1
     */
    private byte[] genes = new byte[Constant.GENELENGTH];
    /**
     * 个体适应度
     */
    private double fitness = 0.0;


    public WeightIndividual(){
        initIndividual();
    }

    /**
     * / 创建一个随机的 基因个体
     */
    public void initIndividual() {
        for (int i = 0; i < Constant.GENELENGTH; i++) {
            genes[i] = (byte) ((Math.random() * Constant.GENEMAX) - 128);
        }
    }

    @Override
    public String toString() {
        return "WeightIndividual{" +
                "genes=" + Arrays.toString(genes) +
                ", fitness=" + fitness +
                '}';
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

    public static void main(String[] args) {
        double max = Integer.MIN_VALUE,min =Integer.MAX_VALUE;
        for (int i = 0; i < 1000; i++) {
            byte v = (byte) (Math.random() * Constant.GENEMAX - 128);
            max = Double.valueOf(Math.max(max,v));
            min = Double.valueOf(Math.min(min,v));
            System.out.println("v " + v);
        }
        System.out.println("max " + max);
        System.out.println("min " + min);


        WeightIndividual individual = new WeightIndividual();
        System.out.println(individual);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeightIndividual that = (WeightIndividual) o;

        if (Double.compare(that.fitness, fitness) != 0) return false;
        return Arrays.equals(genes, that.genes);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = Arrays.hashCode(genes);
        temp = Double.doubleToLongBits(fitness);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
