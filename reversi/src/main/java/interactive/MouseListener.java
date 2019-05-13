package interactive;


import bean.BoardData;
import bean.Move;
import common.Constant;
import game.Board;
import game.GameContext;
import game.GameRule;
import utils.BoardUtil;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.concurrent.RecursiveAction;

import static game.Board.BOARD_HEIGHT;
import static game.Board.BOARD_WIDTH;

/**
 * @author ypt
 * @ClassName MouseListener
 *  观察者模式 操作之后通知
 *      观察者操作
 *      AI
 * @date 2019/5/7 10:03
 */
public class MouseListener extends Observable implements java.awt.event.MouseListener {

    private Board board;


    /**
     * 当前走棋的task
     */
    private GameRule.MakeMoveRun makeMove;

    public MouseListener(Board board) {
        this.board = board;
        initListener();
    }

    /**
     * 初始化观察者
     */
    public void initListener(){
        new AlphaBetaListener(this);
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        board.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Move move = getMove(e);
        if (move == null || !GameRule.checkMove(board,move)){
            return;
        }
        MoveRun moveRun = new MoveRun(move);
        GameContext.submit(moveRun);
    }

    /**
     * 走棋线程
     */
    class MoveRun implements Runnable{

        private Move move;

        public MoveRun(Move move) {
            this.move = move;
        }

        @Override
        public void run() {
            // 显示棋盘
            makeMove = GameRule.getMakeMove(board, move);
            Integer next = makeMove.fork().join();
            if (next > 0){
                // 交给计算机处理
                AiRun run = new AiRun();
                GameContext.invoke(run);
                return;
            }
            // 如果没有棋可以走 获得返回数据
            BoardData boardData = board.getBoardData();
            boolean[][] moves = board.getMoves();
            // 切换棋手
            board.setCurrMove(BoardUtil.change(board.getCurrMove()));
            GameRule.valid_moves(boardData,moves);
            board.upshow();
        }
    }

    /**
     * 异步通知线程
     */
    class AiRun extends RecursiveAction{
        @Override
        protected void compute() {
            setChanged();
            notifyObservers();
        }
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }
    /**
     * 获取下棋的坐标
     * @param e
     * @return
     */
    private Move getMove(MouseEvent e){
        int Point_x = e.getX();
        int Point_y = e.getY();
        //判断棋盘是否在下棋范围 //如果未越界
        if(isBorder(Point_x, Point_y)){
            byte[] x_y = new byte[2];
            //转化为棋盘坐标 对应col
            x_y[0] = (byte)((Point_x - Constant.SPAN) / Constant.ROW);
            // 对应row
            x_y[1] = (byte)((Point_y - Constant.SPAN) / Constant.COL);
            return new Move(x_y[1],x_y[0]);
        }else{
            return null;
        }
    }

    private boolean isBorder(int point_x, int point_y) {
        return !(point_x > (BOARD_WIDTH - Constant.SPAN - 5) || point_x < Constant.SPAN + 5 ||
                point_y > (BOARD_HEIGHT - Constant.SPAN-5) || point_y < Constant.SPAN + 5);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    public GameRule.MakeMoveRun getTask() {
        return makeMove;
    }
}
