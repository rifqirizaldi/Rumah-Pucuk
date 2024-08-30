package com.example.rumahpucuk.model_class;

public class Model_history {
    public String name_order, delivery_status, payment_amount, payment_status, transaction_type;

    public Model_history(String name_order, String delivery_status, String payment_amount, String payment_status, String transaction_type) {
        this.name_order = name_order;
        this.delivery_status = delivery_status;
        this.payment_amount = payment_amount;
        this.payment_status = payment_status;
        this.transaction_type = transaction_type;
    }

    public String getName_order() {
        return name_order;
    }

    public void setName_order(String name_order) {
        this.name_order = name_order;
    }

    public String getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(String payment_amount) {
        this.payment_amount = payment_amount;
    }

    public String getDelivery_status() {
        return delivery_status;
    }

    public void setDelivery_status(String delivery_status) {
        this.delivery_status = delivery_status;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getTransaction_type() {
        return transaction_type;
    }

    public void setTransaction_type(String transaction_type) {
        this.transaction_type = transaction_type;
    }
}
