package com.example.tugbasalih.ab2015volley;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class MainActivity extends ActionBarActivity {

    public String html ="";
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView)findViewById(R.id.mTextView);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,"http://ab2015.anadolu.edu.tr/index.php?menu=8",new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                html=response;
                //mTextView.setText(html);

                Document document = Jsoup.parse(html);
                Element element = document.select("div.col-lg-12").first();

                Elements elements = element.getElementsByTag("a");

                String a= "";
                for ( Element el : elements){
                    a += el.html() +"\n";
                }

                mTextView.setText(a);




            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("Hata:" + error.getMessage());
            }
        });

        requestQueue.add(stringRequest);

        ImageLoader mImageLoader = VolleySingleton.getInstance(this).getImageLoader();
        final NetworkImageView logo = (NetworkImageView)findViewById(R.id.nivLogo);

        logo.setImageUrl("http://ab2015.anadolu.edu.tr/img/konf_ust_banner_v3.png",mImageLoader);



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
