package com.demo.wanbd.androiddemo.model;

/**
 * Created by wanbd on 16/6/1.
 */
public class BlackListModel {
    String phone;
    int model;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "BlackListModel{" +
                "phone='" + phone + '\'' +
                ", model=" + model +
                '}';
    }
}
