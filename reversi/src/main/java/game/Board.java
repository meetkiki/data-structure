package game;

import bean.BoardData;
import bean.Move;
import common.Constant;
import common.ImageConstant;
import utils.BoardUtil;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

import static common.Constant.ROW;
import static common.Constant.COL;
import static common.Constant.SIZE;
import static common.Constant.SPAN;

/**
 * 棋盘类
 * @author Tao
 */
public class Board extends JPanel {


    private static final int BOARD_WIDTH = 448;
    private static final int BOARD_HEIGHT = 448;
    /**
     * 棋盘数组
     */
    private BoardData boardChess = new BoardData();
    /**
     * 图标资源
     */
    private Map<ImageConstant, ImageIcon> imageIconMap;

    private Image background;

    public Board(){
        this.setLayout(null);
        imageIconMap = GameContext.getResources();
        background = imageIconMap.get(ImageConstant.BOARD).getImage();
        this.setBounds(0, 0,BOARD_HEIGHT, BOARD_WIDTH);
        initBoard();
        JPanel curr = this;
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                curr.setCursor(new Cursor(Cursor.HAND_CURSOR));
                byte[] move = getMove(e);
                byte col = move[0];
                byte row = move[1];
                List<Move> changes = GameRule.make_move(boardChess, new Move(row, col));
                Chess[][] chess = boardChess.getChess();
                for (Move mo : changes) {
                    byte ro = mo.getRow();
                    byte co = mo.getCol();
                    chess[ro][co].change(boardChess.getNextmove());
                }
            }
        });
    }

    /**
     * 获取下棋的坐标
     * @param e
     * @return
     */
    private byte[] getMove(MouseEvent e){
        int Point_x = e.getX();
        int Point_y = e.getY();
        //判断棋盘是否在下棋范围 //如果未越界
        if(isBorder(Point_x, Point_y)){
            byte[] x_y = new byte[2];
            //转化为棋盘坐标 对应col
            x_y[0] = (byte)((Point_x - Constant.SPAN) / Constant.ROW);
            // 对应row
            x_y[1] = (byte)((Point_y - Constant.SPAN) / Constant.COL);
            return x_y;
        }else{
            return null;
        }
    }

    private boolean isBorder(int point_x, int point_y) {
        return !(point_x > (BOARD_WIDTH - Constant.SPAN - 5) || point_x < Constant.SPAN + 5 ||
                point_y > (BOARD_HEIGHT - Constant.SPAN-5) || point_y < Constant.SPAN + 5);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //下面这行是为了背景图片可以跟随窗口自行调整大小，可以自己设置成固定大小
        g.drawImage(background, 0, 0,BOARD_WIDTH, BOARD_HEIGHT, this);
    }

    /**
     * 初始化棋盘参数
     */
    private void initBoard() {
        // 初始化棋子
        initChess();
        // 获取行动力
        GameRule.valid_moves(boardChess,boardChess.getNextmove());
        BoardUtil.display(boardChess);
        // 显示棋盘
        upshow();
    }

    /**
     * 初始化棋子
     */
    public void initChess(){
        Chess[][] chess = boardChess.getChess();
        byte row,col;
        for(row=0; row<SIZE; ++row)
            for(col=0; col<SIZE; ++col)
                chess[row][col] = new Chess(Constant.EMPTY);
        byte mid = (byte)(SIZE >> 1);
        chess[mid - 1][mid - 1].setChess(Constant.WHITE);
        chess[mid][mid].setChess(Constant.WHITE);
        chess[mid - 1][mid].setChess(Constant.BLACK);
        chess[mid][mid - 1].setChess(Constant.BLACK);
    }

    /**
     * 更新棋子显示
     */
    public void upshow(){
        boolean[][] moves = boardChess.getMoves();
        Chess[][] chess = boardChess.getChess();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(moves[i][j]){
                    //显示可走的棋
                    chess[i][j].setChess(boardChess.getCanMove());
                }
                // 设置位置
                chess[i][j].setBounds(SPAN + j * ROW,SPAN + i * COL, ROW ,COL);
                this.add(chess[i][j]);
            }
        }
        this.repaint();
    }


    /**
     * 宽高
     * @return
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }

}
