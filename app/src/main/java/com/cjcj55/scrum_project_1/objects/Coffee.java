package com.cjcj55.scrum_project_1.objects;

import androidx.annotation.NonNull;

import java.util.List;

public class Coffee {
    private int id;
    private String name;
    private String description;
    private double price;
    private List<Topping> toppings;
    private List<Flavor> flavors;

    public Coffee(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public List<Topping> getToppings() {
        return toppings;
    }

    public void setToppings(List<Topping> toppings) {
        this.toppings = toppings;
    }

    public List<Flavor> getFlavors() {
        return flavors;
    }

    public void setFlavors(List<Flavor> flavors) {
        this.flavors = flavors;
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
