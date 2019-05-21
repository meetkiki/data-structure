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
    private Board board;
    /**
     * 菜单
     */
    private Menu menu;

    public MainView(){
        this.setLayout(new BorderLayout());
        this.board = new Board();
        this.add(board, BorderLayout.CENTER);

        this.menu = new Menu();
        this.add(menu, BorderLayout.EAST);
        // 注册一个bean
        GameContext.registerBean(MainView.class,this);
    }

    public Board getBoard() {
        return board;
    }

    public Menu getMenu() {
        return menu;
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
