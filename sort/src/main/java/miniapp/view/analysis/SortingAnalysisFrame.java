package miniapp.view.analysis;


/**
 * 绘图
 */
import miniapp.Enum.LineColorEnum;
import miniapp.MiniApp;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

/**
 *    选择具体的排序方法
 *    计算排序的时间
 * @author Tao
 */
public class SortingAnalysisFrame extends JFrame {

    /**
     * /主坐标图
     */
    private MyCanvas trendChartCanvas;
    private CommonCanvas commonCanvas;
    private DerivativeCanvas derivativeCanvas;
    private FittingCanvas fittingCanvas;

    /**
     * 辅助栏面板
     */
    private LinePanel line;
    private ButtonPanel button;
    private JPanel ButtonAndLine;

    private MiniApp miniApp;

    public static final int DEFAULT_WIDTH = 1400;
    public static final int DEFAULT_HEIGHT = 700;

    /**
     * /排序线的颜色
     */
    private static final LineColorEnum[] lineColor = LineColorEnum.values();

    //构造函数
    public SortingAnalysisFrame(MiniApp miniApp) {
        super("排序算法时间复杂度分析");
        this.setLayout(new BorderLayout());
        this.miniApp = miniApp;

        //添加主画布到中心
        trendChartCanvas = new MyCanvas();
        commonCanvas = new CommonCanvas();
        derivativeCanvas = new DerivativeCanvas();
        fittingCanvas = new FittingCanvas();
        ButtonAndLine = new JPanel();
        button = new ButtonPanel();
        line = new LinePanel(trendChartCanvas);
        this.add(trendChartCanvas, BorderLayout.CENTER);
        this.add(trendChartCanvas, BorderLayout.CENTER);

        //边侧辅助面板
        ButtonAndLine.setBorder(BorderFactory.createLineBorder(Color.RED));
        ButtonAndLine.setLayout(new BorderLayout(0, 50));
        ButtonAndLine.add(button, BorderLayout.NORTH);
        ButtonAndLine.add(line, BorderLayout.CENTER);
        this.add(ButtonAndLine, BorderLayout.EAST);

        this.setVisible(true);
        this.setResizable(false);
        pack();
    }

    //获取线的颜色
    public static LineColorEnum[] getLineColor() {
        return lineColor;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }
}
