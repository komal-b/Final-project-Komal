package com.welcomehome.item;

import org.springframework.data.relational.core.mapping.Column;

import java.util.List;

public class Item {

    @Column
    private int itemID;
    @Column
    private String iDescription;
    @Column
    private String color;
    @Column
    private boolean isNew;
    @Column
    private boolean hasPieces;
    @Column
    private String material;
    @Column
    private String mainCategory;
    @Column
    private String subCategory;
    private List<Location> location;
    private  List<Piece> piece;

    public boolean isHasPieces() {
        return hasPieces;
    }

    public List<Location> getLocation() {
        return location;
    }

    public void setLocation(List<Location> location) {
        this.location = location;
    }

    public List<Piece> getPiece() {
        return piece;
    }

    public void setPiece(List<Piece> piece) {
        this.piece = piece;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getiDescription() {
        return iDescription;
    }

    public void setiDescription(String iDescription) {
        this.iDescription = iDescription;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean getHasPieces() {
        return hasPieces;
    }

    public void setHasPieces(boolean hasPieces) {
        this.hasPieces = hasPieces;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
}
