package com.example.tugbasalih.ab2015sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tugba on 02.02.2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static String DBNAME = "customer.db";
    private static int DBVERSION = 1;


    public DBHelper(Context context) {
        super(context, DBNAME, null, DBVERSION);
    }


    public long insertCustomer(Customer c) {

        long id = 0;

        ContentValues cv = new ContentValues();
        cv.put("name", c.getName());
        cv.put("age", c.getAge());

        SQLiteDatabase db = getWritableDatabase();
        id = db.insert("customer", null, cv);



        return id;

    }


    public List<Customer> getAllCustomers(){

        List<Customer> customers = new ArrayList<Customer>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from customer", null);


        while (cursor.moveToNext()){

            Customer c = new Customer();
                c.setId(cursor.getInt(0));
                c.setName(cursor.getString(1));
                c.setAge(cursor.getString(2));

            customers.add(c);
        }
        return customers;

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table customer("
                + "id integer primary key autoincrement,"
                + "name text not null,"
                + "age text null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
