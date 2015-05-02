package com.iiitd.hostel.backend;

/**
 * Created by vince on 4/26/2015.
 */

import com.googlecode.objectify.annotation.Entity;


        import com.googlecode.objectify.annotation.Id;

/**
 * Created by irani_r on 8/25/2014.
 */
@Entity
public class Quote {
    @Id
    Long id;
    String who;
    String what;
    Integer itemId;
    Integer quantity;
    Long timeStamp;

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Quote() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWho() {
        return who;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setWho(String who) {

        this.who = who;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }
}