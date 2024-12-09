package com.welcomehome.item;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.stereotype.Repository;

@Repository
public class Location {

    @Column
    private int roomNum;
    @Column
    private int shelfNum;
    @Column
    private String shelf;
    @Column
    private String shelfDescription;
    @Column
    private String pNotes;

    private Piece piece;

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public int getShelfNum() {
        return shelfNum;
    }

    public void setShelfNum(int shelfNum) {
        this.shelfNum = shelfNum;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getShelfDescription() {
        return shelfDescription;
    }

    public void setShelfDescription(String shelfDescription) {
        this.shelfDescription = shelfDescription;
    }

    public String getpNotes() {
        return pNotes;
    }

    public void setpNotes(String pNotes) {
        this.pNotes = pNotes;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    @Override
    public String toString() {
        return "Location{" +
                "roomNum=" + roomNum +
                ", shelfNum=" + shelfNum +
                ", shelf='" + shelf + '\'' +
                ", shelfDescription='" + shelfDescription + '\'' +
                ", pNotes='" + pNotes + '\'' +
                ", piece=" + piece +
                '}';
    }
}
