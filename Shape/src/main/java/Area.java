import java.awt.Graphics;

public class Area extends Shape {

    private int x, y, width, height;

    public Area(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        // /设置画布颜色
        g.setColor(this.getColor());
        g.drawRect(x, y, width, height);
    }
}
