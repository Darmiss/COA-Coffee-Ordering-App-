package com.cjcj55.scrum_project_1.objects;

import com.cjcj55.scrum_project_1.objects.order_items.CoffeeItem;

import java.util.ArrayList;
import java.util.List;

public class UserCart {
    private List<CoffeeItem> coffeeItemList;

    public UserCart() {
        coffeeItemList = new ArrayList<>();
    }

    public void addCoffeeToCart(CoffeeItem coffeeItem) {
        this.coffeeItemList.add(coffeeItem);
    }

    public void removeCoffeeFromCart(CoffeeItem coffeeItem) {
        int index = this.coffeeItemList.indexOf(coffeeItem);
        this.coffeeItemList.remove(index);
    }

    public void clearCart() {
        this.coffeeItemList = new ArrayList<>();
    }

    public CoffeeItem getCoffeeAt(int index) {
        return coffeeItemList.get(index);
    }

    public List<CoffeeItem> getUserCart() {
        return this.coffeeItemList;
    }
}
