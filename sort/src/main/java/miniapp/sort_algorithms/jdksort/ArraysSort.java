package miniapp.sort_algorithms.jdksort;

import miniapp.Enum.LineColorEnum;
import miniapp.abstraction.SortMethod;

import java.util.Arrays;

/**
 * @author Tao
 */
public class ArraysSort implements SortMethod {

    @Override
    public String getCnName() {
        return "JDK Arrays Sort";
    }

    @Override
    public int[] sort(int[] arr) {
        Arrays.sort(arr);
        return arr;
    }

    @Override
    public LineColorEnum lineColor() {
        return LineColorEnum.BlueGrey;
    }

    @Override
    public String methodName() {
        return "ArraysSort";
    }

    @Override
    public void destory() {
    }
}
