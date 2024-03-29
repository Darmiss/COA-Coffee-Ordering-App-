package com.cjcj55.scrum_project_1.objects;

import com.cjcj55.scrum_project_1.objects.catalog.order_items.CoffeeItem;

import java.util.ArrayList;
import java.util.List;

public class UserCart {
    private List<CoffeeItem> coffeeItemList;
    String timeOrdered;
    String pickupTime;
    int userId;
    int transactionId;
    double price;
    boolean isFulfilled;
    boolean cancelledByCustomer;

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

    public boolean isEmpty() {
        return coffeeItemList.isEmpty();
    }

    public String getTimeOrdered() {
        return timeOrdered;
    }

    public void setTimeOrdered(String timeOrdered) {
        this.timeOrdered = timeOrdered;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public void setFulfilled(int fulfilled) {
        if (fulfilled == 0) {
            this.isFulfilled = false;
        } else {
            this.isFulfilled = true;
        }
    }

    public boolean getFulfilled() {
        return this.isFulfilled;
    }

    public void setCancelledByCustomer(int cancelled) {
        if (cancelled == 0) {
            this.cancelledByCustomer = false;
        } else {
            this.cancelledByCustomer = true;
        }
    }

    public boolean getCancelledByCustomer() {
        return this.cancelledByCustomer;
    }
}
