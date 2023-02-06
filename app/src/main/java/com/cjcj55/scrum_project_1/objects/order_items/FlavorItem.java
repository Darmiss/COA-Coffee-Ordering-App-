package com.cjcj55.scrum_project_1.objects.order_items;

public class FlavorItem {
    private int id;
    private String name;
    private String description;
    private double price;

    public FlavorItem(int id, String name, String description, double price) {
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
}
