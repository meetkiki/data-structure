package miniapp.sort_algorithms.heapsort;

import miniapp.abstraction.SortVisual;
import miniapp.view.manoeuvre.AlgoFrame;

import java.util.Arrays;

/**
 * @author Tao
 */
public class HeapFrame implements SortVisual {

    @Override
    public String getCnName() {
        return "堆排序";
    }

    /**
     * 堆排序及其图形化实现
     *  堆的父节点为i 子节点为2i+1 和2i+2
     * @param frame
     */
    @Override
    public void sort(AlgoFrame frame) {
        int N = frame.length() - 1;
        // 构造最大堆 根节点为最大元素
        buildHeap(frame);
        // 逐个删除根节点 放到数组的最后
        while (N >= 1){
            frame.swap(N--,0);
            // 将最后一个结点放在根节点 下沉到合适位置
            sink(frame,0,N);
        }
        System.out.println(Arrays.toString(frame.getData().getNumbers()));
    }

    /**
     * 构造最大堆
     * @param frame
     */
    private void buildHeap(AlgoFrame frame) {
        int l = frame.length() - 1;
        for (int i = l / 2 - 1; i >= 0; i--) {
            // 从子堆开始构建最大堆
            sink(frame, i, l);
        }
    }

    /**
     * 下沉方法
     * @param frame
     * @param i
     * @param r
     */
    private void sink(AlgoFrame frame, int i, int r) {
        int k;
        while ((k = 2 * i + 1) <= r){
            // 找出最大的儿子
            if ((k+1) <= r && frame.less(k,k+1)) k++;
            // 如果儿子大于父亲则交换
            if (frame.less(i,k)){
                frame.swap(i,k);
                i = k;
            }else{
                break;
            }
        }
    }


    @Override
    public String methodName() {
        return "Heap Sort";
    }
}
