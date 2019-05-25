package arithmetic;

import bean.BoardChess;
import bean.MinimaxResult;


/**
 * 搜索抽象类
 * @author Tao
 */
public interface SearchAlgorithm {

    /**
     * 抽象搜索
     * @param boardChess
     * @param depth
     * @return
     */
    MinimaxResult search(BoardChess boardChess,int depth);

}
