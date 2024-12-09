package com.welcomehome.item;


import com.welcomehome.register.Act;
import com.welcomehome.register.User;
import com.welcomehome.register.UserConrtoller;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/welcomehome")
public class ItemController {

    private Logger loggers = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    private  ItemService itemService;

    @GetMapping("/itemLocation")
    public List<Location> getItemLocation(@RequestParam String itemID){
        return itemService.getItemLocation(Integer.parseInt(itemID));
    }

    @GetMapping("/itemOrder")
    public List<Location> getOrdersAndLocation(@RequestParam String orderID){
        return itemService.getOrdersAndLocation(Integer.parseInt(orderID));
    }

    @PostMapping("/itemDonated")
    public ResponseEntity<String> itemDonate(@RequestBody DonationRequest donationRequest) {
        try {


            // Perform donation
            itemService.itemDonate(
                    donationRequest.getStaffAct(),
                    donationRequest.getDonorUsername(),
                    donationRequest.getItems()
            );

            return ResponseEntity.ok("Donation successfully recorded.");
        } catch (Exception e) {
            loggers.error("Donation error: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/createOrder")
    public ResponseEntity<Map<String, String>> createOrder(@RequestBody OrderRequest orderRequest, HttpSession session){
        loggers.info(orderRequest.toString());
        int orderId=itemService.createOrder(orderRequest.getStaffUser(), orderRequest.getClient(), orderRequest.getItemIds(), session);
        String id = Integer.toString(orderId);
        return ResponseEntity.ok(Collections.singletonMap("orderID", id));

    }

    @GetMapping("/userOrders")
    public List<Order> getOrdersByUser(@RequestParam String username) throws SQLException {
        return itemService.getOrdersByUser(username);
    }




}
