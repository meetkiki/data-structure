package miniapp.view;

import miniapp.abstraction.Sort;
import miniapp.entity.SortData;
import miniapp.sort_algorithms.bubblesort.BubbleFrame;
import miniapp.sort_algorithms.bucketsort.BucketFrame;
import miniapp.sort_algorithms.countingsort.CountingFrame;
import miniapp.sort_algorithms.heapsort.HeapFrame;
import miniapp.sort_algorithms.insertionsort.InsertionFrame;
import miniapp.sort_algorithms.mergesort.MergeFrame;
import miniapp.sort_algorithms.quicksort.QuickFrame;
import miniapp.sort_algorithms.selectionsort.SelectionFrame;
import miniapp.sort_algorithms.shellsort.ShellFrame;

import java.awt.*;
import java.util.concurrent.CountDownLatch;

public class AlgoVisualizer {

    private static int SCENEWIDTH = 1200;
    private static int SCENEHEIGHT = 1000;
    private static int N = 100;

    private SortData data;
    private AlgoFrame frame;
    private Sort sort;
    private Environment environment;
    private CountDownLatch count;


    public AlgoVisualizer(String sort){
        this.count = new CountDownLatch(1);
        this.data = new SortData(N,SCENEHEIGHT - 100);
        this.sort = choseSort(sort);
        // 初始化视图
        init();
    }

    private void init() {
        EventQueue.invokeLater(() -> {
            this.frame = new AlgoFrame(sort.methodName(),data, SCENEWIDTH, SCENEHEIGHT);
            this.environment = new Environment(sort,frame);
            this.count.countDown();
            new Thread(()->environment.invoke()).start();
        });
    }
    /**
     * 排序算法
     * @param sort
     * @return
     */
    private Sort choseSort(String sort) {
        switch (sort){
            case "InsertSort":      return new InsertionFrame();
            case "SelectionSort":   return new SelectionFrame();
            case "BubbleSort":      return new BubbleFrame();
            case "BucketSort":      return new BucketFrame();
            case "CountingSort":    return new CountingFrame();
            case "HeapSort":        return new HeapFrame();
            case "MergeSort":       return new MergeFrame();
            case "QuickSort":       return new QuickFrame();
            case "ShellSort":       return new ShellFrame();
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