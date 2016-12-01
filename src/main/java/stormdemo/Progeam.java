package stormdemo;

/**
 * Created by yance on 2016/8/25.
 */
public class Progeam {

    private String name;

    private  int index;

    public Progeam(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String toString() {
        return "Progeam{" +
                "name='" + name + '\'' +
                ", index=" + index +
                '}';
    }
}
