package miniapp.sort_algorithms.bubblesort;

import miniapp.abstraction.SortVisual;
import miniapp.view.manoeuvre.AlgoFrame;

public class BubbleFrame implements SortVisual {

    @Override
    public void sort(AlgoFrame frame) {
        // 初始化
        int N = frame.length();
        // 排序好空间从右边扩散
        for (int i = 0;frame.compareLess(i, N); i++) {
            // 冒泡排序思想为将未排序区间的最大值往最后冒
            for (int j = N - 1; frame.compareMore(j, i); j --) {
                // 比较左区间内当前值 找出最大值往右冒泡
                if (frame.less(j,j - 1)){
                    frame.swap(j,j - 1);
                }
            }
            // 展示格式化后数据
            frame.updateOrdereds(i + 1);
        }
    }

    @Override
    public String methodName() {
        return "Bubble Sort";
    }

    @Override
    public String getCnName() {
        return "冒泡排序";
    }
}
