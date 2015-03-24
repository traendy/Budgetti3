package com.peters.snke.bugetti3;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Sönke on 24.03.2015.
 */
public class DateManager {

   private Date date;

    public DateManager(Date date) {
        this.date = date;
    }

    public DateManager() {
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.GERMAN);
        Date date = new Date();

        return dateFormat.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
