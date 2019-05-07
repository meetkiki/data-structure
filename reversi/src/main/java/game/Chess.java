package game;


import common.Constant;
import common.ImageConstant;
import utils.BoardUtil;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * @author Tao
 */
public class Chess extends JPanel implements Cloneable{
    /**
     * 棋子状态
     */
    private byte chess;
    /**
     * 图标
     *
     *  transient 不序列化
     */
    private transient Image image;

    /**
     * 新下的地点
     */
    private transient boolean newMove;

    /**
     *  /设置棋子状态
     */
    public Chess(byte chess) {
        this.setChess(chess);
    }


    /**
     * 仅仅设置棋子状态
     * @param chess
     */
    public void onlyChess(byte chess){
        this.chess = chess;
    }

    /**
     * //设置棋子状态
     * @param chess
     */
    public void setChess(byte chess) {
        Map<ImageConstant, ImageIcon> imageIconMap = GameContext.getResources();
        switch (chess){
            case Constant.WHITE:
                this.image = imageIconMap.get(ImageConstant.PLAYER_W).getImage();
                break;
            case Constant.BLACK:
                this.image = imageIconMap.get(ImageConstant.PLAYER_B).getImage();
                break;
            case Constant.DOT_W:
                this.image = imageIconMap.get(ImageConstant.CANMOVE_W).getImage();
                break;
            case Constant.DOT_B:
                this.image = imageIconMap.get(ImageConstant.CANMOVE_B).getImage();
                break;
            case Constant.EMPTY:
                this.image = imageIconMap.get(ImageConstant.EMPTY).getImage();
                break;
            default:break;
        }
        this.onlyChess(chess);
        this.repaint();
    }

    /**
     * 设置新走棋状态
     * @param chess
     */
    public void setNewPlayer(byte chess){
        Map<ImageConstant, ImageIcon> imageIconMap = GameContext.getResources();
        if(chess == Constant.WHITE){
            this.image = imageIconMap.get(ImageConstant.NWHITE).getImage();
        }else if(chess == Constant.BLACK){
            this.image = imageIconMap.get(ImageConstant.NBLACK).getImage();
        }
        this.setNewMove(true);
        this.onlyChess(chess);
        this.repaint();
    }

    public byte getChess() {
        return chess;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //下面这行是为了背景图片可以跟随窗口自行调整大小，可以自己设置成固定大小
        g.drawImage(image, 0, 0,Constant.ROW, Constant.COL, this);
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Chess{" +
                "chess=" + chess +
                '}';
    }

    public boolean isNewMove() {
        return newMove;
    }

    public void setNewMove(boolean newMove) {
        this.newMove = newMove;
    }

    /**
     * //转变棋子动画
     * @param player
     */
    public BoardUtil.TimerRunTask change(byte player, CountDownLatch latch) {
        return BoardUtil.converSion(player,this,latch);
    }

    @Override
    public void repaint() {
        super.repaint();
    }


    @Override
    public Chess clone(){
        Chess clone = null;
        try {
            clone = (Chess) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return clone;
    }



}
