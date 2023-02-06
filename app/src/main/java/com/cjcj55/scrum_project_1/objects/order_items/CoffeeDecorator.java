package com.cjcj55.scrum_project_1.objects.order_items;

import java.util.List;

public class CoffeeDecorator {
    public static CoffeeItem addToppings(CoffeeItem coffeeItem, List<ToppingItem> toppingItems) {
        coffeeItem.setToppingItemList(toppingItems);
        return coffeeItem;
    }

    public static CoffeeItem addFlavors(CoffeeItem coffeeItem, List<FlavorItem> flavorItems) {
        coffeeItem.setFlavorItemList(flavorItems);
        return coffeeItem;
    }
}
