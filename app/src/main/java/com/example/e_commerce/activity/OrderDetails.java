package com.example.e_commerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.e_commerce.Database.MyDatabase;
import com.example.e_commerce.Model.Order;
import com.example.e_commerce.R;
import com.example.e_commerce.fragment.Home;


public class OrderDetails extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
//                editor=sharedPreferences.edit();
//                editor.putString("username",null);
//                editor.putString("password",null);
//                editor.putBoolean("login",false);
//                editor.apply();
                Intent intent = new Intent(OrderDetails.this,Login.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.home:
                Intent intent1 = new Intent(OrderDetails.this, Home.class);
                startActivity(intent1);
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=new MenuInflater(this);
        inflater.inflate(R.menu.menu2,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        EditText currentD,deliverD,usern,phon,feedback,rate;

        MyDatabase database=new MyDatabase(this);

        currentD=findViewById(R.id.currentdate);
        deliverD=findViewById(R.id.deliverdate);
        usern=findViewById(R.id.custname);
        phon=findViewById(R.id.phnzz);
        feedback=findViewById(R.id.feedbackk);
        rate=findViewById(R.id.ratez);

        Button conOrd=findViewById(R.id.dn);

        conOrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.createNewOrder(currentD.getText().toString(),deliverD.getText().toString(),usern.getText().toString(),phon.getText().toString(),feedback.getText().toString(),rate.getText().toString());
                currentD.setText("");
                deliverD.setText("");
                usern.setText("");
                phon.setText("");
                feedback.setText("");
                rate.setText("");

                Toast.makeText(getApplicationContext(),"Order Confrimed",Toast.LENGTH_LONG).show();
            }
        });

    }
}