package common;

/**
 * @author Tao
 */

public enum  DirEnum {

    /**
     * Warren Smith 棋盘方向   右 左 左下 右上 下 上 右下 左上
     */
    R(1),
    L(-1),
    LD(8),
    RU(-8),
    D(9),
    U(-9),
    RD(-9),
    LU(-9);
    private int dir;
    DirEnum(int dir) {
        this.dir = dir;
    }

    public int getDir() {
        return dir;
    }
}
