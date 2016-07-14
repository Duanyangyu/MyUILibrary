package com.test.duan.myuilibrarytest.guanchazhe;

/**
 * Created by Duanyy on 2016/7/1.
 */
public class ConcreteSubject extends Subject {

    private String weatherState;


    public String getWeatherState() {
        return weatherState;
    }

    public void setWeatherState(String weatherState) {
        this.weatherState = weatherState;
        notifyObservers();
    }
}
