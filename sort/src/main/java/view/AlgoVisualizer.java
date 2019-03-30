package view;

import entity.SortData;
import sort_algorithms.BubbleSort;
import sort_algorithms.BucketSort;
import sort_algorithms.CountingSort;
import sort_algorithms.HeapSort;
import sort_algorithms.InsertionSort;
import sort_algorithms.MergeSort;
import sort_algorithms.QuickSort;
import sort_algorithms.SelectionSort;
import sort_algorithms.ShellSort;
import sort_algorithms.SortMethod;

import java.awt.*;

public class AlgoVisualizer {

    private static int SCENEWIDTH = 800;
    private static int SCENEHEIGHT = 800;
    private static int N = 100;

    private SortData data;
    private AlgoFrame frame;
    private SortMethod sortMethod;


    public AlgoVisualizer(String sort){
        this.data = new SortData(N,SCENEHEIGHT - 50);
        this.sortMethod = choseSort(sort);
        // 初始化视图
        init();
    }

    /**
     * 排序算法
     * @param sort
     * @return
     */
    private SortMethod choseSort(String sort) {
        switch (sort){
            case "InsertSort":      return new InsertionSort();
            case "SelectionSort":   return new SelectionSort();
            case "BubbleSort":      return new BubbleSort();
            case "BucketSort":      return new BucketSort();
            case "CountingSort":    return new CountingSort();
            case "HeapSort":        return new HeapSort();
            case "MergeSort":       return new MergeSort();
            case "QuickSort":       return new QuickSort();
            case "ShellSort":       return new ShellSort();
            default:                throw new IllegalArgumentException("No sort algorithms");
        }
    }

    private void init() {
        EventQueue.invokeLater(() -> {
            frame = new AlgoFrame(sortMethod.methodName(),data, SCENEWIDTH, SCENEHEIGHT);
            new Thread(()->sortMethod.sort(frame)).start();
        });
    }

    /**
     * 设置排序间隔
     * @param delay
     */
    public void setDelay(int delay){
        data.setDELAY(delay);
    }

}