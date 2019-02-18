package com.clfsjkj.govcar.bean;

public class ChooseCarBean {
    private int carType;
    private String carName;
    private int needNum;

    @Override
    public String toString() {
        return "ChooseCarBean{" +
                "carType=" + carType +
                ", carName='" + carName + '\'' +
                ", needNum=" + needNum +
                '}';
    }

    public int getNeedNum() {
        return needNum;
    }

    public void setNeedNum(int needNum) {
        this.needNum = needNum;
    }

    public int getCarType() {
        return carType;
    }

    public void setCarType(int carType) {
        this.carType = carType;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }
}
