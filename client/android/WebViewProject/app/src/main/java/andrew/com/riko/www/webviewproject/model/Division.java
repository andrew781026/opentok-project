package andrew.com.riko.www.webviewproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Test on 2017/11/8.
 */

public class Division implements Serializable{

    @SerializedName("id")
    private int id ;
    @SerializedName("no")
    private String no ;
    @SerializedName("name")
    private String name ;

    public Division() {
    }

    public Division(int id, String no, String name) {
        this.id = id;
        this.no = no;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Division{" +
                "id=" + id +
                ", no='" + no + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
