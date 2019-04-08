package miniapp.view.analysis;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

/**
 * @author Tao
 */
public class RunProgressBar extends JPanel {

    private JLabel runing;
    private InnerProgressBar rProgressBar;

    public RunProgressBar(String runing,Double[] times) {
        rProgressBar = new InnerProgressBar(times);
        rProgressBar.setStringPainted(true);
        this.runing = new JLabel(runing);
        this.runing.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setTimes(times);
        this.setLayout(new GridLayout(1,2,0,10));
        this.add(this.runing);
        this.add(rProgressBar);
        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }



    private class InnerProgressBar extends JProgressBar{
        /**
         * 更新进度条
         */
        public void updateValue() {
            super.setValue((int)getProgress());
        }

        private Double[] times;

        public InnerProgressBar(Double[] times) {
            this.times = times;
        }

        public void setTimes(Double[] times) {
            this.times = times;
        }

        private double getProgress(){
            double pro = 0.00;
            for (int i = 0; i < times.length; i++) {
                if (times[i] != null){
                    pro = pro + 1.00;
                }
            }
            return (pro / times.length) * 100;
        }

    }

    public int setTimes(Double[] times) {
        rProgressBar.setTimes(times);
        rProgressBar.updateValue();
        return rProgressBar.getValue();
    }
}
