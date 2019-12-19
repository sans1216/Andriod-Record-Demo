package com.example.myapplication.Count;

import cn.bmob.v3.BmobObject;

public class CostBean extends BmobObject {
    private String costType;
    private String allType;
    private double costAmount;
    private int icon;
    private String date;
    public CostBean(String costType,String allType ,double costAmount,String date){
        this.costAmount = costAmount;
        this.costType = costType;
        this.allType = allType;
        this.date = date;
    }
    public double getCostAmount() {
        return costAmount;
    }

    public String getAllType() {
        return allType;
    }

    public String getCostType() {
        return costType;
    }

    public int getIcon() {
        return icon;
    }

    public String getDate() {
        return date;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCostAmount(double costAmount) {
        this.costAmount = costAmount;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public void setAllType(String allType) {
        this.allType = allType;
    }

}
