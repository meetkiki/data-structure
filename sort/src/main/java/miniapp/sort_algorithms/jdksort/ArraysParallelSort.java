package miniapp.sort_algorithms.jdksort;

import miniapp.Enum.LineColorEnum;
import miniapp.abstraction.SortMethod;

import java.util.Arrays;

/**
 * @author Tao
 */
public class ArraysParallelSort implements SortMethod {

    @Override
    public String getCnName() {
        return "JDK ArraysParallel 排序";
    }

    @Override
    public int[] sort(int[] arr) {
        Arrays.parallelSort(arr);
        return arr;
    }

    @Override
    public LineColorEnum lineColor() {
        return LineColorEnum.Tomato;
    }

    @Override
    public String methodName() {
        return "ArraysParallelSort";
    }

    @Override
    public void destory() {
    }
}
