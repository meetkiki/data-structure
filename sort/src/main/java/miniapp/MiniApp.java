package miniapp;


import miniapp.view.Screen;
import miniapp.view.screens.MainMenuScreen;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.util.ArrayList;

public class MiniApp {

    private final JFrame window;

    public static final int WIN_WIDTH = 1200;
    public static final int WIN_HEIGHT = 720;
    private final ArrayList<Screen> screens;


    public MiniApp() {
        screens = new ArrayList<>();
        window = new JFrame ("Sort visualiser");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 居中
//        window.setLocationRelativeTo(null);
        window.setVisible(true);

        window.pack();
    }

    public Screen getCurrentScreen() {
        return screens.get(screens.size() - 1);
    }

    public void pushScreen(Screen screen) {
        if (!screens.isEmpty()) {
            window.remove(getCurrentScreen());
        }
        screens.add(screen);
        window.setContentPane(screen);
        window.validate();
        screen.onOpen();
    }

    public void popScreen() {
        if (!screens.isEmpty()) {
            Screen prev = getCurrentScreen();
            screens.remove(prev);
            window.remove(prev);
            if (!screens.isEmpty()) {
                Screen current = getCurrentScreen();
                window.setContentPane(current);
                window.validate();
                current.onOpen();
            } else {
                window.dispose();
            }
        }
    }


    public void start() {
        pushScreen(new MainMenuScreen(this));
        window.pack();
    }

    /**
     * 排序间隔
     *
     *  change          3 * delay
     *  compare         1 * delay
     *  assignment      1 * delay
     */
    private static int DELAY = 10;

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "true");
        SwingUtilities.invokeLater(() -> new MiniApp().start());
    }


}
