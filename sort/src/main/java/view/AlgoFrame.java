package view;

import entity.SortData;

import java.awt.*;
import javax.swing.*;

public class AlgoFrame extends JFrame implements Cloneable{
    private int canvasWidth;
    private int canvasHeight;
    /**
     * 是否为主
     */
    private boolean master = true;

    /**
     * 判断是否为主
     * @return
     */
    public boolean isMaster(){
        return master;
    }

    /**
     * 排序data
     */
    private SortData data;

    public AlgoFrame(){}
    public AlgoFrame(String title, SortData data, int canvasWidth, int canvasHeight){
        super(title);

        this.data = data;

        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;

        AlgoCanvas canvas = new AlgoCanvas();
        setContentPane(canvas);
        pack();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        // 居中
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}

    /**
     * 重新渲染视图
     * @param data
     */
    public void render(SortData data){
        this.data = data;
        repaint();
    }

    public SortData getData() {
        return data;
    }

    /**
     * 设置显示参数
     *
     * @param orderedIndex          排序好数组
     * @param currentCompareIndex   当前数据索引
     * @param currentChangeIndex    待交换数据索引
     */
    public void setData(int orderedIndex, int currentCompareIndex, int currentChangeIndex){
        // 初始值为-1
        setData(-1,orderedIndex,currentCompareIndex,currentChangeIndex);
    }

    /**
     * 设置参数
     * @param orderedIndex          排序好数组区间
     * @param currentCompareIndex   当前数据索引
     * @param currentChangeIndex    待交换数据索引
     */
    public void setData(int orderedStart, int orderedIndex, int currentCompareIndex, int currentChangeIndex){
        data.addOrdereds(orderedStart,orderedIndex);
        // 更新参数
        updateData(currentCompareIndex,currentChangeIndex);
    }


    /**
     * 更新参数
     * @param currentCompareIndex   当前数据索引
     * @param currentChangeIndex    待交换数据索引
     */
    public void updateData(int currentCompareIndex, int currentChangeIndex){
        data.currentCompareIndex = currentCompareIndex;
        data.currentChangeIndex = currentChangeIndex;

        this.render(data);
        AlgoVisHelper.pause(data.getDELAY());
        System.out.println(data.getOrdereds());
        System.out.println("change --- " + this.getChange());
    }



    /**
     * 比较两个数的大小返回布尔型
     * @param currentCompareIndex
     * @param currentChangeIndex
     * @return
     */
    public boolean less(int currentCompareIndex, int currentChangeIndex){
        // 设置指向
        updateData(currentCompareIndex,currentChangeIndex);
        return data.less(currentCompareIndex,currentChangeIndex);
    }
    /**
     * 比较两个数的是否小于等于  返回布尔型
     * @param currentCompareIndex
     * @param currentChangeIndex
     * @return
     */
    public boolean lessOrEqual(int currentCompareIndex, int currentChangeIndex){
        // 设置指向
        updateData(currentCompareIndex,currentChangeIndex);
        return data.lessOrEqual(currentCompareIndex,currentChangeIndex);
    }

    /**
     * 交换两个数的数据
     * @param currentCompareIndex
     * @param currentChangeIndex
     */
    public void swap(int currentCompareIndex, int currentChangeIndex){
        data.swap(currentCompareIndex,currentChangeIndex);
    }

    /**
     * 获取克隆对象
     * @return
     */
    public AlgoFrame cloneData(){
        AlgoFrame frame = null;
        try {
            SortData sortData = (SortData)data.getClone();
            sortData.setNumbers(data.cloneData());
            this.master = true;
            // 克隆为副 this为主
            frame = (AlgoFrame)this.clone();
            frame.master = false;
            frame.data = sortData;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException("Clone Error!");
        }
        return frame;
    }
    /**
     *  获得数据长度
     */
    public int length(){
        return data.size();
    }

    /**
     *  获得数据
     */
    public int get(int index){
        return data.get(index);
    }

    /**
     * 数组交换次数
     * @return
     */
    public int getChange(){
        return data.getArrayChanges();
    }

    /**
     * 替换传入索引的值
     * @param srcIndex
     * @param value
     * @return
     */
    public void replace(int srcIndex, int value){
        replace(this.data,srcIndex,value);
    }

    /**
     * 替换传入索引的值
     * @param data 数据项
     * @param srcIndex
     * @param value
     * @return
     */
    public void replace(SortData data,int srcIndex, int value){
        // 设置指向
        updateData(srcIndex,srcIndex);
        data.set(srcIndex,value);
    }

    /**
     * 替换传入索引的值
     * @param data 数据项
     * @param srcIndex
     * @param value
     * @return
     */
    public void replace(AlgoFrame data,int srcIndex, int value){
        // 设置指向
        optimizeSetData(this,data,-1,-1,srcIndex,srcIndex);
        data.getData().set(srcIndex,value);
    }


    /**
     * 归并优化后可视化更新显示
     * @param frame             原数组
     * @param auxFrame          拷贝数组
     * @param orderedStart
     * @param orderedIndex
     * @param currentCompareIndex
     * @param currentChangeIndex
     */
    public void optimizeSetData(AlgoFrame frame, AlgoFrame auxFrame, int orderedStart, int orderedIndex, int currentCompareIndex, int currentChangeIndex){
        optimizeSetData(frame,auxFrame,orderedStart,orderedIndex,currentCompareIndex,currentChangeIndex,true);
    }

    /**
     * 归并优化后可视化更新显示
     * @param frame             原数组
     * @param auxFrame          拷贝数组
     * @param orderedStart
     * @param orderedIndex
     * @param currentCompareIndex
     * @param currentChangeIndex
     */
    public void optimizeSetData(AlgoFrame frame, AlgoFrame auxFrame, int orderedStart, int orderedIndex, int currentCompareIndex, int currentChangeIndex,boolean showMaster){
        // 判断是否需要将子数组显示 true 只显示master false 显示从
        if (showMaster == frame.isMaster()) frame.setData(orderedStart,orderedIndex,currentCompareIndex,currentChangeIndex);
        if (showMaster == auxFrame.isMaster()) auxFrame.setData(orderedStart,orderedIndex,currentCompareIndex,currentChangeIndex);
    }

    /**
     * 数组拷贝方法
     * @param src       源数组
     * @param srcIndex  源数组开始索引
     * @param desc      目标数组
     * @param descIndex 目标数组开始索引
     * @param length    长度
     */
    public void dataCoppy(AlgoFrame src,int srcIndex ,AlgoFrame desc,int descIndex,int length){
        for (int i = 0; i < length; i++) {
            desc.replace(descIndex + i,src.get(srcIndex + i));
        }
    }


    private class AlgoCanvas extends JPanel{

        public AlgoCanvas(){
            // 双缓存
            super(true);
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D)g;

            // 抗锯齿
            RenderingHints hints = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.addRenderingHints(hints);

            // 具体绘制
            int w = canvasWidth / data.size();
            for(int i = 0; i < data.size() ; i ++ ) {
                // 排序好空间
                if (data.isSorted(i))
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.Red);
                else
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.Grey);

                if(i == data.currentCompareIndex)
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.LightBlue);
                if(i == data.currentChangeIndex)
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.Indigo);
                AlgoVisHelper.fillRectangle(g2d,  i * w, canvasHeight - data.get(i), w - 1, data.get(i));
            }
        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}