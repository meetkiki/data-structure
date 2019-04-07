package miniapp.view.analysis;

import miniapp.Enum.LineColorEnum;
import miniapp.Enum.SortEnum;
import miniapp.abstraction.ICommand;
import miniapp.abstraction.SortMethod;
import miniapp.view.manoeuvre.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 右侧：画线面板
 */
class LinePanel extends JPanel {
    private int X_Start;
    private int Y_Start;
    private int Y_Change = 10;
    private SortEnum[] sortType;
    private LineColorEnum[] lineColor;
    private Environment<Map<String, Double[]>> environment;
    private MyCanvas myCanvas;
    private ProgressBarPanel progressPanel;
    private Map<String,SwingWorker> workerMap;

    public LinePanel(SortingAnalysisFrame frame) {
        this.myCanvas = frame.getTrendChartCanvas();
        this.progressPanel = frame.getProgrees();
        this.workerMap = new ConcurrentHashMap<>();

        //初始化
        this.environment = new Environment<>();
        this.sortType = SortEnum.values();
        this.lineColor = SortingAnalysisFrame.getLineColor();

        setBorder(BorderFactory.createEtchedBorder());
        setLayout(new GridLayout(16, 1, 0, 10));

        //获取画线位置
        this.X_Start = this.getX() + 10;
        this.Y_Start = this.getY() + 10;
        for (SortEnum sortEnum : sortType) {
            LineButton lineButton = new LineButton(sortEnum.getCnName(),sortEnum.getSortMethod());
            Color color = sortEnum.getSortMethod().lineColor().getColor();
            lineButton.setBackground(color);
            lineButton.setBorder(BorderFactory.createEmptyBorder());
            add(lineButton);
            add(new LineCanve(color));
            lineButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent evt) {
                    if (evt.isMetaDown()){
                        LineButton lButton = (LineButton)evt.getSource();
                        String name = lButton.sortMethod.methodName();
                        // 双击取消
                        SwingWorker worker = workerMap.get(name);
                        if (worker != null && !worker.isDone()){
                            worker.cancel(true);
                            workerMap.remove(name);
                            SortCommand.getCacheMap().remove(name);
                            progressPanel.remove(name);
                            myCanvas.paint();
                        }
                    }else if(evt.getClickCount() == 1){
                        LineButton lButton  = (LineButton)evt.getSource();
                        if (lButton.isRunning()){
                            return;
                        }else {
                            try {
                                String name = lButton.sortMethod.methodName();
                                RunProgressBar addBar = progressPanel.addBar(name);
                                if (addBar == null) {
                                    JOptionPane.showMessageDialog(null, workerMap.get(name) != null ?
                                            "The execution queue is full ! You can double-click to cancel the task !"
                                            : "The execution queue is running !", "warning", JOptionPane.WARNING_MESSAGE);
                                    return;
                                }
                                workerMap.put(name,doSort(new DoSortTask(name, frame), lButton));
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }
            });
        }
    }

    /**
     * 线程安全的执行方法
     * @param command
     * @param lineButton
     */
    private SwingWorker doSort(ICommand command,LineButton lineButton){
        SwingWorker<Map, Void> swingWorker = new SwingWorker<Map,Void>() {
            @Override
            protected Map doInBackground(){
                lineButton.setRunning(true);
                environment.setCommand(command);
                return environment.invoke();
            }

            @Override
            public void done() {
                try {
                    String name = lineButton.sortMethod.methodName();
                    Double[] times = SortCommand.getCacheMap().get(name);
                    progressPanel.updateBar(name,times == null ? new Double[1] : times);
                    lineButton.setRunning(false);
                    progressPanel.remove(name);
                    workerMap.remove(name);
                    myCanvas.paint();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        swingWorker.execute();
        return swingWorker;
    }

    /**
     * 右侧按钮控制排序启动
     */
    private class LineButton extends JButton{
        private SortMethod sortMethod;
        private boolean running;

        public LineButton(String text,SortMethod sortMethod) {
            super(text);
            this.sortMethod = sortMethod;
            this.running = false;
        }

        public SortMethod getSortMethod() {
            return sortMethod;
        }

        public boolean isRunning() {
            return running;
        }

        public void setRunning(boolean running) {
            this.running = running;
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

            //对排序绘制
            g.setColor(color);
            //绘制直线，通过循环，将所有的点连线
            g2D.drawLine(X_Start, Y_Start, X_Start + 80, Y_Start);
        }
    }
}