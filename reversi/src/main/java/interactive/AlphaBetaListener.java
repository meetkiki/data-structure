package interactive;

import arithmetic.AlphaBeta;
import arithmetic.ParallelAlphaBeta;
import arithmetic.ReversiEvaluation;
import bean.BoardData;
import bean.MinimaxResult;
import common.Constant;
import game.Board;
import game.GameRule;

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
        // 显示棋盘
        BoardData boardChess = mouseListener.getBoardChess();
        // 棋盘UI
        Board board = mouseListener.getBoard();
        while (GameRule.valid_moves(boardChess,boardChess.getNextmove()) > 0){
            BoardData cloneData = boardChess.cloneData();
            MinimaxResult result = AlphaBeta.alphaBeta(cloneData);

            System.out.println(result + "Thread : " + Thread.currentThread().getName());
            // 必须要先走玩家棋
            mouseListener.getTask().join();

            // 走这步棋
            GameRule.MakeMoveRun makeMove = GameRule.getMakeMove(boardChess, result.getMove());
            makeMove.fork();
            makeMove.join();
            board.upshow();

            System.out.println("WHITE -- " + ReversiEvaluation.player_counters(boardChess.getChess(), Constant.WHITE));
            System.out.println("BLACK -- " + ReversiEvaluation.player_counters(boardChess.getChess(), Constant.BLACK));
        }

    }
}
