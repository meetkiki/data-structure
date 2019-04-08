package miniapp.view.screens;


/**
 * 绘图
 */
import miniapp.Enum.LineColorEnum;
import miniapp.MiniApp;
import miniapp.view.Screen;
import miniapp.view.analysis.ButtonPanel;
import miniapp.view.analysis.CommonCanvas;
import miniapp.view.analysis.DerivativeCanvas;
import miniapp.view.analysis.FittingCanvas;
import miniapp.view.analysis.LinePanel;
import miniapp.view.analysis.MyCanvas;
import miniapp.view.analysis.ProgressBarPanel;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

/**
 *    选择具体的排序方法
 *    计算排序的时间
 * @author Tao
 */
public class SortingAnalysisScreen extends Screen {

    /**
     * /主坐标图
     */
    private MyCanvas trendChartCanvas;
    private CommonCanvas commonCanvas;
    private DerivativeCanvas derivativeCanvas;
    private FittingCanvas fittingCanvas;

    private JPanel centerPanel;
    /**
     * 辅助栏面板
     */
    private LinePanel line;
    private ButtonPanel button;
    private JPanel ButtonAndLine;
    private JPanel backPanel;
    private JButton back;
    private JButton about;
    private ProgressBarPanel progrees;

    private MiniApp miniApp;

    public static final int DEFAULT_WIDTH = 1400;
    public static final int DEFAULT_HEIGHT = 700;
    public static final String BACK = "back";
    public static final String ABOUT = "about";

    /**
     * /排序线的颜色
     */
    private static final LineColorEnum[] lineColor = LineColorEnum.values();

    public SortingAnalysisScreen(MiniApp miniApp) {
        super(miniApp);
        this.setLayout(new BorderLayout());
        this.miniApp = miniApp;

        centerPanel = new JPanel();
        backPanel = new JPanel();
        //添加主画布到中心
        trendChartCanvas = new MyCanvas(this);
        commonCanvas = new CommonCanvas();
        derivativeCanvas = new DerivativeCanvas();
        fittingCanvas = new FittingCanvas();
        ButtonAndLine = new JPanel();
        progrees = new ProgressBarPanel();
        button = new ButtonPanel();
        line = new LinePanel(this);
        back = new JButton(BACK);
        about = new JButton(ABOUT);
        backPanel.setLayout(new GridLayout(2,1,0,10));
        backPanel.add(about);
        backPanel.add(back);
        backPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        centerPanel.setLayout(new BorderLayout(0, 10));
        centerPanel.add(trendChartCanvas, BorderLayout.CENTER);
        centerPanel.add(progrees, BorderLayout.SOUTH);
        this.add(centerPanel, BorderLayout.CENTER);

        //边侧辅助面板
        ButtonAndLine.setBorder(BorderFactory.createLineBorder(Color.RED));
        ButtonAndLine.setLayout(new BorderLayout(0, 5));
        ButtonAndLine.add(button, BorderLayout.NORTH);
        ButtonAndLine.add(line, BorderLayout.CENTER);
        ButtonAndLine.add(backPanel,BorderLayout.SOUTH);
        this.add(ButtonAndLine, BorderLayout.EAST);
        about.addActionListener(e->
            JOptionPane.showMessageDialog(null,
                    "left mouse button click sort execution, right click cancel display .",
                    "about", JOptionPane.INFORMATION_MESSAGE)
        );
        back.addActionListener(e->app.popScreen());
        this.setVisible(true);
    }

    public ProgressBarPanel getProgrees() {
        return progrees;
    }

    public MyCanvas getTrendChartCanvas() {
        return trendChartCanvas;
    }

    /**
     * /获取线的颜色
     */
    public static LineColorEnum[] getLineColor() {
        return lineColor;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }

    @Override
    public void onOpen() {

    }
}
