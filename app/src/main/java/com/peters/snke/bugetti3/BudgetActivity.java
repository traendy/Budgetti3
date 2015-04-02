package com.peters.snke.bugetti3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sönke on 31.03.2015.
 */
public class BudgetActivity extends Activity {
    EditText amount_et, name_et;
    Button approve_btn, income_btn, expensives_btn;
    TextView budget_tv;
    ListView overview_lv;
    List overview, definitions, amounts;
    Context applicationContext = MainActivity.getContextOfApplication();
    BudgetDatabaseHelper helper;
    ListAdapter overviewAdapter;
    Dao<Budget, Integer> budgetdao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        helper= new BudgetDatabaseHelper(getApplicationContext());//applicationContext

        try {
            budgetdao=helper.getBudgetdao();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        amount_et=(EditText)findViewById(R.id.amount_et);
        name_et=(EditText)findViewById(R.id.name_et);

        approve_btn=(Button)findViewById(R.id.approve_btn);
        income_btn=(Button)findViewById(R.id.income_btn);
        expensives_btn=(Button)findViewById(R.id.expensives_btn);

        budget_tv=(TextView)findViewById(R.id.budget_tv);

        overview_lv=(ListView)findViewById(R.id.overview_lv);

        //todo liste optimieren

        amounts = new ArrayList<Float>();
        overview= new ArrayList<String>();
        definitions= new ArrayList<Budget>();
        initializeListView();

        approve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*approve();*/
                try {
                    helper.deleteAll();
                    overview.clear();
                    amounts.clear();
                    definitions.clear();
                    initializeListView();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            private void approve() {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
                SharedPreferences.Editor editor = preferences.edit();
                if(budget_tv.getText().toString().isEmpty()){
                   Toast.makeText(applicationContext, "Budget konnte nicht erfasst werden", Toast.LENGTH_SHORT).show();
                    return;
                }
                editor.putFloat("budget", Float.parseFloat(budget_tv.getText().toString()));
                editor.commit();
                Toast.makeText(applicationContext,"Budget wurde gesetzt", Toast.LENGTH_SHORT).show();
                Intent mainIntent = new Intent(applicationContext, MainActivity.class);
                startActivity(mainIntent);
            }
        });
        income_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addincome();
                calBudget();

            }
        });
        expensives_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addloss();
                calBudget();
            }
        });
    }

    private void initializeListView() {
        try {
            overview= budgetdao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        overviewAdapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,overview);
        overview_lv.setAdapter(overviewAdapter);
        overview_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id ) {
                for( int i = 0; i<overview.size();i++){
                    System.out.println("--------------");
                    System.out.println("overview: "+ overview.get(i));
                    System.out.println("--------------");
                }
                try {
                    budgetdao.deleteById(position);
                    overview.clear();
                    overview = budgetdao.queryForAll();

                    //overview_lv.deferNotifyDataSetChanged();
                    //upDatedata(); notifydatasetchanged???? warum findet er diese methode nicht -.-
                    overview_lv.invalidateViews();
                    overview_lv.setAdapter(overviewAdapter);
                }catch(Exception e){

                }
            }
        });
    }


    private void addincome(){
        Budget newBudget = new Budget();
        if(name_et.getText().toString().isEmpty()||amount_et.getText().toString().isEmpty()){
            Toast.makeText(this,"Bitte, füll alle Felder aus", Toast.LENGTH_SHORT).show();
            return;
        }

        newBudget.setName(name_et.getText().toString());
        newBudget.setAmount(Float.parseFloat(amount_et.getText().toString()));

        try {
            budgetdao.create(newBudget);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        initializeListView();
    }
    private void addloss(){
        Budget newBudget = new Budget();
        if(name_et.getText().toString().isEmpty()||amount_et.getText().toString().isEmpty()){
            Toast.makeText(this,"Bitte, füll alle Felder aus", Toast.LENGTH_SHORT).show();
            return;
        }

        newBudget.setName(name_et.getText().toString());
        newBudget.setAmount(Float.parseFloat(amount_et.getText().toString())*-1);

        try {
            budgetdao.create(newBudget);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        initializeListView();
    }

    private void calBudget(){
        float Budget=0.0f;
        try {
            definitions=budgetdao.queryForAll();
            for(int i =0; i<definitions.size(); i++){
                Budget testbudget = (Budget) definitions.get(i);
                Budget+=(testbudget.getAmount());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        budget_tv.setText(String.valueOf(Budget));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        helper.close();
        OpenHelperManager.releaseHelper();
        helper=null;
    }
}
