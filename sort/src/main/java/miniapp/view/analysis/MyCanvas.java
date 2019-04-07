package miniapp.view.analysis;

import miniapp.Enum.LineColorEnum;
import miniapp.Enum.SortEnum;
import miniapp.utils.UnequalConversion;

import java.awt.*;
import java.util.Map;

/**
 *  // 主画布
 * @author Tao
 */
public class MyCanvas extends Canvas {
    /**
     * / 画布框架起点坐标
     */
    private final int FREAME_X = 50;
    private final int FREAME_Y = 50;
    // 横
    private final int FREAME_WIDTH = 1000;
    // 纵
    private final int FREAME_HEIGHT = 500;

    /**
     * / 画布原点坐标
     */
    private final int Origin_X = FREAME_X + 50;
    private final int Origin_Y = FREAME_Y + FREAME_HEIGHT;

    /**
     * / 画布X,Y轴终点坐标
     */
    private final int XAxis_X = FREAME_X + FREAME_WIDTH;
    private final int XAxis_Y = Origin_Y;
    private final int YAxis_X = Origin_X;
    private final int YAxis_Y = FREAME_Y;

    /**
     * / 画布X，Y轴上的分度值（1分度=像素）
     */
    private final int LENGTH_INTERVAL = 40;
    private final int TIME_INTERVAL = 20;
    private final int NUM_INTERVAL = FREAME_HEIGHT / TIME_INTERVAL;

    private LineColorEnum[] lineColor;

    /**
     * 排序总数组
     */
    private static Map<String, double[]> sortArray;

    /**
     * /每次画线时的缓存数组
     */
    private double[] tempArray;

    /**
     * /画线的的名称
     */
    private SortEnum[] sortEnums;

    /**
     * /获取每个排序的时间（原单位：毫秒）
     */
    public MyCanvas() {
        //获取画线颜色
        lineColor = SortingAnalysisFrame.getLineColor();

        //获取画线的名称
        sortEnums = SortEnum.values();
        sortArray = DoSortTask.getCacheMap();
    }

    public void paint(){
        sortArray = DoSortTask.getCacheMap();
        repaint();
    }

    /**
     * /绘图
     */
    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        //定义颜色
        Color c = new Color(200, 70, 0);
        g.setColor(c);
        //绘图提示-消除锯齿
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 画坐标轴 // 轴线粗度
        g2D.setStroke(new BasicStroke(Float.parseFloat("2.0F")));
        // X轴以及方向箭头 // x轴线的轴线
        g.drawLine(Origin_X, Origin_Y, XAxis_X, XAxis_Y);
        // 上边箭头、
        g.drawLine(XAxis_X, XAxis_Y, XAxis_X - 5, XAxis_Y - 5);
        // 下边箭头
        g.drawLine(XAxis_X, XAxis_Y, XAxis_X - 5, XAxis_Y + 5);
        // Y轴以及方向箭头
        g.drawLine(Origin_X, Origin_Y, YAxis_X, YAxis_Y);
        g.drawLine(YAxis_X, YAxis_Y, YAxis_X - 5, YAxis_Y + 5);
        g.drawLine(YAxis_X, YAxis_Y, YAxis_X + 5, YAxis_Y + 5);
        // 画X轴上的空间刻度（从坐标轴原点起，每隔LENGTH_INTERVAL(容量分度)像素画一时间点，到100万止）
        g.setColor(Color.BLUE);
        g2D.setStroke(new BasicStroke(Float.parseFloat("1.0f")));
        // X轴刻度依次变化情况
        for (int i = Origin_X, j = 0; j <= 1000; i += LENGTH_INTERVAL, j += 50) {
            g.drawString(" " + j, i - 10, Origin_Y + 20);
        }
        g.drawString("数组大小/万", XAxis_X - 20, XAxis_Y + 20);
        // 画Y轴上时间刻度（从坐标原点起，每隔10像素画一格，到1000止）
        for (int i = Origin_Y, j = 0; j <= NUM_INTERVAL; i -= TIME_INTERVAL, j += 1) {
            g.drawString( UnequalConversion.conversionTo(j), Origin_X - 60, i + 3);
        }
        // 时间刻度小箭头值
        g.drawString("时间/ms", YAxis_X - 5, YAxis_Y - 15);
        // 画网格线
        g.setColor(Color.LIGHT_GRAY);
        // 坐标内部横线
        for (int i = Origin_Y; i >= YAxis_Y; i -= TIME_INTERVAL) {
            g.drawLine(Origin_X, i, XAxis_X, i);
        }
        // 坐标内部竖线
        for (int i = Origin_X; i <= XAxis_X; i += LENGTH_INTERVAL) {
            g.drawLine(i, Origin_Y, i, YAxis_Y);
        }

        g.setColor(c);
        // 轴线粗度
        g2D.setStroke(new BasicStroke(Float.parseFloat("2.0F")));

        // 排序算法绘制
        for (int k = 0; k < sortEnums.length; k++) {
            g.setColor(sortEnums[k].getSortMethod().lineColor().getColor());
            if (sortArray.get(sortEnums[k].getSortMethod().methodName()) != null) {
                double[] times = sortArray.get(sortEnums[k].getSortMethod().methodName());
                tempArray = times;
            } else{
                continue;
            }
            //绘制直线，通过循环，将所有的点连线
            for (int i = 0; i < DoSortTask.abscissa - 1; i++) {
                g2D.drawLine(Origin_X + i * LENGTH_INTERVAL, Origin_Y - (int)(UnequalConversion.conversionLoad(tempArray[i]) * NUM_INTERVAL),
                        Origin_X + (i + 1) * LENGTH_INTERVAL, Origin_Y - (int)(UnequalConversion.conversionLoad(tempArray[i + 1]) * NUM_INTERVAL));
                if (i == (DoSortTask.abscissa - 2)) {
                    g2D.setFont(new Font("黑体", Font.BOLD, 16));
                    g2D.drawString(sortEnums[k].getCnName(), Origin_X + (i + 1) * LENGTH_INTERVAL + 10, Origin_Y - (int)(UnequalConversion.conversionLoad(tempArray[i + 1]) * NUM_INTERVAL));
                }
            }
        }
    }
}