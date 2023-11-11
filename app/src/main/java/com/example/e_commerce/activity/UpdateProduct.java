package com.example.e_commerce.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.Database.MyDatabase;
import com.example.e_commerce.R;


public class UpdateProduct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        final MyDatabase pro=new MyDatabase(this);
        final EditText name=(EditText)findViewById(R.id.currntdtt);
        final EditText price=(EditText)findViewById(R.id.delvdatt);
        final EditText quantity=(EditText)findViewById(R.id.usnaame);
        final TextView hi=(TextView)findViewById(R.id.textView);
        Button update=(Button)findViewById(R.id.upload);

        name.setText(getIntent().getExtras().getString("name"));
        price.setText(getIntent().getExtras().getString("price"));
        quantity.setText(getIntent().getExtras().getString("quantity"));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pro.updateProduct(getIntent().getExtras().getString("name"),name.getText().toString(),price.getText().toString(),quantity.getText().toString());
                Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_LONG).show();
            }
        });
    }
}