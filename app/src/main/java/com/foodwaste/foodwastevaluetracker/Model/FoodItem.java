package com.foodwaste.foodwastevaluetracker.Model;

public class FoodItem {
    String name;
    int price;
    int usage;
    String date;
    int month;
    String category;
    float value = (float) this.usage * this.price / 100;
    float valueloss = (float) this.price - this.value;
    int week;


    public FoodItem(String name, int price, int usage, String date, int month, String category, float value, float valueloss, int week) {
        this.name = name;
        this.price = price;
        this.usage = usage;
        this.date = date;
        this.month = month;
        this.category = category;
        this.value = value;
        this.valueloss = valueloss;
        this.week = week;
    }

    public FoodItem() {
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

    public int getUsage() {
        return usage;
    }

    public String getDate() {
        return date;
    }

    public int getMonth() {
        return month;
    }
    public String getCategory() {
        return category;
    }
    public float getValue() {
        return value;
    }
    public float getValueloss() {
        return valueloss;
    }
    public int getWeek() {
        return week;
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
