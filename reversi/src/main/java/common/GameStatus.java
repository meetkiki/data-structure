package common;


/**
 * 游戏进行的状态
 * @author Tao
 */
public enum GameStatus {
    /**
     * 初始
     */
    OPENING(0),
    /**
     * 中局
     */
    MIDDLE(1),
    /**
     * 终局
     */
    OUTCOME(2),
    /**
     * 结束
     */
    END(3);

    private int status;
    GameStatus(int status) {
        this.status = status;
    }
}
