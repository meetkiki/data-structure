/**
 * 搬砖的
 */
public class brickmason implements Worker {

    public brickmason(String name) {
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
}
