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

        setVisible(true);
    }

    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}

    // data
    private SortData data;
    public void render(SortData data){
        this.data = data;
        repaint();
    }

    /**
     * 设置显示参数
     *
     * @param orderedIndex          排序好数组
     * @param currentCompareIndex   当前数据
     * @param currentChangeIndex    待交换数据
     */
    public void setData(int orderedIndex, int currentCompareIndex, int currentChangeIndex){
        // 初始值为-1
        data.addOrdereds(-1,orderedIndex);
        data.currentCompareIndex = currentCompareIndex;
        data.currentChangeIndex = currentChangeIndex;

        this.render(data);
        System.out.println(data.getOrdereds());
        AlgoVisHelper.pause();
    }

    /**
     * 比较两个数的大小返回布尔型
     * @param currentCompareIndex
     * @param currentChangeIndex
     * @return
     */
    public boolean less(int currentCompareIndex, int currentChangeIndex){
        return data.get(currentChangeIndex) < data.get(currentChangeIndex);
    }

    /**
     * 设置参数
     * @param orderedIndex          排序好数组
     * @param currentCompareIndex   当前数据
     * @param currentChangeIndex    待交换数据
     */
    public void setData(int orderedStart, int orderedIndex, int currentCompareIndex, int currentChangeIndex){
        data.addOrdereds(orderedStart,orderedIndex);
        data.currentCompareIndex = currentCompareIndex;
        data.currentChangeIndex = currentChangeIndex;

        this.render(data);
        AlgoVisHelper.pause();
        System.out.println(data.getOrdereds());
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
            int w = canvasWidth/data.N();
            for(int i = 0 ; i < data.N() ; i ++ ) {
                // 排序好空间
                if (data.isSorted(i))
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.Red);
                else
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.Grey);

                if(i == data.currentCompareIndex)
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.LightBlue);
                if(i == data.currentChangeIndex)
                    AlgoVisHelper.setColor(g2d, AlgoVisHelper.Indigo);
                AlgoVisHelper.fillRectangle(g2d, i * w, canvasHeight - data.get(i), w - 1, data.get(i));
            }
        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(canvasWidth, canvasHeight);
        }
    }
}