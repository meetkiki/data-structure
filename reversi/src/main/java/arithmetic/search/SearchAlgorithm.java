package arithmetic.search;

import arithmetic.subsidiary.TranspositionTable;
import bean.BoardChess;
import bean.MinimaxResult;


/**
 * 搜索抽象类
 * @author Tao
 */
public abstract class SearchAlgorithm {

    protected boolean enableTranspositionTable = true;

    public SearchAlgorithm() {
    }

    public SearchAlgorithm(boolean enableTranspositionTable) {
        this.enableTranspositionTable = enableTranspositionTable;
    }

    /**
     * 抽象搜索
     * @param boardChess
     * @param depth
     * @return
     */
    public abstract MinimaxResult search(BoardChess boardChess,int depth);


    protected void initTranspositionTableTable() {
        if (enableTranspositionTable) {
            TranspositionTable.resetZobrist();
        }
    }
}
