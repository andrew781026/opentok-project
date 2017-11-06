package andrew.com.riko.www.doctorapplication.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Test on 2017/11/4.
 */

public class Task implements Serializable {

    private String title;
    private int age;
    private String name;
    private String description;
    private int imageResourceId;

    public Task() {
    }

    public Task(String title, String name, int age, String description, int imageResourceId) {
        this.title = title;
        this.age = age;
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
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
                "title='" + title + '\'' +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageResourceId=" + imageResourceId +
                '}';
    }
}
