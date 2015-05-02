package com.iiitd.hostel.Database;

/**
 * Created by ruchika on 4/26/15.
 */
public class ListDetails {

    private int id;
    private int Item_Id;
    private String Item_name;
    private int Quantity;
    private long timestamp;

    public int getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItem_Id() {
        return Item_Id;
    }

    public void setItem_Id(int item_Id) {
        Item_Id = item_Id;
    }

    public String getItem_name() {
        return Item_name;
    }

    public void setItem_name(String item_name) {
        Item_name = item_name;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    @Override
    public String toString() {
        return "Item_name='" + Item_name + '\'' +
                ", Quantity=" + Quantity;
    }
}
