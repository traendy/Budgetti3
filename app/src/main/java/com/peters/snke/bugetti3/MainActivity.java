package com.peters.snke.bugetti3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import java.util.HashMap;
import java.util.List;


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
        dao = helper.getChargeDao();
        chargeList = dao != null ? dao.queryForAll() : null;
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        String oldDate, newDate;
        for (int i = 0; i < chargeList.size(); i++) {
            newDate = chargeList.get(i).getDate();
            listDataHeader.add(newDate);
            for (int k = 0; k < listDataHeader.size(); k++) {
                int n = listDataHeader.size();
                oldDate = listDataHeader.get(k);
                if (n <= k + 1) {
                    newDate = listDataHeader.get(k + 1);
                }
                if (oldDate == newDate) listDataHeader.remove(k + 1);
            }
        }
        for (int l = 0; l < listDataHeader.size(); l++) {
            List<String> childList = new ArrayList<String>();
            String HeaderDate = listDataHeader.get(l);
            int m = 0;
            StringBuilder sb = new StringBuilder();
            while (HeaderDate == chargeList.get(m).getDate()) {
                sb.append(chargeList.get(m).getCharge_name()).append(": \t\t").append(String.valueOf(chargeList.get(m).getCharge_amount()));
                childList.add(sb.toString());
                m++;
            }
            listDataChild.put(listDataHeader.get(l), childList);
        }

    }

    public void reset(View view) throws SQLException {
        Dao<Charges, Integer> dao = null;
        dao = helper.getChargeDao();
        dao.delete(chargeList);
        helper.deleteAll();

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

        addChargeToDb(charge);
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (dao != null) {
            try {
                dao.create(charge);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private Charges getChargeFromView() {
        DateManager dm = new DateManager();
        Charges charge = new Charges();

        if (AddName.getText().toString().isEmpty()) charge.setCharge_name("Default");
        else charge.setCharge_name(AddName.getText().toString());
        if (AddCosts.getText().toString().isEmpty()) return null;
        else charge.setCharge_amount(Float.parseFloat(AddCosts.getText().toString()));
        charge.setDate(dm.getDate());
        return charge;
    }

    public void testapp(View view) {
        Intent BudgetIntent = new Intent(this, BudgetActivity.class);
        startActivity(BudgetIntent);
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
