package miniapp.view.manoeuvre;

import miniapp.Enum.LineColorEnum;
import miniapp.abstraction.OperatingArray;
import miniapp.abstraction.SortVisual;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.swing.*;

public class AlgoFrame extends JPanel implements Cloneable, OperatingArray {
    private int canvasWidth;
    private int canvasHeight;
    private boolean playSounds;
    private int width;
    private SortVisual sortVisual;
    /**
     * 是否为主
     */
    private boolean master = false;
    /**
     * 副Frame
     */
    private AlgoFrame auxFrame;
    /**
     * 算法名称
     */
    private String algorithmName = "";
    /**
     * 间隔输入框
     */
    private JSpinner spinner;
    /**
     * 等待时间
     */
    private int delay = 10;
    /**
     * 判断是否为主
     * @return
     */
    public boolean isMaster(){
        return master;
    }

    /**
     * 排序data
     */
    private SortData data;

    private MidiSoundPlayer midiSoundPlayer;

    public AlgoFrame(SortData data,boolean playSounds){
        // 双缓存
        super(true);

        this.data = data;
        this.canvasWidth = SortData.DEFAULT_WIN_WIDTH;
        this.canvasHeight = SortData.DEFAULT_WIN_HEIGHT;
        this.delay = data.getDELAY();

        this.playSounds = playSounds;
        this.width = canvasWidth / data.size();

        this.midiSoundPlayer = new MidiSoundPlayer(SortData.NUM_HEIGHT);

        this.spinner = new JSpinner(new SpinnerNumberModel(data.getDELAY(), 1, 200, 1));
        this.spinner.addChangeListener((event) -> {
            this.delay = (Integer) spinner.getValue();
            this.data.setDELAY(delay);
            if (auxFrame != null){
                this.auxFrame.getData().setDELAY(delay);
                this.auxFrame.delay = delay;
            }
        });
        this.add(spinner);
        this.spinner.setValue(data.getDELAY());
    }

    public SortVisual getSortVisual() {
        return sortVisual;
    }

    public void setSortVisual(SortVisual sortVisual) {
        this.sortVisual = sortVisual;
        this.algorithmName = sortVisual.methodName();
    }

    public int getCanvasWidth(){return canvasWidth;}
    public int getCanvasHeight(){return canvasHeight;}

    /**
     * 重新渲染视图
     * @param data
     */
    public void render(SortData data){
        this.data = data;
        repaint();
    }

    public SortData getData() {
        return data;
    }

    /**
     * 设置显示参数
     *
     * @param orderedIndex          排序好数组
     * @param currentCompareIndex   当前数据索引
     * @param currentChangeIndex    待交换数据索引
     */
    public void setData(int orderedIndex, int currentCompareIndex, int currentChangeIndex){
        // 初始值为-1
        setData(-1,orderedIndex,currentCompareIndex,currentChangeIndex,true);
    }

    /**
     * 设置参数
     * @param orderedIndex          排序好数组区间
     * @param currentCompareIndex   当前数据索引
     * @param currentChangeIndex    待交换数据索引
     */
    public void setData(int orderedStart, int orderedIndex, int currentCompareIndex, int currentChangeIndex){
        setData(orderedStart,orderedIndex,currentCompareIndex,currentChangeIndex,true);
    }

    /**
     * 设置参数
     * @param orderedIndex          排序好数组区间
     * @param currentCompareIndex   当前数据索引
     * @param currentChangeIndex    待交换数据索引
     * @param isStep                是否等待
     */
    public void setData(int orderedStart, int orderedIndex, int currentCompareIndex, int currentChangeIndex,boolean isStep){
        this.data.addOrdereds(orderedStart,orderedIndex);
        // 更新参数
        updateData(currentCompareIndex,currentChangeIndex,isStep);
    }

    /**
     * 更新排序区间 不等待
     * @param orderedStart
     * @param orderedIndex
     */
    public void updateOrdereds(int orderedStart, int orderedIndex){
        data.addOrdereds(orderedStart,orderedIndex);
        // 更新参数
        updateData(orderedStart,orderedIndex,false);
    }

    /**
     * 更新排序区间 不等待
     * @param orderedIndex
     */
    public void updateOrdereds(int orderedIndex){
        data.addOrdereds(-1,orderedIndex);
        // 更新参数
        updateData(-1,orderedIndex,false);
    }

    /**
     * 初始化排序区间
     */
    public void initOrdereds() {
        data.initOrdereds();
        // 设置焦点
        spinner.requestFocus();
    }

    /**
     * 更新参数
     * @param currentCompareIndex   当前数据索引
     * @param currentChangeIndex    待交换数据索引
     */
    public void updateData(int currentCompareIndex, int currentChangeIndex){
        updateData(currentCompareIndex,currentChangeIndex,true);
    }

    /**
     * 更新参数
     * @param currentCompareIndex   当前数据索引
     * @param currentChangeIndex    待交换数据索引
     */
    public void updateData(int currentCompareIndex, int currentChangeIndex,boolean isStep){
        this.data.currentCompareIndex = currentCompareIndex;
        this.data.currentChangeIndex = currentChangeIndex;
        if (isStep) pause(this.data.getDELAY());
        this.render(this.data);
        if (playSounds){
            if (currentChangeIndex < 0 || currentChangeIndex >= data.size()){
                currentChangeIndex = 0;
            }
            midiSoundPlayer.makeSound(this.data.get(currentChangeIndex));
        }
//        System.out.println(data.getOrdereds());
//        System.out.println("change --- " + this.getChange());
//        System.out.println("compare --- " + this.getCompare());
    }


    /**
     * 比较两个数的大小返回布尔型  判断A是否大于B
     * @param curl
     * @param curr
     * @return
     */
    @Override
    public boolean compareMore(int curl, int curr){
        return compare(curl,curr) > 0;
    }


    /**
     * 比较两个数的大小返回int型 小于 等于 大于
     * @param curl
     * @param curr
     * @return
     */
    public int compare(int curl, int curr){
        data.compareIncrement();
        pause(data.getDELAY());
        return curl - curr;
    }

    public void pause(int dalay){
        repaint();
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
    }

    /**
     * 比较两个数的大小返回布尔型  判断A是否等于B
     * @param curl
     * @param curr
     * @return
     */
    @Override
    public boolean compareEqual(int curl, int curr){
        return compare(curl,curr) == 0;
    }

    /**
     * 比较两个数的大小返回布尔型  判断A是否小于B
     * @param curl
     * @param curr
     * @return
     */
    @Override
    public boolean compareLess(int curl, int curr){
        return compare(curl,curr) < 0;
    }

    /**
     * 比较两个数的大小返回布尔型  判断A是否大于等于B
     * @param curl
     * @param curr
     * @return
     */
    @Override
    public boolean compareMoreOrEqual(int curl, int curr) {
        return compare(curl,curr) >= 0;
    }

    /**
     * 比较两个数的大小返回布尔型  判断A是否小于等于B
     * @param curl
     * @param curr
     * @return
     */
    @Override
    public boolean compareLessOrEqual(int curl, int curr) {
        return compare(curl,curr) <= 0;
    }

    /**
     * 比较两个数的大小返回布尔型
     * @param currentCompareIndex
     * @param currentChangeIndex
     * @return
     */
    public boolean less(int currentCompareIndex, int currentChangeIndex){
        // 设置指向
        updateData(currentCompareIndex,currentChangeIndex);
        return data.less(currentCompareIndex,currentChangeIndex);
    }
    /**
     * 比较两个数的是否小于等于  返回布尔型
     * @param currentCompareIndex
     * @param currentChangeIndex
     * @return
     */
    public boolean lessOrEqual(int currentCompareIndex, int currentChangeIndex){
        // 设置指向
        updateData(currentCompareIndex,currentChangeIndex);
        return data.lessOrEqual(currentCompareIndex,currentChangeIndex);
    }

    /**
     * 交换两个数的数据
     * @param currentCompareIndex
     * @param currentChangeIndex
     */
    public void swap(int currentCompareIndex, int currentChangeIndex){
        // 设置指向
        updateData(currentCompareIndex,currentChangeIndex);
//        // 交换两个数据的值需要3次操作 再等待2次
//        pause(this.data.getDELAY());
//        pause(this.data.getDELAY());
        this.data.swap(currentCompareIndex,currentChangeIndex);
    }

    /**
     * 获取克隆对象
     * @return
     */
    public AlgoFrame cloneData(){
        try {
            SortData sortData = (SortData)data.getClone();
            sortData.setNumbers(data.cloneData());
            this.master = true;
            // 克隆为副 this为主
            auxFrame = (AlgoFrame)super.clone();
            auxFrame.spinner = this.spinner;
            auxFrame.midiSoundPlayer = this.midiSoundPlayer;
            auxFrame.auxFrame = this;
            auxFrame.master = false;
            auxFrame.data = sortData;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            throw new RuntimeException("Clone Error!");
        }
        return auxFrame;
    }
    /**
     *  获得数据长度
     */
    public int length(){
        return data.size();
    }

    /**
     *  获得数据
     */
    public int get(int index){
        return data.get(index);
    }

    /**
     * 数组交换次数
     * @return
     */
    public int getChange(){
        return data.getArrayChanges();
    }

    /**
     * 替换传入索引的值
     * @param srcIndex
     * @param value
     * @return
     */
    public void replace(int srcIndex, int value){
        replace(this.data,srcIndex,value);
    }

    /**
     * 替换传入索引的值
     * @param data 数据项
     * @param srcIndex
     * @param value
     * @return
     */
    public void replace(SortData data,int srcIndex, int value){
        // 设置指向
        updateData(srcIndex,srcIndex);
        data.set(srcIndex,value);
    }

    /**
     * 替换传入索引的值
     * @param data 数据项
     * @param srcIndex
     * @param value
     * @return
     */
    public void replace(AlgoFrame data,int srcIndex, int value){
        // 设置指向
        optimizeSetData(this,data,true,-1,-1,srcIndex,srcIndex);
        data.getData().set(srcIndex,value);
    }


    /**
     * 归并排序 更新区间不耗时
     * @param frame             原数组
     * @param orderedStart
     * @param orderedIndex
     */
    public void optimizeUpdateOrdered(AlgoFrame frame, int orderedStart, int orderedIndex){
        optimizeSetData(frame,this,false,orderedStart,orderedIndex,-1,-1);
    }

    /**
     * 归并优化后可视化更新显示
     * @param frame             原数组
     * @param orderedStart
     * @param orderedIndex
     * @param currentCompareIndex
     * @param currentChangeIndex
     */
    public void optimizeSetData(AlgoFrame frame, int orderedStart, int orderedIndex, int currentCompareIndex, int currentChangeIndex){
        optimizeSetData(frame,this,true,orderedStart,orderedIndex,currentCompareIndex,currentChangeIndex);
    }

    /**
     * 归并优化后可视化更新显示
     * @param frame             原数组
     * @param orderedStart
     * @param orderedIndex
     * @param currentCompareIndex
     * @param currentChangeIndex
     */
    public void optimizeSetData(AlgoFrame frame,boolean isStep, int orderedStart, int orderedIndex, int currentCompareIndex, int currentChangeIndex){
        optimizeSetData(frame,this,isStep,orderedStart,orderedIndex,currentCompareIndex,currentChangeIndex);
    }

    /**
     * 归并优化后可视化更新显示
     * @param frame             原数组
     * @param auxFrame          拷贝数组
     * @param orderedStart
     * @param orderedIndex
     * @param currentCompareIndex
     * @param currentChangeIndex
     */
    public void optimizeSetData(AlgoFrame frame, AlgoFrame auxFrame,boolean isStep, int orderedStart, int orderedIndex, int currentCompareIndex, int currentChangeIndex){
        frame.setData(orderedStart,orderedIndex,currentCompareIndex,currentChangeIndex,isStep);
        auxFrame.setData(orderedStart,orderedIndex,currentCompareIndex,currentChangeIndex,isStep);
    }

    /**
     * 数组拷贝方法
     * @param src       源数组
     * @param srcIndex  源数组开始索引
     * @param desc      目标数组
     * @param descIndex 目标数组开始索引
     * @param length    长度
     */
    public void dataCoppy(AlgoFrame src,int srcIndex ,AlgoFrame desc,int descIndex,int length){
        for (int i = 0; i < length; i++) {
            desc.replace(descIndex + i,src.get(srcIndex + i));
        }
    }

    public void shuffle() {
        shuffle(SortData.NUM_HEIGHT);
    }

    public void shuffle(int height) {
        Random rng = new Random();
        for (int i = 0; i < data.size(); i++) {
            int swapWithIndex = rng.nextInt(data.size() - 1);
            data.swap(i,swapWithIndex);
        }
        data.initChange();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        try
        {
            boolean isSlave = auxFrame != null;
            Map<RenderingHints.Key, Object> renderingHints = new HashMap<>();
            renderingHints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.addRenderingHints(renderingHints);
                g2d.setFont(new Font("Monospaced", Font.BOLD, 20));
                g2d.drawString("    Sort algorithm: " + algorithmName, 10, 30);
                g2d.drawString("        Step delay: " + (isSlave ? ((this.isMaster() ? this.delay : auxFrame.delay)) : this.delay) + "ms." + "( ↑ and ↓ )", 10, 55);
                g2d.drawString("     Array Changes: " + (isSlave ? (this.isMaster() ? this.data.getArrayChanges() : auxFrame.data.getArrayChanges())
                        : this.data.getArrayChanges()), 10, 80);
                g2d.drawString("     Array Compare: " + (isSlave ? (this.isMaster() ? this.data.getArrayCompare() : auxFrame.data.getArrayCompare())
                        : this.data.getArrayCompare()), 10, 105);
            // 如果含有双frame
            if (isSlave){
                g2d.drawString("   Slave Step delay: " + (!this.isMaster() ? this.delay : auxFrame.delay) + "ms", getWidth() - 380, 55);
                g2d.drawString("Slave Array Changes: " + (!this.isMaster() ? this.data.getArrayChanges() : auxFrame.data.getArrayChanges()), getWidth() - 380, 80);
                g2d.drawString("Slave Array Compare: " + (!this.isMaster() ? this.data.getArrayCompare() : auxFrame.data.getArrayCompare()), getWidth() - 380, 105);
            }
            // 图像信息
            drawBars(g2d);
        } finally {
            g2d.dispose();
        }
    }

    private void drawBars(Graphics2D g2d) {
        int bufferedImageWidth = getWidth();
        int bufferedImageHeight = getHeight();

        if(bufferedImageHeight > 0 && bufferedImageWidth > 0) {
            if(bufferedImageWidth < 256) {
                bufferedImageWidth = 256;
            }

            BufferedImage bufferedImage = new BufferedImage(bufferedImageWidth, bufferedImageHeight, BufferedImage.TYPE_INT_ARGB);
            makeBufferedImageTransparent(bufferedImage);
            Graphics2D bufferedGraphics = null;
            try {
                bufferedGraphics = bufferedImage.createGraphics();
                // 具体绘制
                for(int i = 0; i < data.size() ; i ++ ) {
                    // 先画主 后画备
                    drawCanvas(this.isMaster() ? this : auxFrame, bufferedGraphics, i);
                    drawCanvas(this.isMaster() ? auxFrame : this, bufferedGraphics, i);
                }
            }finally {
                if(bufferedGraphics != null)
                {
                    bufferedGraphics.dispose();
                }
            }
            g2d.drawImage(bufferedImage, 0, 0, getWidth(), getHeight(), 0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), null);
        }
    }

    private void drawCanvas(AlgoFrame data, Graphics2D bufferedGraphics, int i) {
        if (data == null) return;
        // 是否含有备
        boolean isSlave = this.auxFrame != null;
        int rise = SortData.NUM_HEIGHT / 2 + 10,barWidth = width;
        if (data.getData().isSorted(i))
            bufferedGraphics.setColor(LineColorEnum.Red.getColor());
        else
            bufferedGraphics.setColor(LineColorEnum.Grey.getColor());

        if(i == data.getData().currentCompareIndex)
            bufferedGraphics.setColor(LineColorEnum.LightBlue.getColor());
        if(i == data.getData().currentChangeIndex)
            bufferedGraphics.setColor(LineColorEnum.Indigo.getColor());
        // 双frame处理
        bufferedGraphics.fillRect(i * barWidth,
                isSlave ?
                        (data.isMaster() ?
                                canvasHeight - rise - (data.get(i) / 2)
                                : canvasHeight - (data.get(i) / 2))
                        : canvasHeight - data.get(i),
                barWidth - 1,
                // 有备高度缩减
                isSlave ? data.get(i) / 2 : data.get(i));
    }

    private void makeBufferedImageTransparent(BufferedImage image)
    {
        Graphics2D bufferedGraphics = null;
        try
        {
            bufferedGraphics = image.createGraphics();

            bufferedGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
            bufferedGraphics.fillRect(0, 0, image.getWidth(), image.getHeight());
            bufferedGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        }
        finally
        {
            if(bufferedGraphics != null)
            {
                bufferedGraphics.dispose();
            }
        }
    }

    @Override
    public Dimension getPreferredSize(){
        return new Dimension(canvasWidth, canvasHeight);
    }

    public void finish() {
        auxFrame = null;
        render(this.data);
        for (int i = 0; i < data.size(); i++) {
            pause(data.getDELAY());
            updateData(i,i);
        }
    }
}