import java.awt.Graphics;
import java.awt.Color;

/**
 * 形状抽象类，所有的形状都必须继承的类
 */
public abstract class Shape {
    /**
     * 颜色
     */
    private Color c;


    public void setColor(Color c){
        this.c = c;
    }
    public Color getColor(){
        return c;
    }
    /**
     * 绘制的方法
     * @param g
     */
    public abstract void draw(Graphics g);
}
