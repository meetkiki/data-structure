package game;

import bean.BoardChess;
import bean.BoardData;
import common.Constant;
import common.ImageConstant;
import interactive.MouseListener;
import utils.BoardUtil;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
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


    public static final int BOARD_WIDTH = 448;
    public static final int BOARD_HEIGHT = 448;
    /**
     * 棋盘数组
     */
    private BoardData boardData = new BoardData();
    /**
     * 可下子数组
     */
    private boolean[][] moves = new boolean[SIZE][SIZE];
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
        this.initBoard();
        this.addMouseListener(new MouseListener(this));
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
        GameRule.valid_moves(boardData,moves);
        // 显示棋盘
        upshow();
    }

    /**
     * 初始化棋子
     */
    public void initChess(){
        Chess[][] chess = boardData.getChess();
        for(byte row=0; row<SIZE; ++row)
            for(byte col=0; col<SIZE; ++col)
                chess[row][col] = new Chess(Constant.EMPTY);
        byte mid = (byte)(SIZE >> 1);
        chess[mid - 1][mid - 1].setChess(Constant.WHITE);
        chess[mid][mid].setChess(Constant.WHITE);
        chess[mid - 1][mid].setChess(Constant.BLACK);
        chess[mid][mid - 1].setChess(Constant.BLACK);
        // 初始化一维棋盘
        BoardChess boardChess = new BoardChess(chess, boardData.getCurrMove());
        boardData.setBoardChess(boardChess);
    }

    /**
     * 更新棋子显示
     *  这里使用SwingWorker异步更新UI
     */
    public void upshow(){
        Board board = this;
        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground(){
                boolean[][] moves = board.getMoves();
                Chess[][] chess = board.getChess();
                for (byte row = 0; row < SIZE; row++) {
                    for (byte col = 0; col < SIZE; col++) {
                        if(moves[row][col]){
                            //显示可走的棋
                            chess[row][col].setChess(board.getBoardData().getCanMove());
                        }
                        // 设置位置
                        chess[row][col].setBounds(SPAN + col * ROW,SPAN + row * COL, ROW ,COL);
                        board.add(chess[row][col]);
                    }
                }
                board.repaint();
                return null;
            }
        };
        swingWorker.execute();
    }

    /**
     * 宽高
     * @return
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }

    public boolean[][] getMoves() {
        return moves;
    }

    public Chess[][] getChess() {
        return boardData.getChess();
    }

    public byte getCurrMove() {
        return boardData.getCurrMove();
    }

    public byte setCurrMove(byte currMove) {
        boardData.setCurrMove(currMove);
        return currMove;
    }

    public BoardData getBoardData() {
        return boardData;
    }
}
