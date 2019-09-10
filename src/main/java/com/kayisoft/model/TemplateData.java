package com.kayisoft.model;

/**
 * @Author tianqiu.lan
 * @Date 2019/9/9
 */

public class TemplateData {
    private String value;
    private String color;
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public TemplateData(String value, String color) {
        this.value = value;
        this.color = color;
    }

    public TemplateData() {
    }
}
