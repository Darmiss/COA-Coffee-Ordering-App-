package com.cjcj55.scrum_project_1.objects;


import java.util.List;

public class Transaction {
    private List<Coffee> coffees;
    double total;
    public Transaction(List<Coffee> coffees){
        this.coffees = coffees;
        for(int i = 0; i < coffees.size(); i++){
            total = total + coffees.get(i).getPrice();
            for(int j = 0; j < coffees.get(i).getFlavors().size(); j++){
                total = total +  coffees.get(i).getFlavors().get(j).getPrice();
            }
            for(int y = 0; y < coffees.get(i).getToppings().size(); y++){
                total = total + coffees.get(i).getToppings().get(y).getPrice();
            }
        }
    }
    public void setCoffees(List<Coffee> coffees){
        this.coffees = coffees;
    }
    public List<Coffee> getCoffees(){
        return coffees;
    }
    public double getTotal(){
        return total;
    }

}
