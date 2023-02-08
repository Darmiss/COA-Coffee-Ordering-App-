package com.cjcj55.scrum_project_1.objects;


import com.cjcj55.scrum_project_1.objects.order_items.CoffeeItem;

import java.util.List;

public class Transaction {
    //Not used but used as a reference
    private List<CoffeeItem> coffeeItems;
    double total;
    public Transaction(List<CoffeeItem> coffees){
        this.coffeeItems = coffees;
        for(int i = 0; i < coffees.size(); i++){
            total = total + coffees.get(i).getPrice();
            for(int j = 0; j < coffees.get(i).getFlavorItemList().size(); j++){
                total = total +  coffees.get(i).getFlavorItemList().get(j).getPrice();
            }
            for(int y = 0; y < coffees.get(i).getToppingItemList().size(); y++){
                total = total + coffees.get(i).getToppingItemList().get(y).getPrice();
            }
        }
    }
    public void setCoffeeItems(List<CoffeeItem> coffeeItems){
        this.coffeeItems = coffeeItems;
    }
    public List<CoffeeItem> getCoffeeItems(){
        return coffeeItems;
    }
    public double getTotal(){
        return total;
    }

}
