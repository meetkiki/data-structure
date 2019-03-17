import java.awt.*;


/**
 * 在界面上画一个字符
 */
public class Word extends Shape {
    String c;
    int x;
    int y;

    public Word(String c, int x, int y) {
        this.c = c;
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.getColor());
        g.drawString(c, x, y);
    }
}
