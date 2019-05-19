package interactive;


import arithmetic.ReversiEvaluation;
import bean.BoardChess;
import bean.BoardData;
import bean.Move;
import common.Constant;
import game.Board;
import game.GameContext;
import game.GameRule;
import game.Menu;
import utils.BoardUtil;

import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.concurrent.ForkJoinTask;
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
     * 当前执棋类型
     */
    private byte curMove;
    /**
     * 设置监听
     */
    private Observer observer;
    /**
     * 当前走棋的task
     */
    private GameRule.MakeMoveRun makeMove;

    public MouseListener(Board board) {
        this.board = board;
        this.initListener();
    }

    /**
     * 初始化观察者
     */
    public void initListener(){
        observer = new AlphaBetaListener(this);
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        board.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Move move = getMove(e);
        if (move == null || !GameRule.checkMove(board,move)){
            return;
        }
        Menu menu = board.getMenu();
        if (menu.isOne() && curMove == board.getCurrMove()){
            MoveRun moveRun = new MoveRun(move);
            ForkJoinTask submit = GameContext.submit(moveRun);
//            GameContext.getCall(submit);
        }

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
                board.setRunning(true);
                BoardData boardData = board.getBoardData();
                BoardChess boardChess = boardData.getBoardChess();
                boolean isContinue;
                do {
                    // 交给计算机处理
                    computerMove();
                    if (GameRule.isShutDown(boardChess)){
                        int white = ReversiEvaluation.player_counters(boardChess.getChess(), Constant.WHITE);
                        int black = ReversiEvaluation.player_counters(boardChess.getChess(), Constant.BLACK);
                        JOptionPane.showMessageDialog(board.getMainView(),(black - white) > 0 ? "黑方胜利" :
                                ((black - white) == 0 ? "平局" : "白方胜利"), "提示", JOptionPane.WARNING_MESSAGE);
                        break;
                    }else if (GameRule.valid_moves(boardChess) == 0){
                        JOptionPane.showMessageDialog(board.getMainView(), BoardUtil.getChessStr(curMove) + "方需要放弃一手 由"
                                + BoardUtil.getChessStr(BoardUtil.change(curMove)) + "方连下", "提示", JOptionPane.WARNING_MESSAGE);
                        boardData.changePlayer();
                    }
                    isContinue = board.getCurrMove() != curMove;
                }while (isContinue);
            }else {
                // 如果没有棋可以走 获得返回数据
                BoardData boardData = board.getBoardData();
                boolean[][] moves = board.getMoves();
                // 切换棋手
                board.setCurrMove(BoardUtil.change(board.getCurrMove()));
                GameRule.valid_moves(boardData,moves);
                board.upshow();
            }
        }

        private void computerMove() {
            setChanged();
            notifyObservers();
            board.setRunning(false);
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

    public byte getCurMove() {
        return curMove;
    }

    public void setCurMove(byte curMove) {
        this.curMove = curMove;
    }

    public GameRule.MakeMoveRun getTask() {
        return makeMove;
    }
}
