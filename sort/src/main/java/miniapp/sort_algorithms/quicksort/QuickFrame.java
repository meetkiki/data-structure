package miniapp.sort_algorithms.quicksort;

import miniapp.abstraction.Constant;
import miniapp.abstraction.SortVisual;
import miniapp.view.AlgoFrame;

public class QuickFrame implements SortVisual {

    /**
     * 快速排序可视化
     * @param frame
     */
    @Override
    public void sort(AlgoFrame frame) {
        int length = frame.length();
        quickSort(frame,0,length-1);
    }

    @Override
    public String methodName() {
        return "Quick Sort";
    }


    /**
     * 插入排序
     * @param frame
     * @param l
     * @param r
     */
    private void InsertSort(AlgoFrame frame, int l, int r){
        for (int i = l;frame.compareLessOrEqual(i, r); i++) {
            // 假定[l,l+1]是有序的 则循环后面的元素找到他们在有序数组中的位置
            for (int j = i; frame.compareMore(j, l) && frame.less(j,j - 1); j--) {
                frame.swap(j,j - 1);
            }
            frame.updateOrdereds(l,i);
        }
        frame.updateOrdereds(l,r + 1);
    }

    private void quickSort(AlgoFrame frame, int l, int r) {
        // 小数组优化
        if (frame.compareMoreOrEqual(l, r - Constant.INSERTSIZE)) {
            InsertSort(frame,l,r);
            return;
        }
        // 获取分区点
        int q = partition(frame,l,r);
        quickSort(frame,l,q-1);
        frame.updateOrdereds(l,q-1);
        quickSort(frame,q+1,r);
        frame.updateOrdereds(q+1,r);
    }

    /**
     * 快速排序优化
     *
     * @param frame
     * @param l
     * @param r
     * @return
     */
    private int partition(AlgoFrame frame, int l, int r) {
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
