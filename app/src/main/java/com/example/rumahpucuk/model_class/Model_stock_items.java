package com.example.rumahpucuk.model_class;

public class Model_stock_items {
    private String nameItems;
    private String price,amount;

//    public Model_stock_items(){}

    public Model_stock_items(String nameItems,String amount, String price){
        this.nameItems = nameItems;
        this.price = price;
        this.amount = amount;
    }

    public String getNameItems() {
        return nameItems;
    }

    public String getAmount() {
        return amount;
    }

    public String getPrice() {
        return price;
    }

    public void setNameItems(String nameItems) {
        this.nameItems = nameItems;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
