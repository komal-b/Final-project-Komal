package com.welcomehome.item;

import com.welcomehome.register.Act;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemDao itemDao;

    public ItemService(ItemDao itemDao) {
        this.itemDao = itemDao;
    }

    public List<Location> getItemLocation(int itemID){
        return itemDao.getItemLocation(itemID);
    }

    public List<Location> getOrdersAndLocation(int orderID) {
        return itemDao.getOrdersAndLocation(orderID);
    }

    public void itemDonate(String username, String donorName, List<Item> item) {
        itemDao.itemDonate( username,  donorName, item);
    }

    public int createOrder(String staffUser, String clientUser,  List<Integer> itemIds, HttpSession session){
        return itemDao.createOrder(staffUser, clientUser, itemIds, session);

    }

    public List<Order> getOrdersByUser(String username) throws SQLException {
        return itemDao.getOrdersByUser(username);
    }
}
