package com.cjcj55.scrum_project_1.objects;

import java.util.List;

public class CoffeeDecorator {
    public static Coffee addToppings(Coffee coffee, List<Topping> toppings) {
        coffee.setToppings(toppings);
        return coffee;
    }

    public static Coffee addFlavors(Coffee coffee, List<Flavor> flavors) {
        coffee.setFlavors(flavors);
        return coffee;
    }
}
