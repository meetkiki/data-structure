import java.awt.Graphics;
import java.util.ArrayList;
 
import javax.swing.JFrame;
 
/**
 * 重写了paint()方法的界面类
 * 
 * @author Micro
 * 
 */
public class MyWindow extends JFrame {

	/**
	 * 储存所画的图形的列表
	 */
	ArrayList<Shape> shapes = new ArrayList<Shape>();
 
	public ArrayList<Shape> getShapes() {
		return shapes;
	}

	/**
	 * 重写父类绘制窗体的方法
	 */
	public void paint(Graphics g) {
		super.paint(g);
		// 将队列中的形状取出来绘制
		for (int i = 0; i < shapes.size(); i++) {
			// 根据下标取出一个形状对象
			Shape sh = shapes.get(i);
			// 绘制
			sh.draw(g);
		}
	}
 
}
