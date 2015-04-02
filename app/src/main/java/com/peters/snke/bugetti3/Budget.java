package com.peters.snke.bugetti3;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Sönke on 31.03.2015.
 */
@DatabaseTable (tableName = "Budget")
public class Budget{
    @DatabaseField (generatedId = true)
    int id;
    @DatabaseField(defaultValue = "default")
    String name;
    @DatabaseField(columnName = "amount",defaultValue = "0.0f")
    float amount;

    public Budget() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(": \t").append(amount);
        return sb.toString();

    }
}
