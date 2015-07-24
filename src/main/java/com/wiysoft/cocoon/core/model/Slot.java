package com.wiysoft.cocoon.core.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by weiliyang on 7/17/15.
 */
@Entity
@Table(name = "t_slot")
public final class Slot implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @Column(nullable = false)
    private String code;
    @Column(nullable = false, name = "generated")
    private Date date;
    @Column(nullable = false)
    private float price;
    @Column(nullable = false, name = "priceChange")
    private float change;
    @Column(nullable = false)
    private float changePercentage;
    @Column(nullable = false)
    private long volume;
    @Column(nullable = false)
    private long value;
    @Column(nullable = false)
    private String type;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getChange() {
        return change;
    }

    public void setChange(float change) {
        this.change = change;
    }

    public float getChangePercentage() {
        return changePercentage;
    }

    public void setChangePercentage(float changePercentage) {
        this.changePercentage = changePercentage;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Slot() {

    }

    @Override
    public String toString() {
        StringBuffer buff = new StringBuffer();

        buff.append("[code: "+ code +"],");
        buff.append("[name: "+ name +"],");
        buff.append("[date: "+ date +"],");
        buff.append("[price: "+ price +"],");
        buff.append("[change: "+ change +"],");
        buff.append("[changePercentage: "+ changePercentage +"],");
        buff.append("[volume: "+ volume +"],");
        buff.append("[value: "+ value +"],");
        buff.append("[type: "+ type +"]");

        return buff.toString();
    }
}
