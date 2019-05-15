package game;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * 棋盘类
 * @author Tao
 */
public class MainView extends JPanel {

    public static final int WIN_WIDTH = 600;
    public static final int WIN_HEIGHT = 448;
    /**
     * 棋盘
     */
    JPanel board;
    /**
     * 菜单
     */
    JPanel menu;

    public MainView(){
        this.setLayout(new BorderLayout());
        menu = new Menu();
        this.add(menu, BorderLayout.EAST);

        board = new Board();
        this.add(board, BorderLayout.CENTER);
    }


    /**
     * 宽高
     * @return
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIN_WIDTH, WIN_HEIGHT);
    }

}
