package com.peters.snke.bugetti3;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Sönke on 23.03.2015.
 */
public class Password {
    private String password;
    Context applicationContext = MainActivity.getContextOfApplication();


    public String getPassword() {
        SharedPreferences preferences =PreferenceManager.getDefaultSharedPreferences(applicationContext);
        if(preferences.getString("passwod","").equals(null)||preferences.getString("passwod","").equals(""))
            return "E";
        else return preferences.getString("passwod","");
    }

    public void setPassword(String password) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("password", password);
        editor.commit();
        this.password = password;
    }

}
