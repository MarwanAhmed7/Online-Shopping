package com.example.e_commerce.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.e_commerce.Adabter.CartAdapter;
import com.example.e_commerce.Database.MyDatabase;
import com.example.e_commerce.Model.ProductModel;
import com.example.e_commerce.R;

import com.example.e_commerce.activity.OrderDetails;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class Cart extends Fragment {

    private ListView cart_products;
    private CartAdapter adapter;
    private ArrayList<ProductModel> data = new ArrayList<>();
    ProductModel productModel;


    private MyDatabase database;
    private SharedPreferences sharedPreferences;

    TextView orignal_price,delivery_cost,total_cost,location_txt;
    List<Address> addresses;
    FusedLocationProviderClient fusedLocationProviderClient;

    double cost=0;

    int PERMISSION_ID = 44;

    public Cart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);


        cart_products = view.findViewById(R.id.cart_product);

        database = new MyDatabase(getContext());

        orignal_price=view.findViewById(R.id.order_price);
        delivery_cost=view.findViewById(R.id.delivery_cost);
        total_cost=view.findViewById(R.id.total_cost);
        location_txt=view.findViewById(R.id.add_address);
        String location = location_txt.getText().toString();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());


        getProductsids();

        Button applyA= (Button) view.findViewById(R.id.apply_address);

        applyA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getlocation();
                } else
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            }


        });

        Button confirm=(Button)view.findViewById(R.id.confirm_order);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("in confirm");
                database.updateProductSoldCount(productModel.getProName(),productModel.getPro_quantity());
                Intent intent=new Intent(getActivity(), OrderDetails.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void getProductsids() {
        sharedPreferences = this.getActivity().getSharedPreferences("cart", Context.MODE_PRIVATE);
        String ids = sharedPreferences.getString("lastorder", null);
        if (ids != null) {
            Gson gson = new Gson();
            ArrayList id = gson.fromJson(ids, ArrayList.class);
            getCartProduct(id);


            adapter = new CartAdapter(getContext(), data);
            adapter.setTotal_cost(cost);
            cart_products.setAdapter(adapter);


            orignal_price.setText(String.valueOf(adapter.getTotal_cost()) + " $");
            delivery_cost.setText("20.0 $");
            total_cost.setText(cost + 20.0 + "$");
        }


    }

    private void getCartProduct(ArrayList<Integer> ids) {

        data.clear();
        for (int i = 0; i < ids.size(); i++) {
            Cursor cursor = database.getProductbyId(String.valueOf(ids.get(i)));
            if (cursor != null && cursor.moveToFirst()) {
                        productModel = new ProductModel(Integer.parseInt(cursor.getString(4)),
                        Integer.parseInt(cursor.getString(5)),
                        cursor.getString(1), cursor.getBlob(2),
                        Double.parseDouble(cursor.getString(3)));
                productModel.setPro_id(Integer.parseInt(cursor.getString(0)));
                data.add(productModel);
                cost+=Double.parseDouble(cursor.getString(3));
            }
        }

    }



    @SuppressLint("MissingPermission")
    private void getlocation()
    {
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        location_txt.setText(addresses.get(0).getAddressLine(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
}
}
