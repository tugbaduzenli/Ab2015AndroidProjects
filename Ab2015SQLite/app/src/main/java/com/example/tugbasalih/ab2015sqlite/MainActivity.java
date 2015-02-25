package com.example.tugbasalih.ab2015sqlite;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadCustomers();

    }

    private void loadCustomers(){
        DBHelper helper = new DBHelper(this);
        List<Customer> customers = helper.getAllCustomers();

        String[] strCustomers = new String[customers.size()];

        for (int i=0 ; i<customers.size(); i++){
            strCustomers[i] = customers.get(i).toString();
        }

        ListView lvCustomer = (ListView)findViewById(R.id.lvCustomer);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,strCustomers);

        lvCustomer.setAdapter(adapter);

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

    public void saveCustomer(View view) {

        EditText etName = (EditText)findViewById(R.id.etName);
        EditText etAge = (EditText)findViewById(R.id.etAge);

        if (!etName.getText().toString().equals("")){

            Customer c = new Customer();
            c.setName(etName.getText().toString().trim()); // trim sağdan soldan boşlukları kaldırır.
            c.setAge(etAge.getText().toString());

            DBHelper helper = new DBHelper(MainActivity.this);
            long id = helper.insertCustomer(c);
            c.setId(id);

            Toast.makeText(this,"Eklenen müşteri numarası:" + c.getId(),Toast.LENGTH_SHORT).show();

            loadCustomers();
        }
    }
}
