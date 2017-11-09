package andrew.com.riko.www.webviewproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Test on 2017/11/8.
 */

public class Hospital implements Serializable{

    @SerializedName("id")
    private int id ;
    @SerializedName("city_id")
    private String cityId ;
    @SerializedName("name")
    private String name ;
    @SerializedName("address")
    private String address ;
    @SerializedName("tel")
    private String telephoneNumber ;
    @SerializedName("website")
    private String website ;
    @SerializedName("level")
    private String level ;

    public Hospital() {
    }

    public Hospital(int id, String cityId, String name, String address, String telephoneNumber, String website, String level) {
        this.id = id;
        this.cityId = cityId;
        this.name = name;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
        this.website = website;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "id=" + id +
                ", cityId='" + cityId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", website='" + website + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
