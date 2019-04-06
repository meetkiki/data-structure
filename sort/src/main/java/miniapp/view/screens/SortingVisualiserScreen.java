package miniapp.view.screens;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingWorker;

import miniapp.MiniApp;
import miniapp.abstraction.SortVisual;
import miniapp.entity.SortData;
import miniapp.view.Screen;
import miniapp.view.manoeuvre.AlgoFrame;
import miniapp.view.manoeuvre.Environment;
import miniapp.view.manoeuvre.SortFrameCommand;

/**
 * The main class for the sort visualiser GUI
 *
 */
public final class SortingVisualiserScreen extends Screen {
    private final AlgoFrame algoFrame;
    private final SortData sortData;
    private final ArrayList<SortVisual> sortQueue;
    private Environment environment;

    /**
     * Creates the GUI
     * @param algorithms List of algorithms to run for visualisation
     * @param playSounds Whether or not you want the algorithm to play sounds
     * @param app The main application
     */
    public SortingVisualiserScreen(ArrayList<SortVisual> algorithms, boolean playSounds, MiniApp app) {
        super(app);
        setLayout(new BorderLayout());
        sortData = new SortData();
        algoFrame = new AlgoFrame(sortData,playSounds);
        sortQueue = algorithms;
        add(algoFrame, BorderLayout.CENTER);
    }
    
    private void longSleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } 
    }

    private void shuffleAndWait() {
        algoFrame.shuffle();
    }

    @Override
    public void onOpen() {
        //This would block the EventDispatchThread, and so
        //it must run on a worker thread
        SwingWorker<Void, Void> swingWorker = new SwingWorker<Void,Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (SortVisual algorithm : sortQueue) {
                    shuffleAndWait();
                    algoFrame.setName(algorithm.methodName());
                    algoFrame.setSortVisual(algorithm);

                    environment = new Environment(new SortFrameCommand(algorithm, algoFrame));
                    environment.invoke();
                    longSleep();
                }
                return null;
            }
            
            @Override
            public void done() {
                app.popScreen(); 
            }
        };
        
        swingWorker.execute();
    }
}
