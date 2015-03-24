package com.peters.snke.bugetti3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Sönke on 23.03.2015.
 */
public class ResetActivity extends Activity {
    EditText budget_et, password_et;
    Button budget_confirm_btn;
    BudgetManager bm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        budget_et= (EditText)findViewById(R.id.budget_et);
        password_et= (EditText)findViewById(R.id.password_et);
        budget_confirm_btn=(Button)findViewById(R.id.budget_confirm_btn);
        while(budget_et.getText().toString().equals("")&& !password_et.getText().toString().equals(getPW())){
        budget_confirm_btn.setVisibility(View.INVISIBLE);}
        if(!(budget_et.getText().toString().equals("")) && (password_et.getText().toString().equals(getPW()))){
            budget_confirm_btn.setVisibility(View.VISIBLE);
        }
    }
    private void confirmBudget (View view){
        bm= new BudgetManager();
        bm.setBudget(Float.parseFloat(budget_et.getText().toString()));
        Toast.makeText(this, "Budget is now: " +budget_et.getText().toString(), Toast.LENGTH_SHORT).show();
        Intent menuIntent= new Intent(this, MainActivity.class);
        startActivity(menuIntent);
        this.finish();
    }

    private String getPW(){
        Password pw = new Password();
        return pw.getPassword();
    }

    @Override
    public void onBackPressed() {
        Intent newMenu= new Intent(this, MainActivity.class);
        startActivity(newMenu);
        this.finish();
    }
}
