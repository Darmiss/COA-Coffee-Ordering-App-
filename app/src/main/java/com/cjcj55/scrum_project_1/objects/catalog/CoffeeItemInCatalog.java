package com.cjcj55.scrum_project_1.objects.catalog;

import androidx.annotation.NonNull;

public class CoffeeItemInCatalog {
    private int id;
    private String name;
    private String description;
    private double price;

    public CoffeeItemInCatalog(int id, String name, String description, double price) {
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
        return this.price;
    }

    @NonNull
    @Override
    public String toString() {
        String coffeeStr = getId() + ", " + getName() + ", " + getDescription() + ", $" + getPrice();
//        if (!this.toppings.isEmpty()) {
//            for (int i = 0; i < toppings.size(); i++)
//            {
//                coffeeStr += "\t" + toppings.get(i).toString();
//            }
//        }
//        if (!this.flavors.isEmpty()) {
//            for (int i = 0; i < flavors.size(); i++)
//            {
//                coffeeStr += "\t" + flavors.get(i).toString();
//            }
//        }
        return coffeeStr;
    }
}
