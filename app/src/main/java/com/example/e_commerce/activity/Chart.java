package com.example.e_commerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerce.Database.MyDatabase;
import com.example.e_commerce.Model.ChartModel;
import com.example.e_commerce.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class Chart extends AppCompatActivity {

//    BarChart barChart;
    PieChart pieChart;
    MyDatabase database;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logouttt:
                Intent intent = new Intent(Chart.this,Login.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.aHome:
                Intent intent1 = new Intent(Chart.this, AdminActivity.class);
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
        setContentView(R.layout.activity_chart);

//        barChart=findViewById(R.id.bar_chart);
        pieChart=findViewById(R.id.pie_chart);
        database=new MyDatabase(getApplicationContext());

//        ArrayList<BarEntry> barEntries=new ArrayList<>();
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        List<ChartModel> products=database.getAllSoldPro();

        System.out.println("Product size "+products.size());

        for(int i=0;i<products.size();i++){
            float vale=(float)(i*10.0);
//            BarEntry barEntry=new BarEntry(i, products.get(i).count);
            System.out.println("Products Name "+products.get(i).getProName());
            PieEntry pieEntry = new PieEntry(products.get(i).getProQuantity(),products.get(i).getProName());
//            barEntries.add(barEntry);
            pieEntries.add(pieEntry);
        }
//        BarDataSet barDataSet=new BarDataSet(barEntries,"Best Selling Product");
//        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//        barDataSet.setDrawValues(false);

//        barChart.setData(new BarData(barDataSet));
//
//        barChart.animateY(5000);
//
//        barChart.getDescription().setText("Best Selling Product Chart");
//
//        barChart.getDescription().setTextColor(Color.BLUE);

        PieDataSet pieDataSet = new PieDataSet(pieEntries,"Products");

        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        pieChart.setData(new PieData(pieDataSet));

        pieChart.animateXY(5000,5000);

        pieChart.getDescription().setEnabled(false);
    }
}