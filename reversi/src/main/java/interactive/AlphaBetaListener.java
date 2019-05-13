package interactive;

import arithmetic.AlphaBeta;
import arithmetic.ReversiEvaluation;
import bean.BoardChess;
import bean.BoardData;
import bean.MinimaxResult;
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

    public AlphaBetaListener(Observable ob) {
        this.ob = ob;
        this.ob.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        MouseListener mouseListener = (MouseListener) o;
        // 棋盘UI
        Board board = mouseListener.getBoard();
        BoardData boardData = board.getBoardData();
        BoardChess boardChess = boardData.getBoardChess();
        while (!GameRule.isShutDown(boardChess)){
            // 棋盘数据
            if (boardData.getCurrMove() == Constant.WHITE){
                AlphaBeta.Depth = 4;
            }else{
                AlphaBeta.Depth = 5;
            }
            long st = System.currentTimeMillis();
            MinimaxResult result = AlphaBeta.alphaBeta(boardChess);
            long en = System.currentTimeMillis();
            System.out.println("AlphaBeta 耗时 " + (en - st) + "ms");
            System.out.println(result + "Thread : " + Thread.currentThread().getName());
            // 必须要先走玩家棋
            mouseListener.getTask().join();

            // 走这步棋
            GameRule.MakeMoveRun makeMove = GameRule.getMakeMove(board, result.getMove());
            makeMove.fork();
            GameContext.getCall(makeMove);
            if (GameRule.isShutDown(boardChess)){
                System.out.println("对局结束!");
            }
            board.upshow();
            System.out.println("WHITE -- " + ReversiEvaluation.player_counters(boardChess.getChess(), Constant.WHITE));
            System.out.println("BLACK -- " + ReversiEvaluation.player_counters(boardChess.getChess(), Constant.BLACK));
        }
    }
}
