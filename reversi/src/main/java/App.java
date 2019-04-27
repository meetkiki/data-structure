import game.MainView;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * 黑白棋启动类
 *
 *
 * @author Tao
 */
public class App {

    private final JFrame window;

    public App() {
        window = new JFrame ("Reversi");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.pack();
        window.setVisible(true);
    }

    public void initialize() {
        window.setContentPane(new MainView());
        window.validate();
    }


    public void start() {
        initialize();
        window.pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new App().start();
        });
    }



}
