package miniapp;

import miniapp.Enum.SortEnum;
import miniapp.view.manoeuvre.AlgoVisualizer;
import miniapp.view.manoeuvre.Environment;

public class MiniApp {
    private static String InsertSort = "InsertSort";
    private static String SelectionSort = "SelectionSort";
    private static String BubbleSort = "BubbleSort";
    private static String BucketSort = "BucketSort";
    private static String CountingSort = "CountingSort";
    private static String HeapSort = "HeapSort";
    private static String MergeSort = "MergeSort";
    private static String MergeBUSort = "MergeBUSort";
    private static String MergeOptimizedSort = "MergeOptimizedSort";
    private static String QuickSort = "QuickSort";
    private static String Quick3waySort = "Quick3waySort";
    private static String DualPivotQuickSort = "DualPivotQuickSort";
    private static String ShellSort = "ShellSort";

    /**
     * 排序间隔
     *
     *  change          3 * delay
     *  compare         1 * delay
     *  assignment      1 * delay
     */
    private static int DELAY = 10;

    public static void main(String[] args) {
        AlgoVisualizer visualizer = new AlgoVisualizer(MergeSort);
        visualizer.setDelay(DELAY);
        Environment environment = visualizer.getEnvironment();
        while (true){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("耗时:"+environment.takeTime()+"ms");
        }
    }


}
