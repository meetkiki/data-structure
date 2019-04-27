package common;

/**
 * 图片加载常量
 * @author Tao
 */
public enum  ImageConstant {
    /**
     * 白子
     */
    PLAYER_W("white.JPG"),
    /**
     * 黑子
     */
    PLAYER_B("black.JPG"),
    /**
     * 能够移动的白
     */
    CANMOVE_W("Canmove_W.JPG"),
    /**
     * 能够移动的黑
     */
    CANMOVE_B("Canmove_B.JPG"),
    /**
     * 空地
     */
    EMPTY("blank.JPG"),
    /**
     * 新落下的白
     */
    NWHITE("Nwhite.JPG"),
    /**
     * 新落下的黑
     */
    NBLACK("Nblack.JPG"),
    /**
     * 吃子旋转
     *      1 --- 6
     *     白     黑
     */
    OVERTURN1("overturn1.JPG"),
    /**
     * 吃子旋转
     *      1 --- 6
     *     白     黑
     */
    OVERTURN2("overturn2.JPG"),
    /**
     * 吃子旋转
     *      1 --- 6
     *     白     黑
     */
    OVERTURN3("overturn3.JPG"),
    /**
     * 吃子旋转
     *      1 --- 6
     *     白     黑
     */
    OVERTURN4("overturn4.JPG"),
    /**
     * 吃子旋转
     *      1 --- 6
     *     白     黑
     */
    OVERTURN5("overturn5.JPG"),
    /**
     * 吃子旋转
     *      1 --- 6
     *     白     黑
     */
    OVERTURN6("overturn6.JPG"),
    /**
     * 棋盘
     */
    BOARD("board.JPG");

    String resources;
    ImageConstant(String resources) {
        this.resources = resources;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }
}
