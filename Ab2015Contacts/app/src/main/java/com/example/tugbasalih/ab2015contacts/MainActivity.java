package com.example.tugbasalih.ab2015contacts;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor cursor=
        getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
        int nameId = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

        TextView tv=(TextView)findViewById(R.id.tv);

        String isimler="";
        if(cursor.moveToNext()){
            do{
                isimler+=cursor.getString(nameId)+"\n";

            }while (cursor.moveToNext());
        }
        tv.setText(isimler);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
