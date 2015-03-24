package com.peters.snke.bugetti3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class MainActivity extends Activity {
    private final String LOG_TAG = getClass().getSimpleName();
    Context context = this;
    EditText AddCosts, AddName;
    TextView Paid, Left, ResultText;
    Button resButton, calculateButton, btn_b;
    ListView listView;
    Password pw;
    DatabaseHelper helper;
    List<Charges> chargeList;
    List<String> StringList;
    private Float Budget;
    BudgetManager bm;
    private String password="";
    public static Context contextOfApplication;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contextOfApplication=getApplicationContext();
        bm = new BudgetManager();
        pw= new Password();
        helper = new DatabaseHelper(getApplicationContext());
       // helper = new DatabaseHelper(this);
        try {
            initialize();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(pw.getPassword().equals("E")){
            Intent createPasswordIntent = new Intent(this, createPasswortActivity.class);
            startActivity(createPasswordIntent);
            }


        Log.i(LOG_TAG, "Creating " + getClass() + " at " + System.currentTimeMillis());
        setContentView(R.layout.activity_main);


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
        ResultText.setText(String.valueOf(bm.getBudget()) + " €");
        resButton = (Button) findViewById(R.id.resButton);
        calculateButton = (Button) findViewById(R.id.calculateButton);
        btn_b = (Button) findViewById(R.id.btn_b);
        StringList = new ArrayList();
        chargeList = new ArrayList<Charges>();
        listView = (ListView) findViewById(R.id.listView);
        //loadChargeList();
        createListAdapter();
    }

    private void createListAdapter() throws SQLException {
        loadChargeList();
        ListAdapter adapter = new ArrayAdapter<Charges>(getApplicationContext(), android.R.layout.simple_list_item_1, chargeList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TOdo
            }
        });
    }

    private void loadChargeList() throws SQLException {
        Dao<Charges, Integer> dao = null;
        dao = helper.getChargeDao();

        chargeList = dao != null ? dao.queryForAll() : null;
    }

    public String chargeToString(Charges charge) {


        return charge.toString();
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
        System.out.println("calculate");
        float newBudget;
        //todo check if database gets items, calculate leftbudget and update everything
        Charges charge = getChargeFromView();
        newBudget = bm.getBudget() - charge.getCharge_amount();
        bm.setBudget(newBudget);
        ResultText.setText(String.valueOf(newBudget) + " €");

        addChargeToDb(charge);
        updateListView();

    }

    private void updateListView() {
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
        DateManager dm= new DateManager();
        Charges charge = new Charges();
        charge.setCharge_name(AddName.getText().toString());
        charge.setCharge_amount(Float.parseFloat(AddCosts.getText().toString()));
        charge.setDate(dm.getDate());
        return charge;
    }


    public void testapp(View view) {
        try {
            checkDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void checkDatabase() throws SQLException {
        Dao<Charges, Integer> dao = null;
        dao = helper.getChargeDao();

        System.out.println("_________________---------");
        System.out.println("|checkDatabase: ");
        System.out.println("|getConnectionSource: " + dao.getConnectionSource());
        System.out.println("|queryforid 0 : " + dao.queryForId(0));
        System.out.println("|queryforid 1 : " + dao.queryForId(1));
        System.out.println("|queryforid 2 : " + dao.queryForId(2));
        System.out.println("|queryforid 3 : " + dao.queryForId(3));
        System.out.println("_________________-----------");

    }

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        helper.close();
        OpenHelperManager.releaseHelper();
        helper = null;
    }
}
