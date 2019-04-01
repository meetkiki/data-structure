package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SortData implements Cloneable{
    /**
     * 数据数组
     */
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
     * 当前找到的待交换元素的索引
     */
    public int currentChangeIndex = -1;
    /**
     * Number of changes to the array the current algorithm has taken so far
     * 交换次数
     */
    private int arrayChanges = 0;
    /**
     * Number of changes to the array the current algorithm has taken so far
     * 比较次数
     */
    private int arrayCompare = 0;
    /**
     * 等待时间
     */
    private int DELAY = 100;

    public SortData(int N,int height){
        ordereds = new ArrayList<>();
        numbers = new int[N];
        shuffle(height);
    }

    public SortData(int[] numbers) {
        this.numbers = numbers;
    }

    public int size(){
        return numbers.length;
    }

    public int get(int index){
        checkIndex(index);

        return numbers[index];
    }

    public void set(int index,int element){
        checkIndex(index);

        numbers[index] = element;
        changeIncrement();
    }

    public void swap(int i, int j) {
        if(checkIndex(i) || checkIndex(j)){
            return;
        }

        int t = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = t;
        changeIncrement();
    }

    private boolean checkIndex(int index){
        if( index < 0 || index >= numbers.length)
            throw new IllegalArgumentException("Invalid index to access Sort Data.");
        return false;
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
        checkIndex(curIndex);
        for (Ordered ordered : ordereds) {
            // 如果有一个则为true 否则为false
            if (ordered.isBetween(curIndex)){
                return true;
            }
        }
        return false;
    }

    /**
     * 比较两个数的大小返回布尔型
     * @param currentCompareIndex
     * @param currentChangeIndex
     * @return
     */
    public boolean less(int currentCompareIndex, int currentChangeIndex){
        compareIncrement();
        return numbers[currentCompareIndex] < numbers[currentChangeIndex];
    }

    /**
     * 比较两个数的是否小于等于  返回布尔型
     * @param currentCompareIndex
     * @param currentChangeIndex
     * @return
     */
    public boolean lessOrEqual(int currentCompareIndex, int currentChangeIndex){
        compareIncrement();
        return numbers[currentCompareIndex] <= numbers[currentChangeIndex];
    }

    /**
     * 比较自增
     */
    public void compareIncrement(){
        arrayCompare++;
    }
    /**
     * 交换自增
     */
    public void changeIncrement(){
        arrayChanges++;
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
        Random rng = new Random();
        for (int i = 0; i < arraySize(); i++) {
            this.set(i,rng.nextInt(height));
        }
        arrayChanges = 0;
        arrayCompare = 0;
    }

    public int[] getNumbers() {
        return numbers;
    }

    public void setNumbers(int[] numbers) {
        this.numbers = numbers;
    }

    public int getDELAY() {
        return DELAY;
    }

    public void setDELAY(int DELAY) {
        this.DELAY = DELAY;
    }

    public List<Ordered> getOrdereds() {
        return ordereds;
    }

    /**
     * 克隆数组，用来做原数据数组用
     * @return
     */
    public int[] cloneData(){
        return numbers.clone();
    }

    /**
     * 克隆数组，用来做原数据数组用
     * @return
     */
    public Object getClone() throws CloneNotSupportedException {
        return this.clone();
    }

    public int getArrayCompare() {
        return arrayCompare;
    }
}