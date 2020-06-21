package arithmetic;

import arithmetic.evaluation.ReversiEvaluation;
import arithmetic.search.AlphaBeta;
import arithmetic.search.MTDSearch;
import arithmetic.search.SearchAlgorithm;
import bean.BoardChess;
import bean.MinimaxResult;
import common.Constant;
import game.GameRule;

import java.util.LinkedList;

/**
 * AI调度类
 * @author Tao
 */
public class Calculator {

    private byte player;

    private final SearchAlgorithm alphaBeta;
    private final SearchAlgorithm mtdSearch;

    /**
     * 搜索深度
     */
    private final int depth;
    private final int outDepth;

    public Calculator(ReversiEvaluation evaluation){
        this(evaluation,Constant.DEFAULTDEPTH,Constant.OUTDEPTH);
    }

    public Calculator(ReversiEvaluation evaluation, int depth, int outDepth) {
        AlphaBeta alphaBeta = new AlphaBeta(evaluation);
        this.mtdSearch = new MTDSearch(alphaBeta);
        this.alphaBeta = alphaBeta;
        this.depth = depth;
        this.outDepth = outDepth;
    }

    /**
     * 搜索最佳走法
     * @return
     */
    public MinimaxResult searchMove(BoardChess boardChess){
        int moves = GameRule.valid_moves(boardChess);
        if (moves == 0){
            throw new RuntimeException("不该选手下棋!");
        }
        LinkedList<Byte> empty = boardChess.getEmpty();
        // 如果是终局
        if (empty.size() <= outDepth){
            // 终局深度为空格的长度
            return alphaBeta.search(boardChess, outDepth);
        }
        MinimaxResult result = null;
        try {
            // MTD 算法
            result = mtdSearch.search(boardChess, depth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public byte getPlayer() {
        return player;
    }

    public Calculator setPlayer(byte player) {
        this.player = player;
        return this;
    }

    public SearchAlgorithm getAlphaBeta() {
        return alphaBeta;
    }
}
