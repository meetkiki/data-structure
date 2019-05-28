package arithmetic;

import arithmetic.search.AlphaBeta;
import arithmetic.search.MTDSearch;
import bean.BoardChess;
import bean.MinimaxResult;
import common.Constant;

import java.util.LinkedList;

/**
 * AI调度类
 * @author Tao
 */
public class Calculator {

    private static SearchAlgorithm alphaBeta = new AlphaBeta();
    private static SearchAlgorithm mtdSearch = new MTDSearch();

    /**
     * 搜索深度
     */
    private int depth = Constant.DEFAULTDEPTH;
    private int outDepth = Constant.OUTDEPTH;

    public Calculator(){}

    /**
     * 搜索最佳走法
     * @return
     */
    public MinimaxResult searchMove(BoardChess boardChess){
        LinkedList<Byte> empty = boardChess.getEmpty();
        // 如果是终局
        if (empty.size() <= outDepth){
            // 终局深度为空格的长度
            MinimaxResult result = alphaBeta.search(boardChess, outDepth);
            return result;
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

}
