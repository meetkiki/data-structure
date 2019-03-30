package view;

import entity.SortData;

import java.awt.*;
import javax.swing.*;

public class AlgoFrame extends JFrame{
    private int canvasWidth;
    private int canvasHeight;

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
     * 排序data
     */
    private SortData data;
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
        System.out.println("change --- " + getChange());
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
     * 交换两个数的数据
     * @param currentCompareIndex
     * @param currentChangeIndex
     */
    public void swap(int currentCompareIndex, int currentChangeIndex){
        data.swap(currentCompareIndex,currentChangeIndex);
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
     * 替换两个数的值
     * @param srcIndex
     * @param value
     * @return
     */
    public void replace(int srcIndex, int value){
        replace(this.data,srcIndex,value);
    }

    /**
     * 替换两个数的值
     * @param srcIndex
     * @param value
     * @return
     */
    public void replace(SortData data,int srcIndex, int value){
        // 设置指向
        updateData(srcIndex,srcIndex);
        data.set(srcIndex,value);
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