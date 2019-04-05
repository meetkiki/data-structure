package miniapp.sort_algorithms.quicksort;

import miniapp.view.AlgoFrame;

public class Quick3wayFrame extends QuickFrame {


    /**
     * 快速排序可视化
     * @param frame
     */
    @Override
    public void sort(AlgoFrame frame) {
        int length = frame.length();
        quick3waySort(frame,0,length-1);
    }


    /**
     * 三向快速排序
     * @param frame
     * @param l
     * @param r
     */
    private void quick3waySort(AlgoFrame frame, int l, int r) {
        if (r <= l) return;
        int i = l,j = l + 1,k = r ,v = frame.get(l);
        while (j <= k){
            int compare = frame.compare(frame.get(j), v);
            if (compare < 0) frame.swap(i++,j++);
            else if(compare > 0) frame.swap(j,k--);
            else j++;
        }
        quick3waySort(frame,l,i-1);
        frame.updateOrdereds(l,i-1);
        quick3waySort(frame,k + 1,r);
        frame.updateOrdereds(k + 1,r);
    }


    @Override
    public String methodName() {
        return "Quick3way Sort";
    }
}
