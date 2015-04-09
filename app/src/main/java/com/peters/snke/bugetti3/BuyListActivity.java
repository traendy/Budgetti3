package com.peters.snke.bugetti3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sönke on 09.04.2015.
 */
public class BuyListActivity extends Activity {

    ExpandableListView buy_list_explv;
    Button newList_btn, mainmenu_btn;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ExpandableListAdapter listAdapter;

    List<List<String>> listOfLists;
    List<String> contentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buylist);

        newList_btn= (Button)findViewById(R.id.newlist_btn);
        buy_list_explv=(ExpandableListView)findViewById(R.id.buy_list_explv);
        mainmenu_btn= (Button)findViewById(R.id.mainmenu_btn);
        mainmenu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent( BuyListActivity.this, MainActivity.class);
                startActivity(backIntent);
                BuyListActivity.this.finish();
            }
        });
        createListAdapter();
    }

    private void createListAdapter(){
        checkDbforListInformation();//prepare list data here
        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
        buy_list_explv.setAdapter(listAdapter);

    }
    private void checkDbforListInformation(){

    }
}
