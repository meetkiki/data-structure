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
 *  观察者模式 操作之后通知AI操作
 * @date 2019/5/7 10:03
 */
public class MouseListener extends Observable implements java.awt.event.MouseListener {

    private Board board;

    /**
     * 棋盘数组 ui显示
     */
    private BoardData boardChess;
    /**
     * 模拟棋盘
     */
    private BoardData copyBoard;

    /**
     * 当前走棋的task
     */
    private GameRule.MakeMoveRun makeMove;

    public MouseListener(Board board,BoardData boardChess) {
        this.board = board;
        this.boardChess = boardChess;
        initListener();
    }

    /**
     * 初始化观察者
     */
    public void initListener(){
        AlphaBetaListener alphaBetaListener = new AlphaBetaListener(this);
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        board.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Move move = getMove(e);
        if (move == null || !GameRule.checkMove(boardChess,move,boardChess.getNextmove())){
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
            copyBoard = BoardUtil.copyBoard(boardChess);
            // 显示棋盘
            makeMove = GameRule.getMakeMove(boardChess, move);
            makeMove.fork();
            // 模拟棋盘
            GameRule.make_move(copyBoard.getChess(),move,copyBoard.getNextmove(),true);
            copyBoard.setNextmove(BoardUtil.change(copyBoard.getNextmove()));

            int next = GameRule.valid_moves(copyBoard, copyBoard.getNextmove());
            if (next > 0){
                // 交给计算机处理
                AiRun run = new AiRun();
                GameContext.invoke(run);
                return;
            }
            // 如果没有棋可以走 获得返回数据
            GameRule.valid_moves(boardChess,boardChess.getNextmove());
            boardChess.setNextmove(BoardUtil.change(boardChess.getNextmove()));
            GameRule.valid_moves(boardChess,boardChess.getNextmove());
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

    public BoardData getBoardChess() {
        return boardChess;
    }

    public void setBoardChess(BoardData boardChess) {
        this.boardChess = boardChess;
    }

    public BoardData getCopyBoard() {
        return copyBoard;
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
