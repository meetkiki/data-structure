package miniapp.sort_algorithms.selectionsort;

import miniapp.abstraction.SortVisual;
import miniapp.view.manoeuvre.AlgoFrame;

public class SelectionFrame implements SortVisual {

    @Override
    public void sort(AlgoFrame frame) {
        for(int i = 0;frame.compareLess(i ,frame.length()); i ++ ){
            // 寻找[i, n)区间里的最小值的索引
            int minIndex = i;
            for(int j = i + 1; frame.compareLess(j , frame.length());j ++){
                if(frame.less(j,minIndex)){
                    minIndex = j;
                }
            }
            frame.swap(i , minIndex);
            // 更细排序空间
            frame.updateOrdereds(i + 1);
        }
    }

    @Override
    public String methodName() {
        return "Selection Sort";
    }

    @Override
    public String getCnName() {
        return "选择排序";
    }
}
