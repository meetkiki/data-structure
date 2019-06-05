import game.MainView;
//import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.JFrame;
//import javax.swing.SwingUtilities;
//import javax.swing.UIManager;

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
        window.setLocationRelativeTo(null);
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
//        try {
//            BeautyEyeLNFHelper.launchBeautyEyeLNF();
//            UIManager.put("RootPane.setupButtonVisible", false);
//            SwingUtilities.invokeLater(() -> new App().start());
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
    }

}
