package andrew.com.riko.www.doctorapplication.properties;

/**
 * Created by Test on 2017/11/9.
 */

public enum Mission {
    ASK(1,"諮詢"),APPOINTMENT(2,"掛號"),VISIT(3,"出診"), EMERGENCY(4,"急診") ;

    private int id;
    private String type;

    private Mission(int id, String type) {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Mission{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
