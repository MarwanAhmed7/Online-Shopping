package com.example.e_commerce.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.e_commerce.R;


public class ShowDetails extends AppCompatActivity {

    static String s1,s2,s3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        TextView name,price,quantity;
        name=findViewById(R.id.currntdtt);
        price=findViewById(R.id.delvdatt);
        quantity=findViewById(R.id.usnaame);

        name.setText(s1);
        price.setText(s2);
        quantity.setText(s3);
    }
}