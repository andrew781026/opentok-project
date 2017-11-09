package andrew.com.riko.www.doctorapplication.properties;

/**
 * Created by Test on 2017/11/9.
 */

public enum Status {
    ACCEPT(2,"執行中"),DENY(4,"失敗"),SUCCESS(3,"完成"),FAIL(4,"失敗") ;

    private int id;
    private String name;

    private Status(int id, String msg) {
        this.id = id;
        this.name = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
