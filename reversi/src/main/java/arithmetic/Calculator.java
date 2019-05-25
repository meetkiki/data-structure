package arithmetic;

import bean.BoardChess;
import bean.MinimaxResult;
import bean.Move;
import common.Constant;

import java.util.LinkedList;

/**
 * AI调度类
 * @author Tao
 */
public class Calculator {

    private SearchAlgorithm searchAlgorithm;
    /**
     * 搜索深度
     */
    private int depth = Constant.DEFAULTDEPTH;
    private int outDepth = Constant.OUTDEPTH;

    public Calculator(SearchAlgorithm searchAlgorithm){
        this.searchAlgorithm = searchAlgorithm;
    }

    /**
     * 搜索最佳走法
     * @return
     */
    public Move searchMove(BoardChess boardChess){
        LinkedList<Byte> empty = boardChess.getEmpty();
        // 如果是终局
        if (empty.size() <= outDepth){
            // 终局深度为空格的长度
            MinimaxResult result = this.searchAlgorithm.search(boardChess, outDepth);
            return result.getMove();
        }
        // MTD 算法
        MinimaxResult result = this.searchAlgorithm.search(boardChess, depth);
        return result.getMove();
    }

}
