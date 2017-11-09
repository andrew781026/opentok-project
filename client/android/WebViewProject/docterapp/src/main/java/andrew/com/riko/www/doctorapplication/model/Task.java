package andrew.com.riko.www.doctorapplication.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by Test on 2017/11/4.
 */
@DatabaseTable(tableName = "registration")
public class Task implements Serializable {

    @DatabaseField(columnName="id",id = true)
    private int missionId ;
    @DatabaseField(columnName="title")
    private String title;
    @DatabaseField(columnName="age")
    private int age;
    @DatabaseField(columnName="name")
    private String name;
    @DatabaseField(columnName="description")
    private String description;
    @DatabaseField(persisted=false)
    private int imageResourceId;

    public Task() {
    }

    public Task(int missionId, String title, int age, String name, String description) {
        this.missionId = missionId;
        this.title = title;
        this.age = age;
        this.name = name;
        this.description = description;
    }

    public Task(String title, String name, int age, String description, int imageResourceId) {
        this.title = title;
        this.age = age;
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
    }

    public int getMissionId() {
        return missionId;
    }

    public void setMissionId(int missionId) {
        this.missionId = missionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public void setImageResourceId(int imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "missionId=" + missionId +
                ", title='" + title + '\'' +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageResourceId=" + imageResourceId +
                '}';
    }
}
