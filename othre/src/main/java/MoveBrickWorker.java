/**
 * 砌砖的
 */
public class MoveBrickWorker implements Worker ,Optimized{

    public MoveBrickWorker(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void work() {
        System.out.println(name+"is worker");
    }

    public void optimized() {
        System.out.println("optimized " + name+"is worker");
    }
}
