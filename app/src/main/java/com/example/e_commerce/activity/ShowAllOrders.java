package com.example.e_commerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.e_commerce.Database.MyDatabase;
import com.example.e_commerce.Model.Order;
import com.example.e_commerce.R;

import java.util.ArrayList;


public class ShowAllOrders extends AppCompatActivity {

    ListView orderList;
    ArrayAdapter<String> arrayAdapter;
    MyDatabase database;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logouttt:
                Intent intent = new Intent(ShowAllOrders.this,Login.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.aHome:
                Intent intent1 = new Intent(ShowAllOrders.this, AdminActivity.class);
                startActivity(intent1);
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.menu3,menu);
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_orders);

        orderList=findViewById(R.id.listtt);
        arrayAdapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        orderList.setAdapter(arrayAdapter);
        database=new MyDatabase(getApplicationContext());
        Cursor cursor=database.fetchallOrders();
        while (!cursor.isAfterLast()){
            arrayAdapter.add(cursor.getString(3));
            cursor.moveToNext();
        }

        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String current=parent.getItemAtPosition(position).toString();
                ArrayList<String> values=database.getOrderDetails(current);
                ShowOrderDetails.s1=values.get(0);
                ShowOrderDetails.s2=values.get(1);
                ShowOrderDetails.s3=values.get(2);
                ShowOrderDetails.s4=values.get(3);
                ShowOrderDetails.s5=values.get(4);
                ShowOrderDetails.s6=values.get(5);
                Intent intent=new Intent(ShowAllOrders.this,ShowOrderDetails.class);
                startActivity(intent);
            }
    });


    }
}