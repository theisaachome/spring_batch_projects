package com.highway.batch.customer.model;

public class CustomerUpdate {
    private final long customerId;

    public CustomerUpdate(long customerId) {
        this.customerId = customerId;
    }
    public long getCustomerId() {return this.customerId;}

    @Override
    public String toString() {
        return "CustomerUpdate{" +
                "customerId=" + customerId +
                '}';
    }
}
