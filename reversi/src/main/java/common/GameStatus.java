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
    public int getStatus() {
        return status;
    }
    public static final GameStatus findStatus(int status){
        switch (status){
            case 0:return OPENING;
            case 1:return MIDDLE;
            case 2:return OPENING;
            case 3:return END;
            default:break;
        }
        return null;
    }
}
