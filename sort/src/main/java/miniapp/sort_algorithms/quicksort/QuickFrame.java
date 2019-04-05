package miniapp.sort_algorithms.quicksort;

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

    private void quickSort(AlgoFrame frame, int l, int r) {
        if (frame.compareMoreOrEqual(l,r)) return;
        // 获取分区点
        int q = partition(frame,l,r);
        quickSort(frame,l,q-1);
        frame.updateOrdereds(l,q-1);
        quickSort(frame,q+1,r);
        frame.updateOrdereds(q+1,r);
    }

    protected int partition(AlgoFrame frame, int l, int r) {
        int i = l,j = r + 1,v = frame.get(l);
        while (true){
            /**
             * 找到第一个大于arr[i]的值
             */
            while (frame.compare(frame.get(++i), v) < 0){
                if (frame.compareEqual(i, r)) break;
            }
            /**
             * 找到第一个小于arr[i]的值
             */
            while (frame.compare(v,frame.get(--j)) < 0){
                if (frame.compareEqual(j, l)) break;
            }
            if (frame.compareMoreOrEqual(i, j)) break;
            frame.swap(i,j);
        }
        frame.swap(l,j);
        return j;
    }


}
