package com.test.duan.myuilibrarytest.guanchazhe;

/**
 * Created by Duanyy on 2016/7/1.
 */
public class ConcreteObserver implements Observer {

    private String weatherState;
    private String name;

    @Override
    public void update(Subject subject) {
        this.weatherState = ((ConcreteSubject)subject).getWeatherState();
        System.out.print(name+weatherState);
    }
}
