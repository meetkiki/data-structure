package miniapp.view.analysis;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

/**
 * 右侧：按钮面板
 */
public class ButtonPanel extends JPanel {

    public static final int HEIGHT = 260;

    //普通按钮，导数按钮，拟合按钮
    private JButton commonButton;
    private JButton derivativeButton;
    private JButton fittingButton;
    private JSlider slider;

    public ButtonPanel() {
        commonButton = new JButton("普通曲线");
        derivativeButton = new JButton("一阶导数");
        fittingButton = new JButton("拟合曲线");
        slider = new JSlider(0,100,0);

        setBorder(BorderFactory.createEtchedBorder());
        setLayout(new GridLayout(6, 1, 0, 5));

        add(new JLabel("功能选择："));

        //添加按钮以及响应事件
        add(commonButton);
        commonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //打开普通画布

            }
        });

        add(derivativeButton);
        derivativeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //打开导数画布
                //new DerivativeCanvas();
            }
        });
        add(fittingButton);
        fittingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //打开拟合画布
                //new FittingCanvas();
            }
        });

        add(new JLabel("重复率选择："));
        add(slider);
        // 设置 主刻度标记间隔
        slider.setMajorTickSpacing(25);
        // 设置单个主刻度内的 次刻度标记间隔
        slider.setMinorTickSpacing(5);
        /*
         * 给指定的刻度值显示自定义标签
         */
        Hashtable<Integer, JComponent> hashtable = new Hashtable<Integer, JComponent>();
        //  0  刻度位置，显示 "Start"
        hashtable.put(0, new JLabel("0%"));
        //  10 刻度位置，显示 "Middle"
        hashtable.put(50, new JLabel("50%"));
        //  20 刻度位置，显示 "End"
        hashtable.put(100, new JLabel("100%"));
        slider.setLabelTable(hashtable);
        // 设置是否绘制 刻度线
        slider.setPaintTicks(true);
        // 设置是否绘制 刻度标签（刻度值文本）
        slider.setPaintLabels(true);
        // 设置是否绘制 滑道
        slider.setPaintTrack(true);
        // 设置滑块的方向，SwingConstants.VERTICAL 或 SwingConstants.HORIZONTAL
        slider.setOrientation(SwingConstants.HORIZONTAL);
        // 设置滑块是否对齐到刻度。设置为 true，则滑块最终只能在有刻度的位置取值，即滑块取值不连续。
        slider.setSnapToTicks(true);
        // 改变事件
        slider.addChangeListener((ChangeEvent e) -> DoSortTask.setMultiplicity(slider.getValue()));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getWidth(),HEIGHT);
    }

}