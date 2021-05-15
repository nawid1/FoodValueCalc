package com.foodwaste.foodwastevaluetracker.Model;

public class FoodItem {
    String name;
    int price;
    int usage;
    String date;
    int month;
    String category;
    float value = (float) this.usage*this.price/100;
    float valueloss = (float)this.price-this.value;

    public FoodItem(String name, int price, int usage, String date, int month, String category, float value,float valueloss) {
        this.name = name;
        this.price = price;
        this.usage = usage;
        this.date = date;
        this.month = month;
        this.category = category;
        this.value = value;
        this.valueloss=valueloss;
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

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getValueloss() {
        return valueloss;
    }

    public void setValueloss(float valueloss) {
        this.valueloss = valueloss;
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
