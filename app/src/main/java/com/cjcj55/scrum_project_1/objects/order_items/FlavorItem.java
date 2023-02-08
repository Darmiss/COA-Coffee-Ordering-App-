package com.cjcj55.scrum_project_1.objects.order_items;

import androidx.annotation.NonNull;

import com.cjcj55.scrum_project_1.objects.catalog.FlavorItemInCatalog;

public class FlavorItem {
    private int id;
    private String name;
    private String description;
    private double price;

    public FlavorItem(FlavorItemInCatalog flavorItemInCatalog) {
        this.id = flavorItemInCatalog.getId();
        this.name = flavorItemInCatalog.getName();
        this.description = flavorItemInCatalog.getDescription();
        this.price = flavorItemInCatalog.getPrice();
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
        return this.name + ", " + this.description + ", " + this.price;
    }
}
