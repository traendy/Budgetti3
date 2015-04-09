package com.peters.snke.bugetti3;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Sönke on 09.04.2015.
 */
@DatabaseTable (tableName = "buylistitems")
public class BuyListItems  {

    @DatabaseField(generatedId = true)
    int id;
    @DatabaseField
    String listName;
    @DatabaseField
    String itemName;
    @DatabaseField
    Float betrag;

    public BuyListItems() {
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Float getBetrag() {
        return betrag;
    }

    public void setBetrag(Float betrag) {
        this.betrag = betrag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
