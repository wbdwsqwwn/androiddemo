package com.demo.wanbd.androiddemo.model;

/**
 * Created by wanbd on 16/5/12.
 */
public class VersionModel {
    String Name;
    String code;
    String desc;
    String url;

    public String getUrl() {
        return url;
    }

    public String getName() {
        return Name;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
