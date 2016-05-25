package com.demo.wanbd.androiddemo.model;

/**
 * Created by wanbd on 16/5/24.
 */
public class ContactModel {
    String name;
    String phone;

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ContactModel{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
