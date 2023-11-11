package com.example.e_commerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.e_commerce.Database.MyDatabase;
import com.example.e_commerce.R;

import java.util.ArrayList;


public class showAllProduct extends AppCompatActivity {

    ListView products;
    ArrayAdapter<String> proAdapter;
    MyDatabase db;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logouttt:
                Intent intent = new Intent(showAllProduct.this,Login.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.aHome:
                Intent intent1 = new Intent(showAllProduct.this, AdminActivity.class);
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.conmenu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    protected void onRestart() {
        proAdapter.clear();
        Cursor cursor=db.getProducts();

        while (!cursor.isAfterLast()){
            proAdapter.add(cursor.getString(1));
            cursor.moveToNext();
        }
        super.onRestart();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String selectedpro=((TextView) info.targetView).getText().toString();
        int id=item.getItemId();
        if(id==R.id.item_delete){
            proAdapter.remove(selectedpro);
            db.deleteProduct(selectedpro);
            return true;
        }
        if(id==R.id.item_update){
            Intent in=new Intent(showAllProduct.this,UpdateProduct.class);
            in.putExtra("name",selectedpro);
            in.putExtra("price",db.getProPrice(selectedpro));
            in.putExtra("quantity",db.getProQuantity(selectedpro));
            startActivity(in);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_product);

        products=(ListView)findViewById(R.id.list);
        proAdapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
        products.setAdapter(proAdapter);

        db=new MyDatabase(getApplicationContext());
        Cursor cursor=db.getProducts();
        while (!cursor.isAfterLast()){
            proAdapter.add(cursor.getString(1));
            cursor.moveToNext();
        }
        registerForContextMenu(products);
        products.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                String current=parent.getItemAtPosition(position).toString();
                ArrayList<String> values= db.getProDetails(current);
                ShowDetails.s1= values.get(0);
                ShowDetails.s2=values.get(1);
                ShowDetails.s3=values.get(2);
                Intent intent = new Intent(showAllProduct.this, ShowDetails.class);
                startActivity(intent);
            }
        });
    }
}