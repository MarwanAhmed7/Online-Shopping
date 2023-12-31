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

public class RecoveryPassword extends AppCompatActivity {

    EditText mail;
    Button load_pass;
    TextView show_pass;
    MyDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery_password);
        intiView();

        load_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recoveryPassword();
            }
        });

    }

    protected void intiView(){
        mail=findViewById(R.id.mail_recovery_pass);
        load_pass=findViewById(R.id.load_pass);
        show_pass=findViewById(R.id.pass_recovered);
        database=new MyDatabase(this);
    }

    protected void recoveryPassword(){

        String email=mail.getText().toString().trim();
        String pass=database.getPassword(email);
        if(pass==null)
            Toast.makeText(this, "Please Check your mail", Toast.LENGTH_SHORT).show();
        else
            show_pass.setText(pass);
    }
}
