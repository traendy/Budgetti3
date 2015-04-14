package com.peters.snke.bugetti3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


public class MainActivity extends Activity {
    public static Context contextOfApplication;
    private final String LOG_TAG = getClass().getSimpleName();
    Context context = this;
    EditText AddCosts, AddName;
    TextView Paid, Left, ResultText;
    Button resButton, calculateButton, btn_b;
    ExpandableListView expListView;
    Password pw;
    DatabaseHelper helper;
    List<Charges> chargeList;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ExpandableListAdapter listAdapter;
    BudgetManager bm;
    List<List<String>> listOfLists;
    List<String> contentList;
    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contextOfApplication = getApplicationContext();
        bm = new BudgetManager();
        pw = new Password();
        helper = new DatabaseHelper(getApplicationContext());
        // helper = new DatabaseHelper(this);
        try {
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (pw.getPassword().equals("E")) {
            Intent createPasswordIntent = new Intent(this, createPasswortActivity.class);
            startActivity(createPasswordIntent);
        }


        Log.i(LOG_TAG, "Creating " + getClass() + " at " + System.currentTimeMillis());


        try {
            loadChargeList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void initialize() throws SQLException {

        AddCosts = (EditText) findViewById(R.id.Addcosts);
        AddName = (EditText) findViewById(R.id.Addname);
        Paid = (TextView) findViewById(R.id.Paid);
        Left = (TextView) findViewById(R.id.Left);
        ResultText = (TextView) findViewById(R.id.ResultText);
        ResultText.setText(String.valueOf(bm.getMainBudget()) + " €");
        resButton = (Button) findViewById(R.id.resButton);
        calculateButton = (Button) findViewById(R.id.calculateButton);
        btn_b = (Button) findViewById(R.id.btn_b);


        chargeList = new ArrayList<Charges>();
        expListView = (ExpandableListView) findViewById(R.id.expListView);
        //loadChargeList();
        createListAdapter();
    }

    private void createListAdapter() throws SQLException {
        loadChargeList();//prepare list data here
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

    }

    private void loadChargeList() throws SQLException {
        Dao<Charges, Integer> dao = null;
        boolean listwork = true;
        dao = helper.getChargeDao();
        listOfLists= new ArrayList<List<String>>();//erstellen einer Liste in die dann wiederrum die listen mit den name amounts eingefügt wird
        chargeList = dao != null ? dao.queryForAll() : null;
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        List<String> dateList = new ArrayList<String>();
        contentList = new ArrayList<String>();

        int sizeofChargelist=chargeList.size();
        StringBuilder sb = new StringBuilder();
        for(int dateCounter=0; dateCounter<sizeofChargelist; dateCounter++){
            if(!(chargeList.get(dateCounter).getDate().trim().equals("old")))listDataHeader.add(chargeList.get(dateCounter).getDate());

        }
        if(listDataHeader.size()==0){
            if(chargeList.size()==0)return;
            contentList.add(chargeList.get(0).getCharge_name()+":::"+chargeList.get(0).getCharge_amount());
            listDataChild.put(chargeList.get(0).getDate(), contentList);
            return;
        }
        for(int p =0; p<listDataHeader.size(); p++){

            for(int k=0; k<chargeList.size();k++) {

                List<String> contentPartList = new ArrayList<String>();
                while(listwork){


                    sb.append(chargeList.get(k).getCharge_name()).append(":::").append(chargeList.get(k).getCharge_amount());

                    contentPartList.add(sb.toString());
                    sb.setLength(0);
                    if(contentPartList.size()==0)return;
                    k++;
                    if(k>=chargeList.size())break;
                    if(!(chargeList.get(k).equals(null))&&!(chargeList.get(k).getDate().trim().equals("old")))listwork=false;
                }
                System.out.println(contentPartList);
                listOfLists.add(contentPartList);

                listwork=true;
            }

            listDataChild.put(listDataHeader.get(p),listOfLists.get(p));
        }

    }

    public void reset(View view) throws SQLException {
        Dao<Charges, Integer> dao = null;
        dao = helper.getChargeDao();
        dao.delete(chargeList);
        helper.deleteAll();
        DateManager dm = new DateManager();
        dm.deleteOldDate();
        Intent resetIntent = new Intent(this, ResetActivity.class);
        startActivity(resetIntent);
        this.finish();
    }


    public void calculate(View view) {

        float newBudget;

        Charges charge = getChargeFromView();
        if (charge == null) {
            Toast.makeText(this, "no paid-value", Toast.LENGTH_SHORT).show();
            return;
        }
        newBudget = bm.getBudget() - charge.getCharge_amount();
        bm.setBudget(newBudget);
        ResultText.setText(String.valueOf(newBudget) + " €");

        //addChargeToDb(charge); hab ich rausgenommen da ich in getCharge from view den charge zur db hinuzufügen will

        updateListView();

    }

    private void updateListView() {
        try {
            createListAdapter();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addChargeToDb(Charges charge) {
        Dao<Charges, Integer> dao = null;
        try {
            dao = helper.getChargeDao();
            dao.create(charge);

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    private Charges getChargeFromView() {
        DateManager dm = new DateManager();
        Charges charge = new Charges();
        Dao<Charges, Integer> dao = null;
        int size = 1;
        String oldDate = "";

        try {

            dao = helper.getChargeDao();
            if (dao != null) {
                size = dao.queryForAll().size();
                if (size > 0) oldDate = (dao.queryForId(size).getDate());
            }
            System.out.println("Die größe der db ist: " + size);
            System.out.println("old date an der id    " + (size) + " ist: " + oldDate.trim());
            System.out.println("dm.getDate() ist:     " + dm.getDate().trim());
            System.out.println(dao.queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (AddName.getText().toString().isEmpty()) charge.setCharge_name("Default");
        else charge.setCharge_name(AddName.getText().toString());
        if (AddCosts.getText().toString().isEmpty()) return null;
        else charge.setCharge_amount(Float.parseFloat(AddCosts.getText().toString()));

        if (dm.getOldDate().equals(dm.getDate())) charge.setDate("old");
        else {
            dm.setOldDate(dm.getDate());
            charge.setDate(dm.getDate());
            }


                    //if (oldDate.trim().equals(dm.getDate().trim())||oldDate.trim().equals("old")) charge.setDate("old");
                   // else charge.setDate(dm.getDate());

        try {
            dao.create(charge);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(dao.queryForAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return charge;
    }

    public void testapp(View view) {
        Intent menuIntent = new Intent(this, menu_activity.class);
        startActivity(menuIntent);
        this.finish();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        helper.close();
        OpenHelperManager.releaseHelper();
        helper = null;
    }
}
