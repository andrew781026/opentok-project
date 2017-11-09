package andrew.com.riko.www.webviewproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Test on 2017/11/8.
 */

public class City implements Serializable{

    @SerializedName("id")
    private int id ;
    @SerializedName("region_id")
    private int regionId ;
    @SerializedName("name")
    private String name ;

    public City() {
    }

    public City(int id, int regionId, String name) {
        this.id = id;
        this.regionId = regionId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", regionId=" + regionId +
                ", name='" + name + '\'' +
                '}';
    }
}
