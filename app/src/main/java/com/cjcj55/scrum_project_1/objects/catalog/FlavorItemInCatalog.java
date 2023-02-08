package com.cjcj55.scrum_project_1.objects.catalog;

import androidx.annotation.NonNull;

public class FlavorItemInCatalog {
    private int id;
    private String name;
    private String description;
    private double price;

    public FlavorItemInCatalog(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
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
        return price;
    }

    @NonNull
    @Override
    public String toString() {
        return getId() + ", " + getName() + ", " + getDescription() + ", $" + getPrice();
    }
}
