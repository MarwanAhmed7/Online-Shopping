package com.example.e_commerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.e_commerce.R;


public class AdminActivity extends AppCompatActivity {

    Button upload_btn;
    Button showallpro;
    Button showallorder;
    Button showchart;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.logout:
                Intent intent = new Intent(AdminActivity.this,Login.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);


        }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        upload_btn=findViewById(R.id.upload);
        showallpro=findViewById(R.id.showPro);
        showallorder=findViewById(R.id.showOrd);
        showchart=findViewById(R.id.chart);

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addPro=new Intent(AdminActivity.this,UploadProduct.class);
                startActivity(addPro);
            }
        });
        showallpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allpro=new Intent(AdminActivity.this,showAllProduct.class);
                startActivity(allpro);
            }
        });

        showallorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent allOrd=new Intent(AdminActivity.this,ShowAllOrders.class);
                startActivity(allOrd);
            }
        });

        showchart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ch=new Intent(AdminActivity.this,Chart.class);
                startActivity(ch);
            }
        });
    }
}