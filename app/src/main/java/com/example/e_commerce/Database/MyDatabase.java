package com.example.e_commerce.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.e_commerce.Model.CategoryModel;
import com.example.e_commerce.Model.ChartModel;
import com.example.e_commerce.Model.CustomerModel;
import com.example.e_commerce.Model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {

    final static String dataName = "Mydatabase";
    SQLiteDatabase database;

    public MyDatabase(@Nullable Context context) {
        super(context, dataName, null, 3);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.setVersion(oldVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table  customer (id integer primary key  autoincrement , name text not null, email text not null," +
                "password text not null, gender text not null, birthdate text , jop text )");

        db.execSQL("create table category (id integer primary key  autoincrement , name text not null )");

        db.execSQL("create table product (id integer primary key autoincrement, name text not null ,image blob ," +
                "price real not null , quantity integer not null , cate_id integer not null ," +
                "foreign key (cate_id)references category (id))");

        db.execSQL("create table  orders (id integer primary key  autoincrement , currentdate text not null , deliverdate text not null , "+
                "phonenumber text not null , city text not null , feedback text not null , rate text not null)");

        db.execSQL("create table ordersDetails (proName text not null ,proQuantity text not null)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists customer");
        db.execSQL("drop table if exists category");
        db.execSQL("drop table if exists product");
        db.execSQL("drop table if exists orders");
        db.execSQL("drop table if exists ordersDetails");
        onCreate(db);

    }

    public List<ChartModel> getAllSoldPro(){
        database=getReadableDatabase();
        Cursor cursor=database.rawQuery("select proName, proQuantity from ordersDetails",null);
        List<ChartModel> chartModels=new ArrayList<>();
        System.out.println("Cursor of sold items  "+cursor.getCount());
        if(cursor.moveToFirst()){
            do{
                chartModels.add(new ChartModel(cursor.getString(0),cursor.getInt(1)));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return chartModels;
    }


    public void addProductToProductsSellingCount(String productName){
        database= getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("proName",productName);
        values.put("proQuantity",1);
        database.insert("ordersDetails", null, values);
        database.close();
    }

//    public int getProductSoldCount(String productName){
//        database= getWritableDatabase();
//        Cursor c=database.rawQuery("select name, quantity from product where name = '" + productName+"'" , null);
//        System.out.println(c.getCount());
//        c.moveToFirst();
//        return  c.getInt(0);
//    }

    public void updateProductSoldCount(String productName,int count){
        if(!productsSellingCountContainsProduct(productName)){
            addProductToProductsSellingCount(productName);
            System.out.println("Product Added for first time");
        }
        else {
            System.out.println("updateee product in orderees");
            String[] arg={productName};
            Cursor c=database.rawQuery("select proQuantity from ordersDetails where proName like ?",arg);
            int value=0;
            while (c.moveToNext()){
            System.out.println("Order quantity "+c.getInt(0));
             value=c.getInt(0);}
            database = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("proName", productName);

            int newCount = value + count;
            values.put("proQuantity", newCount);
            System.out.println("new count "+newCount);
            database.update("ordersDetails", values, "proName=?", new String[]{productName});
        }
    }


    public boolean productsSellingCountContainsProduct(String productName){

        database=getReadableDatabase();
        String[] arg={productName};
        Cursor cursor=database.rawQuery("Select proName,proQuantity from ordersDetails where proName like ?",arg);
        if (cursor.getCount()>0){
            System.out.println("founnnddd");
            while (cursor.moveToNext()) {
                System.out.println("nameee in orderrs" + cursor.getString(0));
            }
            return true;
        }
        else{
            System.out.println("nottt founnnddd");
            return false;
        }
    }


//        public void storeSoldItem(String pName){
//        database=getWritableDatabase();
//
//        ContentValues values=new ContentValues();
//        values.put("proName",pName);
//        values.put("proQuantity",0);
//
//        System.out.println(pName+"  ");
//        database.insert("ordersDetails",null,values);
//
//        database.close();
//    }

    public void createNewOrder(String currentdate,String deliverdate,String phonenumber,String city,String feedback,String rate){
        database=getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put("currentdate",currentdate);
        values.put("deliverdate",deliverdate);
        values.put("phonenumber",phonenumber);
        values.put("city",city);
        values.put("feedback",feedback);
        values.put("rate",rate);

        database.insert("orders",null,values);
        database.close();
    }

    public Cursor fetchallOrders(){
        database=getReadableDatabase();
        String[] rowDetails={"id","currentdate","deliverdate","phonenumber","city","feedback","rate"};
        Cursor cursor=database.query("orders",rowDetails,null,null,null,null,null);
        if (cursor!=null)
            cursor.moveToFirst();

        database.close();

        return cursor;
    }

    public void insertCustomer(CustomerModel cust) {
        database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", cust.getCustName());
        values.put("email", cust.getMail());
        values.put("password", cust.getPassword());
        values.put("birthdate", cust.getCustBirthDate());
        values.put("gender", cust.getGender());
        values.put("jop", cust.getCustJop());

        database.insert("customer", null, values);
        database.close();

    }

    public Cursor userLogin(String username, String pass) {
        database = getReadableDatabase();
        String[] args = {username, pass};
        //database.query("customer","id",raw,null,null,null,null,null);
        Cursor cursor = database.rawQuery("select id from customer where name =? and  password =? ", args);

        if (cursor != null)
            cursor.moveToFirst();

        database.close();
        return cursor;

    }

    public String getPassword(String mail) {
        database = getReadableDatabase();
        String[] args = {mail};
        Cursor cursor = database.rawQuery("select password from customer where email =?", args);
        if (cursor.getCount()>0) {

            cursor.moveToFirst();
            database.close();
            return cursor.getString(0);
        }
        database.close();

        cursor.close();
        return null;
    }

    public void insertProduct(ProductModel product){
        database=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",product.getProName());
        values.put("image",product.getProImage());
        values.put("price",product.getPrice());
        values.put("quantity",product.getPro_quantity());
        values.put("cate_id",product.getCatId());

        database.insert("product",null,values);
        database.close();
    }

    public Cursor getProducts(){
        database=getReadableDatabase();
        String[]fields={"id","name","image","price","quantity","cate_id"};
       Cursor cursor= database.query("product",fields,null,null,null,null,null);

       if (cursor!=null)
           cursor.moveToFirst();

       database.close();

       return cursor;
    }

    public void deleteProduct(String name){
        database=getReadableDatabase();
        database.delete("product","name='"+name+"'",null);
        database.delete("ordersDetails","proName='"+name+"'",null);
        database.close();
    }

    public void updateProduct(String oldname,String newname,String newprice,String newquality){
        database=getReadableDatabase();
        ContentValues row=new ContentValues();
        row.put("name",newname);
        row.put("price",newprice);
        row.put("quantity",newquality);

        ContentValues row2=new ContentValues();
        row2.put("proName",newname);
        row2.put("proQuantity",newquality);
        database.update("product",row,"name like ?",new String[]{oldname});
        database.update("ordersDetails",row2,"proName like ?",new String[]{oldname});
        database.close();
    }



    public String getProPrice(String name){
        database=getReadableDatabase();
        String[] arg={name};
        Cursor cursor=database.rawQuery("Select price from product where name like ?",arg);
        cursor.moveToFirst();

        database.close();
        return cursor.getString(0);
    }

    public String getProQuantity(String name){
        database=getReadableDatabase();
        String[] arg={name};
        Cursor cursor=database.rawQuery("Select quantity from product where name like ?",arg);
        cursor.moveToFirst();
        database.close();
        return cursor.getString(0);
    }

    public ArrayList<String> getProDetails(String p){
        ArrayList<String> productDetails=new ArrayList<>();
        database=getReadableDatabase();
        Cursor cursor=database.rawQuery("select * from product where name='"+p+"'",null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                String pname=cursor.getString(cursor.getColumnIndex("name"));
                String pprice=cursor.getString(cursor.getColumnIndex("price"));
                String pquantity=cursor.getString(cursor.getColumnIndex("quantity"));
                productDetails.add(pname);
                productDetails.add(pprice);
                productDetails.add(pquantity);
            }
        }
        return productDetails;
    }

    public ArrayList<String> getOrderDetails(String order){
        ArrayList<String> orderDetails=new ArrayList<>();
        database=getReadableDatabase();
        Cursor cursor=database.rawQuery("select * from orders where phonenumber='"+order+"'",null);
        if(cursor!=null){
            while (cursor.moveToNext()){
                String ocurrentD=cursor.getString(cursor.getColumnIndex("currentdate"));
                String odeliverD=cursor.getString(cursor.getColumnIndex("deliverdate"));
                String ophonenumber=cursor.getString(cursor.getColumnIndex("phonenumber"));
                String ocity=cursor.getString(cursor.getColumnIndex("city"));
                String ofeedback=cursor.getString(cursor.getColumnIndex("feedback"));
                String orate=cursor.getString(cursor.getColumnIndex("rate"));

                orderDetails.add(ocurrentD);
                orderDetails.add(odeliverD);
                orderDetails.add(ophonenumber);
                orderDetails.add(ocity);
                orderDetails.add(ofeedback);
                orderDetails.add(orate);
            }
        }
        return orderDetails;
    }

    public void insertCategory(CategoryModel cate){
        database=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name",cate.getCat_name());
        database.insert("category",null,values);
        database.close();
    }
    public Cursor getCategory(){
        database=getReadableDatabase();
        String []fields={"id","name"};
       Cursor cursor= database.query("category",fields,null,null,null,null,null);

       if (cursor.getCount()>0)
            cursor.moveToFirst();

       database.close();

       return cursor;
    }
    public Cursor getProductbyCategor(String cate){
        database=getReadableDatabase();
        String []args={cate};
      Cursor cursor=  database.rawQuery("select * from product where cate_id =? ",args);
        if (cursor!=null)
            cursor.moveToFirst();

        return cursor;

    }

    public Cursor getProductbyId(String id){
        database=getReadableDatabase();
        String []args={id};
        Cursor cursor=  database.rawQuery("select * from product where id =? ",args);
        if (cursor!=null)
            cursor.moveToFirst();

        return cursor;

    }
    public String getCatId(String name ){
        database=getReadableDatabase();
        String[]args={name};
        Cursor cursor=database.rawQuery("select id from category where name =?",args);

        if (cursor.getCount()>0) {

            cursor.moveToFirst();
            database.close();
            return cursor.getString(0);
        }
        database.close();

        cursor.close();
        return null;

    }
}
