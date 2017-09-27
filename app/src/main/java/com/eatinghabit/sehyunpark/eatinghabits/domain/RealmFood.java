package com.eatinghabit.sehyunpark.eatinghabits.domain;

import io.realm.RealmObject;

/**
 * Created by jj on 2015-10-27.
 */
public class RealmFood extends RealmObject {

    private int level;
    private String name;
    private String subname;
    private String category;
    private String category2;
    private String time;
    private String kal;
    private String image;
    private String description;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubname() {
        return subname;
    }

    public void setSubname(String subname) {
        this.subname = subname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getKal() {
        return kal;
    }

    public void setKal(String kal) {
        this.kal = kal;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
