package miniapp.view.analysis;

import miniapp.Enum.Constant;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Tao
 */
public class ProgressBarPanel extends JPanel {

    private Map<String,RunProgressBar> barList;
    /**
     * 最大队列数
     */
    public static final int QueueSize = 3;
    private static final int Height = 100;

    public ProgressBarPanel() {
        this.barList = new ConcurrentHashMap<>();
        this.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
        setLayout(new GridLayout(3, 1, 0, 5));
    }
    /**
     * 添加进度条
     * @param name
     */
    public RunProgressBar addBar(String name){
        return this.addBar(name,new Double[DoSortTask.abscissa]);
    }

    /**
     * 添加进度条
     * @param name
     */
    public RunProgressBar addBar(String name,Double[] times){
        if (barSize() >= QueueSize || isRunning(name)){
            return null;
        }
        RunProgressBar progressBar = new RunProgressBar(name,times);
        barList.put(name,progressBar);
        this.add(progressBar);
        this.revalidate();
        return progressBar;
    }

    public void updateBar(Map<String,Double[]> cache){
        Set<Map.Entry<String, Double[]>> entries = cache.entrySet();
        entries.forEach((e) -> updateBar(e.getKey(),e.getValue()));
    }

    public void updateBar(String name,Double[] times){
        RunProgressBar bar = barList.get(name);
        if (bar == null) {
            return;
        }
        int value = bar.setTimes(times);
        // 移除100%
        if (value >= Constant.Hundred){
            this.remove(name);
        }
        this.revalidate();
    }

    @Override
    public void revalidate() {
        super.revalidate();
    }

    /**
     * 移除进度条
     * @param name
     */
    public void remove(String name) {
        RunProgressBar run = barList.get(name);
        if (run == null)
            return;
        barList.remove(name);
        this.remove(run);
        this.repaint();
        this.revalidate();
    }

    /**
     * 判断排序算法是否正在执行
     * @param name
     * @return
     */
    public boolean isRunning(String name){
        return barList.get(name) != null;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getWidth(),Height);
    }

    public int barSize(){
        return barList.size();
    }

    public boolean isFull(){
        return barList.size() >= QueueSize;
    }
}
