package miniapp.view.analysis;

import miniapp.Enum.LineColorEnum;
import miniapp.Enum.SortEnum;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * 右侧：画线面板
 */
class LinePanel extends JPanel {
    private int X_Start;
    private int Y_Start;
    private int Y_Change = 10;
    private SortEnum[] sortType;
    private LineColorEnum[] lineColor;

    public LinePanel() {
        //初始化
        sortType = SortEnum.values();
        lineColor = SortingAnalysisFrame.getLineColor();

        setBorder(BorderFactory.createEtchedBorder());
        setLayout(new GridLayout(16, 1, 0, 10));

        //获取画线位置
        X_Start = this.getX() + 10;
        Y_Start = this.getY() + 10;

        for (SortEnum sortEnum : sortType) {
            JButton lineButton = new JButton(sortEnum.getCnName());
            Color color = sortEnum.getSortMethod().lineColor().getColor();
            lineButton.setBackground(color);
            lineButton.setBorder(BorderFactory.createEmptyBorder());
            add(lineButton);
            add(new LineCanve(color));
        }
    }

    /**
     * 线的画布
     */
    class LineCanve extends Canvas {
        private Color color;

        public LineCanve(Color linecoler) {
            //获取颜色
            color = linecoler;
        }

        /**
         * /画线
         */
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2D = (Graphics2D) g;
            //绘图提示-消除锯齿
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // 轴线粗度
            g2D.setStroke(new BasicStroke(Float.parseFloat("2.0F")));

            //对八种排序绘制
            g.setColor(color);
            //绘制直线，通过循环，将所有的点连线
            g2D.drawLine(X_Start, Y_Start, X_Start + 80, Y_Start);
        }
    }
}