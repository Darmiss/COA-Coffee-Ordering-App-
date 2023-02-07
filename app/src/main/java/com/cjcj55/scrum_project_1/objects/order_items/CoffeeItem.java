package com.cjcj55.scrum_project_1.objects.order_items;

import androidx.annotation.NonNull;

import com.cjcj55.scrum_project_1.objects.catalog.CoffeeItemInCatalog;

import java.util.ArrayList;
import java.util.List;

public class CoffeeItem {
    private int id;
    private String name;
    private String description;
    private double price;

    private int amount;

    private List<ToppingItem> toppingItemList;
    private List<FlavorItem> flavorItemList;

    public CoffeeItem(CoffeeItemInCatalog coffeeItemInCatalog) {
        this.id = coffeeItemInCatalog.getId();
        this.name = coffeeItemInCatalog.getName();
        this.description = coffeeItemInCatalog.getDescription();
        this.price = coffeeItemInCatalog.getPrice();
        this.amount = 1;
        this.toppingItemList = new ArrayList<>();
        this.flavorItemList = new ArrayList<>();
    }

    public CoffeeItem(CoffeeItemInCatalog coffeeItemInCatalog, int amount) {
        this.id = coffeeItemInCatalog.getId();
        this.name = coffeeItemInCatalog.getName();
        this.description = coffeeItemInCatalog.getDescription();
        this.price = coffeeItemInCatalog.getPrice();
        this.amount = amount;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public List<ToppingItem> getToppingItemList() {
        return toppingItemList;
    }

    public void setToppingItemList(List<ToppingItem> toppingItemList) {
        this.toppingItemList = toppingItemList;
    }

    public List<FlavorItem> getFlavorItemList() {
        return flavorItemList;
    }

    public void setFlavorItemList(List<FlavorItem> flavorItemList) {
        this.flavorItemList = flavorItemList;
    }

    @NonNull
    @Override
    public String toString() {
        return this.name + ", " + this.description + ", " + this.price + ", " + this.amount;
    }
}
