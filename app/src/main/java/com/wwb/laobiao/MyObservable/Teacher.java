package com.wwb.laobiao.MyObservable;

import java.util.Observable;

public class Teacher extends Observable {
    public static final String ADRESSOK ="ADRESSOK" ;

    private Teacher() {
    }
    private static Teacher teacher = null;

    public static Teacher getInstance() {
        if (teacher == null) {
            synchronized (Teacher.class) {
                if (teacher == null) {
                    teacher = new Teacher();
                }
            }

        }
        return teacher;
    }

    public void postMessage(String eventtype) {
        setChanged();
        notifyObservers(eventtype);
    }
    public void postModel(TeacherModel eventtype) {
        setChanged();
        notifyObservers(eventtype);
    }
    public static class TeacherModel {
        public String packname;
        public  int dely;
        public String message;
        public int sky;
    }
}
