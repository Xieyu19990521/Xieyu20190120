package com.example.yu.xieyu20190120.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class DaoBean {
    String images;
    @Id(autoincrement = false)
    long pid;
    double price;
    String title;
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public long getPid() {
        return this.pid;
    }
    public void setPid(long pid) {
        this.pid = pid;
    }
    public String getImages() {
        return this.images;
    }
    public void setImages(String images) {
        this.images = images;
    }
    @Generated(hash = 2072632779)
    public DaoBean(String images, long pid, double price, String title) {
        this.images = images;
        this.pid = pid;
        this.price = price;
        this.title = title;
    }
    @Generated(hash = 405743142)
    public DaoBean() {
    }

}
