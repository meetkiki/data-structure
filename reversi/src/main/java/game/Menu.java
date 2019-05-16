package game;

import common.Constant;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author ypt
 * @ClassName Menu
 * @date 2019/5/15 15:08
 */
public class Menu extends JPanel {

    public static final int WIDTH = 152;
    public static final int HEIGHT = 448;
    public static final int BHEIGHT = 30;
    public static final int BWIDTH = 120;
    public static Font Dfont = new Font("微软雅黑", Font.PLAIN, 40);

    /**
     * / 全局常量.标识.提示
     */
    private JLabel identify;
    /**
     * / 单人、多人、设置、提示、悔棋
     */
    private JButton OneStart,TwoStart,MenuSet,hint,Undo;
    /**
     * / 是否双人，是否单人 默认单人模式
     */
    private boolean isBoth = false,isOne = false;
    /**
     * / 等级
     */
    private int level = 5;

    private MainView mainView;

    public Menu(MainView mainView) {
        this.mainView = mainView;
        initUI();
        initListener();
    }

    private void initUI() {
        this.setLayout(null);
        this.setBackground(Color.LIGHT_GRAY);
        //添加组件
        identify = new JLabel("黑白棋");
        OneStart = new JButton("单人对战");
        TwoStart = new JButton("双人模式");
        hint = new JButton("提示");
        Undo = new JButton("悔棋");
        MenuSet = new JButton("设置");
        identify.setFont(Dfont);
        identify.setBounds(15, 10, BWIDTH, 45);
        //设置位置
        OneStart.setBounds(15, 100, BWIDTH, BHEIGHT);
        TwoStart.setBounds(15, 140, BWIDTH, BHEIGHT);
        hint.setBounds(15, 300, BWIDTH, BHEIGHT);
        Undo.setBounds(15, 340, BWIDTH, BHEIGHT);
        MenuSet.setBounds(15, 380, BWIDTH, BHEIGHT);
        //添加到菜单
        this.add(OneStart);
        this.add(TwoStart);
        this.add(hint);
        this.add(MenuSet);
        this.add(identify);
        this.add(Undo);
    }


    private void initListener() {
        // 单人开始
        OneStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Board board = mainView.getBoard();
                // 如果已经开局
                if (isOne || isBoth){
                    int log = JOptionPane.showConfirmDialog(mainView, "是否需要重新开局?","提示",JOptionPane.YES_NO_OPTION);
                    if(log != 0) {
                        return;
                    }
                }
                int cho = JOptionPane.showConfirmDialog(mainView, "执黑先行", "是否执黑?", JOptionPane.YES_NO_OPTION);
                byte player = cho == 0 ? Constant.BLACK : Constant.WHITE;
                // 开局
                board.newGame(player);
                // 更新标志
                isOne = true;
                isBoth = false;
            }
        });
        // 两人开始
        TwoStart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }
        });
        // 提示
        hint.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }
        });
        // 悔棋
        Undo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Board board = mainView.getBoard();
                if (board.getSteps().isEmpty()){
                    JOptionPane.showMessageDialog(mainView, "已是初始棋盘", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                GameRule.getUnMove(board).fork().join();
            }
        });
        // 设置
        MenuSet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {

            }
        });
    }

    /**
     * 宽高
     * @return
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    public boolean isOne() {
        return isOne;
    }

    public boolean isBoth() {
        return isBoth;
    }
}
