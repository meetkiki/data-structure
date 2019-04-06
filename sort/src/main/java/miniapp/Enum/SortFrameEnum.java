package miniapp.Enum;

import miniapp.abstraction.SortVisual;
import miniapp.sort_algorithms.bubblesort.BubbleFrame;
import miniapp.sort_algorithms.bucketsort.BucketFrame;
import miniapp.sort_algorithms.countingsort.CountingFrame;
import miniapp.sort_algorithms.heapsort.HeapFrame;
import miniapp.sort_algorithms.insertionsort.InsertionFrame;
import miniapp.sort_algorithms.mergesort.MergeBuFrame;
import miniapp.sort_algorithms.mergesort.MergeFrame;
import miniapp.sort_algorithms.mergesort.MergeOptimizedFrame;
import miniapp.sort_algorithms.quicksort.DualPivotQuickFrame;
import miniapp.sort_algorithms.quicksort.Quick1ScanFrame;
import miniapp.sort_algorithms.quicksort.Quick3wayFrame;
import miniapp.sort_algorithms.quicksort.QuickFrame;
import miniapp.sort_algorithms.selectionsort.SelectionFrame;
import miniapp.sort_algorithms.shellsort.ShellFrame;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum SortFrameEnum {
    /**
     * 插入排序
     */
    InsertSort("InsertSort"),
    /**
     * 选择排序
     */
    SelectionSort("SelectionSort"),
    /**
     * 冒泡排序
     */
    BubbleSort("BubbleSort"),
    /**
     * 桶排序
     */
    BucketSort("BucketSort"),
    /**
     * 计数排序
     */
    CountingSort("CountingSort"),
    /**
     * 堆排序
     */
    HeapSort("HeapSort"),
    /**
     * 自下而上归并排序
     */
    MergeSort("MergeSort"),
    /**
     * 自下而上归并排序
     */
    MergeBuSort("MergeBuSort"),
    /**
     * 优化归并排序
     */
    MergeOptimizedSort("MergeOptimizedSort"),
    /**
     * 快速排序
     */
    QuickSort("QuickSort"),
    /**
     * 三向切分快速排序
     */
    Quick3waySort("Quick3waySort"),
    /**
     * 快速双轴排序
     */
    DualPivotQuickSort("DualPivotQuickSort"),
    /**
     * 希尔排序
     */
    ShellSort("ShellSort");

    private String name;
    private static Map<String, SortVisual> cache;

    static {
        Map<String,SortVisual> map = new HashMap<>(32);
        map.put("InsertSort",           new InsertionFrame());
        map.put("SelectionSort",        new SelectionFrame());
        map.put("BubbleSort",           new BubbleFrame());
        map.put("BucketSort",           new BucketFrame());
        map.put("CountingSort",         new CountingFrame());
        map.put("HeapSort",             new HeapFrame());
        map.put("MergeSort",            new MergeFrame());
        map.put("MergeBuSort",          new MergeBuFrame());
        map.put("MergeOptimizedSort",   new MergeOptimizedFrame());
        map.put("QuickSort",            new QuickFrame());
        map.put("Quick1ScanSort",       new Quick1ScanFrame());
        map.put("Quick3waySort",        new Quick3wayFrame());
        map.put("DualPivotQuickSort",   new DualPivotQuickFrame());
        map.put("ShellSort",            new ShellFrame());
        cache = Collections.unmodifiableMap(map);
    }

    SortFrameEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public SortVisual getSortFrame(){
        return cache.get(name);
    }

    public static SortVisual getFrame(String name) {
        return cache.get(name);
    }

}
