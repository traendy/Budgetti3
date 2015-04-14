package com.peters.snke.bugetti3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Sönke on 09.04.2015.
 */
public class BuyListActivity extends FragmentActivity implements BuyListDialogFragment.NoticeDialogListener {

    ExpandableListView buy_list_explv;
    Button newList_btn, mainmenu_btn;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ExpListAdapterForBl listAdapter;

    List<List<String>> listOfLists;
    List<String> contentList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buylist);
        listDataHeader = new ArrayList<>();
        contentList = new ArrayList<>();
        listDataChild=new HashMap<>();
        newList_btn= (Button)findViewById(R.id.newlist_btn);
        newList_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyListItems bLI = new BuyListItems();
                addClicked();

            }
        });
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
        listAdapter = new ExpListAdapterForBl(this, listDataHeader, listDataChild);
        buy_list_explv.setAdapter(listAdapter);

    }
    private void checkDbforListInformation(){
        listDataHeader.add("test");
        contentList.add("test");
        listDataChild.put(listDataHeader.get(0),contentList);
            return;
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        EditText buyList_et = (EditText)findViewById(R.id.buyList_et);
        String buylistheader=buyList_et.getText().toString();
        listDataHeader.add(buylistheader);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        return;


    }
    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new BuyListDialogFragment();
        dialog.show(getSupportFragmentManager(), "BuyListDialogFragment");
    }


    public void addClicked() {
        DialogFragment newFragment = new BuyListDialogFragment();
        newFragment.show(getSupportFragmentManager(), "buylists");
    }
}
