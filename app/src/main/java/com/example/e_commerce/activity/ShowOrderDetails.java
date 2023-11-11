package com.example.e_commerce.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.e_commerce.R;


public class ShowOrderDetails extends AppCompatActivity {

    static String s1,s2,s3,s4,s5,s6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order_details);



        TextView cdate,ddate,phnumber,city,feedback,rate;

        cdate=findViewById(R.id.currntdtt);
        ddate=findViewById(R.id.delvdatt);
        phnumber=findViewById(R.id.usnaame);
        city=findViewById(R.id.ciity);
        feedback=findViewById(R.id.fdback);
        rate=findViewById(R.id.rte);

        cdate.setText(s1);
        ddate.setText(s2);
        phnumber.setText(s3);
        city.setText(s4);
        feedback.setText(s5);
        rate.setText(s6);
    }
}