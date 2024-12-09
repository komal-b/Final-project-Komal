package com.welcomehome.item;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.stereotype.Repository;

@Repository
public class ItemIn {

    @Column
    private int itemID;
    @Column
    private  int orderID;
    @Column
    private boolean found;

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }
}
