package miniapp.abstraction;

import miniapp.view.AlgoFrame;

/**
 * 可视化接口
 */
public interface SortVisual extends Sort{

    /**
     * 排序可视化
     * @param frame
     * @return
     */
    @Override
    void sort(AlgoFrame frame);


    /**
     * 排序方法名称
     * @return
     */
    @Override
    String methodName();

}
