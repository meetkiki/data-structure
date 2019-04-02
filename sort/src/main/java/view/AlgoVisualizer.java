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
import abstraction.SortMethod;

import java.awt.*;
import java.util.concurrent.CountDownLatch;

public class AlgoVisualizer {

    private static int SCENEWIDTH = 1200;
    private static int SCENEHEIGHT = 1000;
    private static int N = 100;

    private SortData data;
    private AlgoFrame frame;
    private SortMethod sortMethod;
    private Environment environment;
    private CountDownLatch count;


    public AlgoVisualizer(String sort){
        this.count = new CountDownLatch(1);
        this.data = new SortData(N,SCENEHEIGHT - 100);
        this.sortMethod = choseSort(sort);
        // 初始化视图
        init();
    }

    private void init() {
        EventQueue.invokeLater(() -> {
            this.frame = new AlgoFrame(sortMethod.methodName(),data, SCENEWIDTH, SCENEHEIGHT);
            this.environment = new Environment(sortMethod,frame);
            this.count.countDown();
            new Thread(()->environment.invoke()).start();
        });
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
            case "MergeBUSort":       return new MergeSort().new MergeBu();
            default:                throw new IllegalArgumentException("No sort algorithms");
        }
    }

    /**
     * 设置排序间隔
     * @param delay
     */
    public void setDelay(int delay){
        data.setDELAY(delay);
    }

    /**
     * 获取代理执行对象
     * @return
     */
    public Environment getEnvironment(){
        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return environment;
    }
}