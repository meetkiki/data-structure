package bean;

import common.Constant;
import junit.framework.TestCase;
import org.junit.Test;
import utils.BoardUtil;

import java.util.Arrays;

public class WeightIndividualTest extends TestCase {


    @Test
    public void testConvert() {
        double max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            byte v = (byte) (Math.random() * Constant.GENEMAX);
            max = Math.max(max, v);
            min = Math.min(min, v);
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
        BoardUtil.graysToGens(individual.getGrays(), srcs, genes);
        System.out.println(Arrays.toString(genes));
        System.out.println(Arrays.toString(srcs));

        byte[] genes1 = new byte[Constant.GENELENGTH];
        byte[] grays1 = new byte[Constant.GENELENGTH];
        BoardUtil.srcsToGens(individual.getSrcs(), grays1, genes1);
        System.out.println(Arrays.toString(genes1));
        System.out.println(Arrays.toString(grays1));
        int[] srcs1 = new int[Constant.DATALENGTH];
        BoardUtil.graysToGens(grays1, srcs1, genes1);
        System.out.println(Arrays.toString(srcs1));
    }
}