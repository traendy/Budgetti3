package com.peters.snke.bugetti3;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@DatabaseTable(tableName = "charges")
public class Charges {


    @DatabaseField(generatedId = true)
    int id;
    @DatabaseField(defaultValue="Charge")
    String charge_name;
    @DatabaseField
    float charge_amount;
    @DatabaseField
    String date;

    public String getDate() {
        return ("   "+date);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Charges(){

    }

    public Charges(String name, float amount, String date){
        this.charge_name=name;
        this.charge_amount=amount;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCharge_name() {
        return charge_name;
    }

    public void setCharge_name(String charge_name) {
        this.charge_name = charge_name;
    }

    public float getCharge_amount() {
        return charge_amount;
    }

    public void setCharge_amount(float charge_amount) {
        this.charge_amount = charge_amount;
    }

    @Override
    public String toString(){
        StringBuilder sb= new StringBuilder();
        sb.append("id=").append(id);
        sb.append(": ").append(date);
        sb.append(", ").append(charge_name);
        sb.append(": ").append(charge_amount);
        return sb.toString();
    }
}
