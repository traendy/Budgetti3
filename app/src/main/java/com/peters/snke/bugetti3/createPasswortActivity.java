package com.peters.snke.bugetti3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Sönke on 23.03.2015.
 */
public class createPasswortActivity extends Activity {
    EditText password_1, password_2;
    TextView accept_tv, notaccepted_tv;
    Button submit_btn;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        password_1 = (EditText)findViewById(R.id.password_1);
        password_2 = (EditText)findViewById(R.id.password_2);
        accept_tv = (TextView)findViewById(R.id.accept_tv);
        notaccepted_tv = (TextView)findViewById(R.id.notaccepted_tv);
        submit_btn = (Button)findViewById(R.id.submit_btn);
        accept_tv.setVisibility(View.INVISIBLE);
        notaccepted_tv.setVisibility(View.INVISIBLE);
        submit_btn.setVisibility(View.INVISIBLE);

            if(password_2.getText().toString().length()>=1  && password_2.getText().toString().length()<=3){
                notaccepted_tv.setText("Passwords need to be\nat least 3 didgits long!");
                notaccepted_tv.setVisibility(View.VISIBLE);
                submit_btn.setVisibility(View.INVISIBLE);
                accept_tv.setVisibility(View.INVISIBLE);
            }
            if(!(password_1.getText().toString().equals(password_2.getText().toString()))&&password_2.getText().toString().length()>=3){
                notaccepted_tv.setText("Passwords need to be\nthe same!");
                notaccepted_tv.setVisibility(View.VISIBLE);
                submit_btn.setVisibility(View.INVISIBLE);
                accept_tv.setVisibility(View.INVISIBLE);
            }


            if(password_1.getText().toString().length()>=3 && password_2.getText().toString().length()>=3 && password_1.getText().toString().equals(password_2.getText().toString())){
                submit_btn.setVisibility(View.VISIBLE);
                accept_tv.setVisibility(View.VISIBLE);
                notaccepted_tv.setVisibility(View.INVISIBLE);
            }
        }


    public void submitClicked(View view){

        password =password_2.getText().toString();
        Password pw= new Password();
        pw.setPassword(password);
        Intent resetIntent = new Intent(this, ResetActivity.class);
        startActivity(resetIntent);
        this.finish();
    }
    @Override
    public void onBackPressed() {

        this.finish();
    }
}
