package miniapp.sort_algorithms.quicksort;

import miniapp.abstraction.SortVisual;
import miniapp.view.AlgoFrame;

public class DualPivotQuickFrame implements SortVisual {


    @Override
    public void sort(AlgoFrame frame) {
        int length = frame.length();
        dualPivotQuickSort(frame,0,length - 1);
    }

    private void dualPivotQuickSort(AlgoFrame frame, int l, int r) {


    }

    @Override
    public String methodName() {
        return "DualPivotQuick Sort";
    }
}
