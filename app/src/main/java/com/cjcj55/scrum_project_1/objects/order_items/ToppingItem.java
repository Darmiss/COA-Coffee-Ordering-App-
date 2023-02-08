package com.cjcj55.scrum_project_1.objects.order_items;

import androidx.annotation.NonNull;

import com.cjcj55.scrum_project_1.objects.catalog.ToppingItemInCatalog;

public class ToppingItem {
    private int id;
    private String name;
    private String description;
    private double price;

    public ToppingItem(ToppingItemInCatalog toppingItemInCatalog) {
        this.id = toppingItemInCatalog.getId();
        this.name = toppingItemInCatalog.getName();
        this.description = toppingItemInCatalog.getDescription();
        this.price = toppingItemInCatalog.getPrice();
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
