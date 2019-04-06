package miniapp.sort_algorithms.shellsort;

import miniapp.abstraction.SortVisual;
import miniapp.view.manoeuvre.AlgoFrame;

public class ShellFrame implements SortVisual {

    @Override
    public String getCnName() {
        return "希尔排序";
    }


    /**
     * 希尔排序可视化
     *  1.插入排序的缺陷
     *      对于大规模数据插入排序只会一个一个的将数据移动到右边
     *      希尔排序为了加快速度改进了插入排序 交换不相临数组使数组局部有序
     * @param frame
     */
    @Override
    public void sort(AlgoFrame frame) {
        int size = frame.length(),h = 1;
        // 拆分数组为子数组h, 1,4,13,40,121,364,1093
        while (frame.compareLess(h, size / 3)) h = h * 3 + 1;
        while (frame.compareMoreOrEqual(h, 1)){
            // 插入排序
            for( int i = h ;frame.compareLess(i, frame.length()); i ++ ){
                // 寻找[h, n)区间里的最小值的索引
                for( int j = i;frame.compareMoreOrEqual(j - h,0) && frame.less(j,j - h) ; j -= h){
                    frame.swap(j,j - h);
                }
                // 更新排序区间
                frame.updateOrdereds(i + 1);
            }
            // 排序局部数组
            h /= 3;
        }
    }

    @Override
    public String methodName() {
        return "Shell Sort";
    }
}
