package com.welcomehome.item;

import com.welcomehome.register.Act;

import java.util.List;

public class DonationRequest {

        private String staffAct;
        private String donorUsername;
        private List<Item> items;


        public String getStaffAct() {
            return staffAct;
        }

        public void setStaffAct(String staffAct) {
            this.staffAct = staffAct;
        }

        public String getDonorUsername() {

            return donorUsername;
        }

        public void setDonorUsername(String donorUsername) {
            this.donorUsername = donorUsername;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItem(List<Item> item) {
            this.items = item;
        }

//        public List<Piece> getPieces() {
//            return pieces;
//        }
//
//        public void setPieces(List<Piece> pieces) {
//            this.pieces = pieces;
//        }
//
//        public List<Location> getLocations() {
//            return locations;
//        }
//
//        public void setLocations(List<Location> locations) {
//            this.locations = locations;
//        }
// Getters and Setters


}
