package com.wwb.laobiao.MyObservable;

import android.graphics.Bitmap;

import java.util.Observable;

public class Rinker extends Observable {
    private Rinker() {
    }
    private static Rinker teacher = null;

    public static Rinker getInstance() {
        if (teacher == null) {
            synchronized (Rinker.class) {
                if (teacher == null) {
                    teacher = new Rinker();
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

    public void postBitmap(Bitmap bitmap) {
        setChanged();
        notifyObservers(bitmap);
    }

    public static class TeacherModel {
        public String packname;
        public  int dely;
        public String message;
        public int sky;
    }
}
