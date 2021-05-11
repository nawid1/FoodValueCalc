package com.foodwaste.foodwastevaluetracker.Model;

public class FoodItem {
    String name;
    int price;
    int usage;
    String date;
    String category;
    int value;




    public FoodItem() {
    }

    public FoodItem(String name, int price, int usage, String category) {
        this.name = name;
        this.price = price;
        this.usage = usage;

    }

    public int value(int price, int usage){
        this.value = price*usage;
        return value;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getUsage() {
        return usage;
    }

    public void setUsage(int usage) {
        this.usage = usage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


}




   /* public Map<String, Object> toMap(){

            HashMap<String, Object> foodItemMap = new HashMap<>();
            foodItemMap.put("Item", name);
            foodItemMap.put("Price", price);
            foodItemMap.put("Use", usage);
            foodItemMap.put("Time", localDate);

            return foodItemMap;
        }*/
