package interactive;

import arithmetic.AlphaBeta;
import arithmetic.Calculator;
import arithmetic.ReversiEvaluation;
import arithmetic.SearchAlgorithm;
import bean.BoardChess;
import bean.BoardData;
import bean.MinimaxResult;
import bean.Move;
import bean.Zobrist;
import common.Constant;
import game.Board;
import game.GameContext;
import game.GameRule;
import utils.BoardUtil;

import java.util.Observable;
import java.util.Observer;

/**
 * @author ypt
 * @ClassName AlphaBetaListener
 * @Description 观察者
 * @date 2019/5/7 10:14
 */
public class AlphaBetaListener implements Observer {

    private Observable ob;
    private Calculator calculator;

    public AlphaBetaListener(Observable ob) {
        this.ob = ob;
        this.ob.addObserver(this);
        this.calculator = new Calculator(new AlphaBeta());
    }

    @Override
    public void update(Observable o, Object arg) {
        MouseListener mouseListener = (MouseListener) o;
        // 棋盘UI
        Board board = mouseListener.getBoard();
        BoardData boardData = board.getBoardData();
        BoardChess boardChess = boardData.getBoardChess();
//        while (!GameRule.isShutDown(boardChess)) {
//            //             棋盘数据 r
//            if (boardData.getCurrMove() == Constant.WHITE) {
//                AlphaBeta.Depth = 6;
//            } else {
//                AlphaBeta.Depth = 2;
//            }
            long st = System.currentTimeMillis();
//            GameContext.sleep(2000);
            Move bestMove = calculator.searchMove(boardChess);
            long en = System.currentTimeMillis();
            System.out.println("搜索耗时 " + (en - st) + " ms");
            String move = board.getCurrMove() == Constant.WHITE ? "白" : "黑";
            System.out.println(bestMove + "currMove :" + move);
            System.out.println("搜索局面 : " + ReversiEvaluation.getCount() + " 个,置换表命中次数 : "+ Zobrist.getCount() +" 次,每秒: " +
                    (long)(Double.valueOf(ReversiEvaluation.getCount() + Zobrist.getCount()) / ((en - st) / 1000.0 )) + " 个局面");
            // 必须要先走玩家棋
            mouseListener.getTask().join();

            // 走这步棋
            GameRule.MakeMoveRun makeMove = GameRule.getMakeMove(board, bestMove);
            makeMove.fork();
            GameContext.getCall(makeMove);
//            if (GameRule.isShutDown(boardChess)){
//                System.out.println("对局结束!");
//            }else if (GameRule.valid_moves(boardChess) == 0){
//                GameRule.passMove(boardData);
//            }
//            System.out.println("WHITE -- " + ReversiEvaluation.player_counters(boardChess.getChess(), Constant.WHITE));
//            System.out.println("BLACK -- " + ReversiEvaluation.player_counters(boardChess.getChess(), Constant.BLACK));
//        }
        System.out.println("WHITE -- " + ReversiEvaluation.player_counters(boardChess.getChess(), Constant.WHITE));
        System.out.println("BLACK -- " + ReversiEvaluation.player_counters(boardChess.getChess(), Constant.BLACK));
        System.out.println("wCount -- " + boardChess.getwCount());
        System.out.println("bCount -- " + boardChess.getbCount());
        BoardUtil.display(boardChess);
    }
}
