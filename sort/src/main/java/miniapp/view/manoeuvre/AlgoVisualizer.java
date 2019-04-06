package miniapp.view.manoeuvre;

import miniapp.Enum.SortFrameEnum;
import miniapp.abstraction.Sort;
import miniapp.entity.SortData;

import java.awt.*;
import java.util.concurrent.CountDownLatch;

public class AlgoVisualizer {

    private static int SCENEWIDTH = 1200;
    private static int SCENEHEIGHT = 1000;

    private SortData data;
    private AlgoFrame frame;
    private Sort sort;
    private Environment environment;
    private CountDownLatch count;


    public AlgoVisualizer(String sort){
        this.count = new CountDownLatch(1);
        this.data = new SortData();
        this.sort = SortFrameEnum.getFrame(sort);
        // 初始化视图
        init();
    }

    private void init() {
        EventQueue.invokeLater(() -> {
            this.frame = new AlgoFrame(data,false);
            this.environment = new Environment(new SortFrameCommand(sort,frame));
            this.count.countDown();
            new Thread(()->environment.invoke()).start();
        });
    }

    /**
     * 设置排序间隔
     * @param delay
     */
    public void setDelay(int delay){
        data.setDELAY(delay);
    }

    /**
     * 获取代理执行对象
     * @return
     */
    public Environment getEnvironment(){
        try {
            count.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return environment;
    }
}