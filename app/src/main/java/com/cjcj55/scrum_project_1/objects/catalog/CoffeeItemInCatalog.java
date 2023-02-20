package com.cjcj55.scrum_project_1.objects.catalog;

import androidx.annotation.NonNull;
import android.graphics.Bitmap;

public class CoffeeItemInCatalog {
    private int id;
    private String name;
    private String description;
    private double price;
    private Bitmap image;

    public CoffeeItemInCatalog(int id, String name, String description, double price, Bitmap image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return this.price;
    }

    public Bitmap getImage() {
        return this.image;
    }

    @NonNull
    @Override
    public String toString() {
        String coffeeStr = getId() + ", " + getName() + ", " + getDescription() + ", $" + getPrice();
        return coffeeStr;
    }
}