package com.example.pc.bottomlansu;

public class Fruit {
    private String name;
    private int imageId;
    private int buy_ID;
    Fruit(){}
    @Override
    public String toString() {
        return "Fruit{" +
                "name='" + name + '\'' +
                ", imageId=" + imageId +
                ", buy_ID=" + buy_ID +
                '}';
    }

    public Fruit(String name, int imageId, int buy_ID) {
        this.name = name;
        this.imageId = imageId;
        this.buy_ID = buy_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getBuy_ID() {
        return buy_ID;
    }

    public void setBuy_ID(int buy_ID) {
        this.buy_ID = buy_ID;
    }
}
