package com.test.duan.myuilibrarytest.guanchazhe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Duanyy on 2016/7/1.
 */
public class Subject {

    private List<Observer> observers = new ArrayList<>();

    public void attach(Observer o){
        observers.add(o);
    }

    public void detach(Observer observer){
        observers.remove(observer);
    }

    protected void  notifyObservers(){
        for (Observer o : observers){
            o.update(this);
        }
    }


}
