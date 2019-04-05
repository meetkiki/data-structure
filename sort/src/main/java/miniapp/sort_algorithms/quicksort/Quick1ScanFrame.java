package miniapp.sort_algorithms.quicksort;

import miniapp.view.AlgoFrame;

public class Quick1ScanFrame extends QuickFrame {

    @Override
    protected int partition(AlgoFrame frame, int l, int r) {
        int i = l;
        for (int j = l; frame.compareLessOrEqual(j, r - 1); j++) {
            if (frame.less(j,r)){
                frame.swap(i++,j);
            }
        }
        frame.swap(i,r);
        return i;
    }
}
