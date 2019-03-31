public class Test {


    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        // 工人
        Environment environment1 = new Environment(new MoveBrickWorker("MoveBrickWorker"));
        environment1.work();

        // 工人
        Environment environment2 = new Environment(new brickmason("brickmason"));
        environment2.work();
    }


}
