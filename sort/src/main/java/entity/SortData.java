package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SortData {

    private int[] numbers;

    /**
     * 排序好空间
     *  [orderedstart...orderedIndex) 是有序的
     */
    List<Ordered> ordereds;
    /**
     * 当前正在比较的元素索引
     */
    public int currentCompareIndex = -1;
    /**
     * 当前找到的最小元素的索引
     */
    public int currentMinIndex = -1;
    /**
     * Number of changes to the array the current algorithm has taken so far
     * 比较次数
     */
    private int arrayChanges = 0;

    public SortData(int N,int height){
        ordereds = new ArrayList<>();
        numbers = new int[N];
        shuffle(height);
    }

    public int N(){
        return numbers.length;
    }

    public int get(int index){
        if( index < 0 || index >= numbers.length)
            throw new IllegalArgumentException("Invalid index to access Sort Data.");

        return numbers[index];
    }

    public void swap(int i, int j) {

        if( i < 0 || i >= numbers.length || j < 0 || j >= numbers.length)
            throw new IllegalArgumentException("Invalid index to access Sort Data.");

        int t = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = t;
        arrayChanges++;
    }

    public int getArrayChanges() {
        return arrayChanges;
    }

    public int arraySize() {
        return numbers.length;
    }

    /**
     * 是否为排序好区间
     * @param curIndex
     * @return
     */
    public boolean isSorted(int curIndex){
        if( curIndex < 0 || curIndex >= numbers.length)
            throw new IllegalArgumentException("Invalid index to access Sort Data.");
        for (Ordered ordered : ordereds) {
            if (!ordered.isBetween(curIndex)){
                return false;
            }
        }
        return true;
    }

    /**
     * 添加排序区间
     * @param orderedstart
     * @param orderedIndex
     */
    public void addOrdereds(int orderedstart,int orderedIndex) {
        // 如果存在交叉区间 则合并
        for (Ordered or : ordereds) {
            if (or.isIntersect(orderedstart,orderedIndex)){
                // 如果包含则合并
                or.setOrderedstart(Math.min(or.getOrderedstart(),orderedstart));
                or.setOrderedIndex(Math.max(or.getOrderedIndex(),orderedIndex));
                return;
            }
        }
        // 不包含则直接添加
        ordereds.add(new Ordered(orderedstart,orderedIndex));
    }

    public void shuffle(int height) {
        arrayChanges = 0;
        Random rng = new Random();
        for (int i = 0; i < arraySize(); i++) {
            numbers[i] = rng.nextInt(height);
        }
        arrayChanges = 0;
    }

    public List<Ordered> getOrdereds() {
        return ordereds;
    }
}