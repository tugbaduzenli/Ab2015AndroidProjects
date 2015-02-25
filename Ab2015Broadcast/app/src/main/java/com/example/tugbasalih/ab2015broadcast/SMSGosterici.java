package com.example.tugbasalih.ab2015broadcast;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class SMSGosterici extends ActionBarActivity {

    TextView smsText,phoneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsgosterici);

        String telNo = getIntent().getStringExtra("telNo");
        String smsMesaji = getIntent().getStringExtra("smsMesaji");

        smsText=(TextView)findViewById(R.id.smsTxt);
        phoneText=(TextView)findViewById(R.id.phoneTxt);
        smsText.setText(smsMesaji);
        phoneText.setText(telNo);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_smsgosterici, menu);
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
