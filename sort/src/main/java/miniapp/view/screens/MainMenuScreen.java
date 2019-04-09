package miniapp.view.screens;

import miniapp.Enum.SortFrameEnum;
import miniapp.MiniApp;
import miniapp.abstraction.SortVisual;
import miniapp.view.Screen;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * @author Tao
 */
public class MainMenuScreen extends Screen {

    private static final Color BACKGROUND_COLOUR = new Color(84,84,84);
    private final ArrayList<AlgorithmCheckBox> checkBoxes;

    public MainMenuScreen(MiniApp app) {
        super(app);
        checkBoxes = new ArrayList<>();
        setUpGUI();
    }
    private void initContainer(JPanel p) {
        p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
        p.setBackground(BACKGROUND_COLOUR);
    }

    private void addCheckBox(SortVisual algorithm, JPanel panel) {
        JCheckBox box = new JCheckBox("", true);
        box.setAlignmentX(Component.LEFT_ALIGNMENT);
        box.setBackground(BACKGROUND_COLOUR);
        box.setForeground(Color.WHITE);
        checkBoxes.add(new AlgorithmCheckBox(algorithm, box));
        panel.add(box);
    }

    private void setUpGUI() {
        // 排序选择框
        JPanel sortAlgorithmContainer = new JPanel();
        // 音效
        JPanel optionsContainer = new JPanel();
        // 外边框
        JPanel outerContainer = new JPanel();
        //outerContainer.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        initContainer(this);
        initContainer(optionsContainer);
        initContainer(sortAlgorithmContainer);

        outerContainer.setBackground(BACKGROUND_COLOUR);
        outerContainer.setLayout(new BoxLayout(outerContainer, BoxLayout.LINE_AXIS));

        try {
            ClassLoader loader = this.getClass().getClassLoader();
            InputStream inputStream = loader.getResourceAsStream("logo.png");
            BufferedImage image = ImageIO.read(inputStream);
            JLabel label = new JLabel(new ImageIcon(image));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(label);
        } catch (IOException e) {
            System.out.println("Unable to load logo");
        }

        sortAlgorithmContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        SortFrameEnum[] sortFrameEnums = SortFrameEnum.values();
        for (SortFrameEnum sortEnum : sortFrameEnums) {
            addCheckBox(sortEnum.getSortFrame(),sortAlgorithmContainer);
        }

        JCheckBox soundCheckBox = new JCheckBox("Play Sounds");
        soundCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        soundCheckBox.setBackground(BACKGROUND_COLOUR);
        soundCheckBox.setForeground(Color.WHITE);

        optionsContainer.add(soundCheckBox);
        JButton startButton = new JButton("Begin Visual Sorter");
        // 添加按钮监听
        startButton.addActionListener((ActionEvent e) -> {
            ArrayList<SortVisual> algorithms = new ArrayList<>();
            for (AlgorithmCheckBox cb : checkBoxes) {
                if (cb.isSelected()) {
                    algorithms.add(cb.getAlgorithm());
                }
            }
            app.pushScreen(
                new SortingVisualiserScreen(
                    algorithms,
                    soundCheckBox.isSelected(),
                    app
                ));
        });
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton startAnalysis = new JButton("Begin Analysis Sorter");
        // 添加按钮监听
        startAnalysis.addActionListener((ActionEvent e) -> app.pushScreen(new SortingAnalysisScreen(app)));
        startAnalysis.setAlignmentX(Component.CENTER_ALIGNMENT);
        outerContainer.add(optionsContainer);
        outerContainer.add(Box.createRigidArea(new Dimension(5,0)));
        outerContainer.add(sortAlgorithmContainer);
        outerContainer.add(startAnalysis);

        int gap = 15;
        add(Box.createRigidArea(new Dimension(0, gap)));
        add(outerContainer);
        add(Box.createRigidArea(new Dimension(0, gap)));
        add(startButton);
        add(Box.createRigidArea(new Dimension(0, gap)));
        add(startAnalysis);
    }


    @Override
    public void onOpen() {
        checkBoxes.forEach((box) -> box.unselect());
    }

    private class AlgorithmCheckBox {
        private final SortVisual algorithm;
        private final JCheckBox box;

        public AlgorithmCheckBox(SortVisual algorithm, JCheckBox box) {
            this.algorithm = algorithm;
            this.box = box;
            this.box.setText(algorithm.methodName());
        }

        public void unselect() {
            box.setSelected(false);
        }

        public boolean isSelected() {
            return box.isSelected();
        }

        public SortVisual getAlgorithm() {
            return algorithm;
        }
    }

}
