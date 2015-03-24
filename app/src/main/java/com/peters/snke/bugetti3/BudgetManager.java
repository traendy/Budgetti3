package com.peters.snke.bugetti3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

/**
 * Created by Sönke on 23.03.2015.
 */
public class BudgetManager {
    Context applicationContext = MainActivity.getContextOfApplication();
    private Float Budget;

    public BudgetManager() {
    }

    public BudgetManager(Float budget) {
        this.Budget = budget;

    }



    public void compareBudget(float newBudget) {
        if (getBudget() != newBudget) {
            setBudget(newBudget);        }
    }

    public Float getBudget() {
        SharedPreferences preferences =PreferenceManager.getDefaultSharedPreferences(applicationContext);
        Budget = preferences.getFloat("budget", 0.0f);
        if(Budget==null)return 0.0f;
        return Budget;
    }
    public void setBudget(Float newBudget) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat("budget", newBudget);
        this.Budget=newBudget;
        editor.commit();

    }


}
