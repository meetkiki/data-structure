package miniapp.sort_algorithms.countingsort;

import miniapp.abstraction.SortVisual;
import miniapp.view.manoeuvre.AlgoFrame;

public class CountingFrame implements SortVisual {

    @Override
    public String getCnName() {
        return "计数排序";
    }

    @Override
    public void sort(AlgoFrame frame) {

    }

    @Override
    public String methodName() {
        return "Counting Sort";
    }
}
