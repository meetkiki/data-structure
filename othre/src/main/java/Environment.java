/**
 * 环境角色
 */
public class Environment {

    private Worker worker;


    public Environment(Worker worker) {
        this.worker = worker;
    }

    /**
     * 督促工作
     */
    public void work(){
        if (worker instanceof Optimized){
            ((Optimized) worker).optimized();
        }else{
            worker.work();
        }
    }
}
