package com.peters.snke.bugetti3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Sönke on 23.03.2015.
 */
public class ResetActivity extends Activity {
    EditText password_et;
    Button budget_confirm_btn, reset_budget_btn;
    BudgetManager bm;
    Context applicationContext = MainActivity.getContextOfApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_reset);
        bm= new BudgetManager();

        password_et= (EditText)findViewById(R.id.password_et);
        budget_confirm_btn=(Button)findViewById(R.id.budget_confirm_btn);
        budget_confirm_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!password_et.getText().toString().equals(getPW())){
                    Toast.makeText(ResetActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    return;}
                if((password_et.getText().toString().equals(getPW()))){
                   confirmBudget();
                }
            }
        });
        reset_budget_btn=(Button)findViewById(R.id.reset_budget_btn);
        reset_budget_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);


                if(!password_et.getText().toString().equals(getPW())){
                    Toast.makeText(ResetActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    return;}
                if((password_et.getText().toString().equals(getPW()))){
                    SharedPreferences.Editor editor =preferences.edit();
                    if(preferences.getFloat("mainbudget", 0.0f)==0.0f){
                        Toast.makeText(ResetActivity.this, "No Budget found! Define one!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    editor.putFloat("budget",preferences.getFloat("mainbudget",0.0f));
                    editor.commit();
                    Intent menuIntent = new Intent(ResetActivity.this, MainActivity.class);
                    startActivity(menuIntent);
                    ResetActivity.this.finish();
                }
            }
        });

    }
    private void confirmBudget (){
        Intent budgetIntent= new Intent(ResetActivity.this, BudgetActivity.class);
        startActivity(budgetIntent);
        this.finish();
    }

    private String getPW(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);

        return preferences.getString("password","");
    }

    @Override
    public void onBackPressed() {
        Intent newMenu= new Intent(this, MainActivity.class);
        startActivity(newMenu);
        this.finish();
    }
}
