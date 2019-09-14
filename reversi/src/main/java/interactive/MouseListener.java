package interactive;


import arithmetic.evaluation.ReversiEvaluation;
import bean.BoardChess;
import bean.BoardData;
import bean.Move;
import common.Constant;
import common.GameStatus;
import game.Board;
import game.GameContext;
import game.GameRule;
import game.MainView;
import game.Menu;
import utils.BoardUtil;

import javax.swing.JOptionPane;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;

import static common.Constant.SIZE;
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
     * 当前计算机执棋类型
     */
    private byte curMove;

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
        new AlphaBetaListener(this);
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        board.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Move move = getMove(e);
        if (move == null){
            return;
        }
        Menu menu = GameContext.getBean(Menu.class);
        if (menu.isOne() && curMove == board.getCurrMove() && GameRule.checkMove(board,move)){
            MoveRun moveRun = new MoveRun(move);
            GameContext.submit(moveRun);
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
                BoardData boardData = board.getBoardData();
                BoardChess boardChess = boardData.getBoardChess();
                do {
                    board.setRunning(true);
                    // 交给计算机处理
                    computerMove();
                    if (checkShutDown(boardChess)){
                        break;
                    }
                    if (checkContinue(boardChess)) {
                        GameRule.passMove(boardData);
                        board.upshow();
                    }
                }while (board.getCurrMove() != curMove);
            }else if (checkShutDown(board.getBoardChess())){
                return;
            }else if(checkContinue(board.getBoardChess())){
                GameRule.passMove(board.getBoardData());
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
    /**
     * 校验是否由改方继续
     * @param data
     * @return
     */
    public boolean checkContinue(BoardChess data){
        MainView mainView = GameContext.getBean(MainView.class);
        GameRule.valid_moves(data);
        if (data.getStatus() != GameStatus.END){
            if (data.getOurMobility() == 0 && data.getOppMobility() > 0){
                JOptionPane.showMessageDialog(mainView, BoardUtil.getChessStr(data.getCurrMove()) +
                        "方需要放弃一手 由" + BoardUtil.getChessStr(data.getOther()) + "方连下", "提示", JOptionPane.WARNING_MESSAGE);
                return true;
            }
        }
        return false;
    }


    /**
     * 校验是否结束游戏
     * @param data
     * @return
     */
    public boolean checkShutDown(BoardChess data){
        MainView mainView = GameContext.getBean(MainView.class);
        GameRule.valid_moves(data);
        if (data.getStatus() == GameStatus.END){
            int white = GameRule.player_counters(board.getBoardChess().getChess(), Constant.WHITE);
            int black = GameRule.player_counters(board.getBoardChess().getChess(), Constant.BLACK);
            JOptionPane.showMessageDialog(mainView, (black - white) > 0 ? "黑方胜利" :
                    ((black - white) == 0 ? "平局" : "白方胜利"), "提示", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }
}
