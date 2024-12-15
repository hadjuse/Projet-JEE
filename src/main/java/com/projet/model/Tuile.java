package com.projet.model;

public class Tuile {

    private int x;
    private int y;
    private String type;

    public Tuile(int x, int y, String type){
        this.x=x;
        this.y=y;
        this.type=type;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public String getType() {
        return type;
    }

    public void setX(int x){
        this.x=x;
    }

    public void setY(int y){
        this.y=y;
    }

    public void setType(String type){
        this.type=type;
    }

    @Override
    public String toString(){
        return "Tuile{"+"x="+ x+", y="+ y+", type="+ type+'\''+'}';
    }

}
