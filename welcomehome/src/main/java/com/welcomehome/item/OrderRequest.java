package com.welcomehome.item;

import java.util.List;

public class OrderRequest {

    private String staffUser;
    private String client;
    private List<Integer> itemIds;

    public String getStaffUser() {
        return staffUser;
    }

    public void setStaffUser(String staffUser) {
        this.staffUser = staffUser;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "staffUser='" + staffUser + '\'' +
                ", client='" + client + '\'' +
                ", itemIds=" + itemIds +
                '}';
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public List<Integer> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Integer> itemIds) {
        this.itemIds = itemIds;
    }
}
