package com.peters.snke.bugetti3;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Sönke on 24.03.2015.
 */
public class DateManager {
    Context applicationContext = MainActivity.getContextOfApplication();
   private Date date;



    private String oldDate = "";
    public DateManager(Date date) {
        this.date = date;
    }

    public DateManager() {
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.GERMAN);
        Date date = new Date();

        return dateFormat.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOldDate() {
        SharedPreferences preferences =PreferenceManager.getDefaultSharedPreferences(applicationContext);
        oldDate = preferences.getString("oldDate", oldDate);
        if(oldDate.equals(null))oldDate=getDate();
        return oldDate;
    }

    public void setOldDate(String oldDate) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("oldDate", oldDate.trim());
        this.oldDate = oldDate;
        editor.commit();
    }
    public void deleteOldDate(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("oldDate", "");
        this.oldDate = "";
        editor.commit();

    }
}
