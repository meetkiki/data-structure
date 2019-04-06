package miniapp.sort_algorithms.insertionsort;

import miniapp.abstraction.SortVisual;
import miniapp.view.manoeuvre.AlgoFrame;

public class InsertionFrame implements SortVisual {

    @Override
    public String getCnName() {
        return "插入排序";
    }

    /**
     * 可视化插入排序
     *
     * @param frame
     */
    @Override
    public void sort(AlgoFrame frame) {
        int n = frame.length();
        // 假定从1开始前面都是有序的 从无序的每个元素中依次插入到有序的数组中达到完全有序
        for (int i = 1; frame.compareLess(i, n); i++) {
            // 判断数据当前值是否小于已排序好值，找到当前数据合适的位置
            for (int j = i;frame.compareMore(j,0) && frame.less(j,j - 1); j--) {
                frame.swap(j,j - 1);
                frame.updateOrdereds(i + 1);
            }
        }
    }

    @Override
    public String methodName() {
        return "Insert sort";
    }

}
