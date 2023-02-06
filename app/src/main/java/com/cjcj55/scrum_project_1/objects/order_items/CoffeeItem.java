package com.cjcj55.scrum_project_1.objects.order_items;

import java.util.List;

public class CoffeeItem {
    private int id;
    private String name;
    private String description;
    private double price;

    private List<ToppingItem> toppingItemList;
    private List<FlavorItem> flavorItemList;

    public CoffeeItem(int id, String name, String description, double price) {
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
}
