package com.peters.snke.bugetti3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Sönke on 09.04.2015.
 */
public class menu_activity extends Activity{
    Button open_buy_list_btn, back_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        open_buy_list_btn= (Button) findViewById(R.id.open_buy_list_btn);
        open_buy_list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buyListIntent = new Intent(menu_activity.this, BuyListActivity.class);
                startActivity(buyListIntent);
                menu_activity.this.finish();
            }
        });

        back_btn=(Button)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent( menu_activity.this, MainActivity.class);
                startActivity(backIntent);
                menu_activity.this.finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent( menu_activity.this, MainActivity.class);
        startActivity(backIntent);
        menu_activity.this.finish();
    }
}
