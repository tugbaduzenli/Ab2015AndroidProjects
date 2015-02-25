package com.example.tugbasalih.ab2015thread;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn =(Button)findViewById(R.id.btn);

    }

    public void showThread(View v){

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 1000; i++){
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                setStatus(R.id.tvT1, i);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 1000; i++){
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    setStatus(R.id.tvT2, i);
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 1000; i++){
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    setStatus(R.id.tvT3, i);
                }
            }
        }).start();
    }


    private void setStatus(final int id, final int i){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView)findViewById(id)).setText(String.valueOf(i));
            }
        });
    }

    public void starTask(View view) {

        MyTask task =new MyTask();
        task.execute("");
        //task.cancel(true);

    }

    class MyTask extends AsyncTask<String, Integer, Integer>{ //1. doınbacgrounda giden 2. doınbackground döndürür 3. onpostexe ye gider

        ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setTitle("Please wait....");
            dialog.setMessage("progress...");
            dialog.setCancelable(true);

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    MyTask.this.cancel(true);
                }
            });
            dialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            ((TextView)findViewById(R.id.tvT2)).setText(String.valueOf(values[0]));

        }

        @Override
        protected Integer doInBackground(String... params) {

            for (int i=0; i<100; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i);

            }
           return 5000;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

            if(dialog.isShowing()){
                dialog.dismiss();
            }

        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            dialog.dismiss();

            ((TextView)findViewById(R.id.tvT1)).setText(String.valueOf(integer));

        }
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
