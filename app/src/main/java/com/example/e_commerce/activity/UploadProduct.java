package com.example.e_commerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_commerce.Database.MyDatabase;
import com.example.e_commerce.Model.CategoryModel;
import com.example.e_commerce.Model.ProductModel;
import com.example.e_commerce.R;
import com.example.e_commerce.fragment.Home;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class UploadProduct extends AppCompatActivity {

    ImageView productimage;
    EditText productname, productprice, productquantity;
    Spinner proCategory;
    ArrayAdapter adapter;
    Button upload_btn;
    TextView reset_btn;
    MyDatabase database;

    final static int GALLERY_REQUEST_CODE = 101;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logouttt:
                Intent intent = new Intent(UploadProduct.this,Login.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.aHome:
                Intent intent1 = new Intent(UploadProduct.this, AdminActivity.class);
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
        setContentView(R.layout.activity_upload_product);
        intiView();
        addCategory();
        getAllcategory();

        productimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { uploadProduct();
            }
        });
    }

    protected void addCategory(){
        database.insertCategory(new CategoryModel("fashion"));
        database.insertCategory(new CategoryModel("cars"));
        database.insertCategory(new CategoryModel("sport"));


    }

    protected void intiView() {

        productimage = findViewById(R.id.product_image);
        productname = findViewById(R.id.product_name);
        productprice = findViewById(R.id.product_price);
        productquantity = findViewById(R.id.product_quantity);
        proCategory = findViewById(R.id.category);
        upload_btn = findViewById(R.id.btn_upload);
        reset_btn = findViewById(R.id.reset);
        database = new MyDatabase(this);
    }

    protected void chooseImage() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        // Launching the Intent
        startActivityForResult(intent, GALLERY_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                productimage.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    protected static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    protected void getAllcategory(){

        List<String>cate=new ArrayList<>();
        Cursor cursor=database.getCategory();
        if (cursor!=null){
            while (!cursor.isAfterLast()){
                cate.add(cursor.getString(1));
                cursor.moveToNext();
            }
            adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,cate);
            proCategory.setAdapter(adapter);
        }
    }

    protected void uploadProduct(){

        String name=productname.getText().toString().trim();
        String price=productprice.getText().toString().trim();
        String quan=productquantity.getText().toString().trim();
        int catid=Integer.parseInt(database.getCatId(proCategory.getSelectedItem().toString()));
        byte [] image=imageViewToByte(productimage);

        if(!name.equals("")||!price.equals("")||!quan.equals(""))
        {
            ProductModel productModel = new ProductModel(Integer.parseInt(quan), catid,name,image,Double.parseDouble(price));
            database.insertProduct(productModel);


            productimage.setImageResource(R.drawable.proimg);
            productname.setText("");
            productprice.setText("");
            productquantity.setText("");


            Toast.makeText(this, "product added", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Check data again", Toast.LENGTH_SHORT).show();
        }
    }

}
