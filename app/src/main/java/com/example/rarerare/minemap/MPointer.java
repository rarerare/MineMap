package com.example.rarerare.minemap;

/**
 * Created by rarerare on 12/28/16.
 */

public class MPointer {
    private int id;
    private double x;
    private double y;
    public MPointer(int id,float x,float y){
        this.id=id;
        this.x=x;
        this.y=y;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
