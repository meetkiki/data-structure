package miniapp.abstraction;

import miniapp.view.manoeuvre.AlgoFrame;

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

    /**
     * 排序方法中文名称
     * @return
     */
    @Override
    String getCnName();
}
