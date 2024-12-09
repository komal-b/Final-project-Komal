package com.welcomehome.item;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.stereotype.Repository;

@Repository
public class Piece {

    @Column
    private int itemID;
    @Column
    private int pieceNum;
    @Column
    private String pDescription;
    @Column
    private int length;
    @Column
    private int width;
    @Column
    private int height;
    @Column
    private int roomNum;
    @Column
    private int shelfNum;
    @Column
    private String pNotes;


    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getPieceNum() {
        return pieceNum;
    }

    public void setPieceNum(int pieceNum) {
        this.pieceNum = pieceNum;
    }

    public String getpDescription() {
        return this.pDescription;
    }

    public void setpDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getRoomNum() {
        return this.roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public int getShelfNum() {
        return this.shelfNum;
    }

    public void setShelfNum(int shelfNum) {
        this.shelfNum = shelfNum;
    }

    public String getpNotes() {
        return pNotes;
    }

    public void setpNotes(String pNotes) {
        this.pNotes = pNotes;
    }

    @Override
    public String toString() {
        return "Piece{" +
                "itemID=" + itemID +
                ", pieceNum=" + pieceNum +
                ", pDescription='" + pDescription + '\'' +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", roomNum=" + roomNum +
                ", shelfNum=" + shelfNum +
                ", pNotes='" + pNotes + '\'' +
                '}';
    }
}
