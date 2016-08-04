package com.example.chaitanya.afapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    //image view for setting the images from the json
    ImageView imageView, imageView1;

    //text view for setting the title from the json
    TextView textView, textView1;

    //promotions initialization for the first object inside in json, parcelabler class
    Promotions promo;

    //promotions initialization for the second object inside in json, parcelabler class
    Promotions promo2;

    //boolean variable for checking the internet connection
    boolean connection;

    //database class initialization for getting the data from database when there is no internet connection
    DatabaseData databaseData;

    //arraylist for the data from the database
    ArrayList<String> data;

    //onCreate initialization
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //checking the internet connection
        NetworkConnection networkConnection = new NetworkConnection();
        connection = networkConnection.isConnectionAvailable(this);
        databaseData = new DatabaseData(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        imageView = (ImageView) findViewById(R.id.imageView);
        imageView1 = (ImageView) findViewById(R.id.imageView1);
        textView = (TextView) findViewById(R.id.textView);
        textView1 = (TextView) findViewById(R.id.textView1);

        /* if internet connection is true call the downloader class which extends AsyncTask class,
           else get the title from the database and display it on the screen
         */
        if (connection){
            Log.d("Connection"," TRUE");
            Downloader downloader = new Downloader(this);
            downloader.execute();
            clickUI();
        }else {
            Log.d("Connection","FALSE");
            data = databaseData.getAllData();
            Log.d("DatabaseSize", "" + data.size());
            if(data.size() > 0) {
                for (String name : data) {
                    System.out.println("" + name);
                }
                textView.setText(data.get(0));
                textView1.setText(data.get(1));
            }
            /*display the images from the drawable folder initially
            and the display it on the screen first time
            when the user opens the app witout internet connection
             */
            Glide.with(this).load(R.drawable.womenshorts).override(250, 250).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            Glide.with(this).load(R.drawable.womenbrands).override(250, 250).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView1);

            /*Display a toast message to the user for checking the internet connection
            when the user is not connected to the internet */
            Toast toast = Toast.makeText(MainActivity.this, "Check your Connection!!!! Thank you.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }


    }

    /*click listeners for both the image views and text views to start the next activity
     when user clicks on any of the views while there is internet connection
      */
    public void clickUI() {

        //click event when user clicks on the first row
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PromoCard.class);
                intent.putExtra("promo", "first");
                intent.putExtra("promotion1", promo);
                startActivity(intent);
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PromoCard.class);
                intent.putExtra("promo", "first");
                intent.putExtra("promotion1", promo);
                startActivity(intent);
            }
        });

        //click event when user clicks on the second row
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, PromoCard.class);
                intent1.putExtra("promo", "second");
                intent1.putExtra("promotion2", promo2);
                startActivity(intent1);
            }
        });
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, PromoCard.class);
                intent1.putExtra("promo", "second");
                intent1.putExtra("promotion2", promo2);
                startActivity(intent1);
            }
        });
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

    /* Method to display the title and images from the json.
    This method is called from the Downloader class passing the arraylist
    which we get from the json.
     */
    public void sendPromotions(ArrayList arrayList) {

        //get the arraylist of the data from the parcelabler
        promo = (Promotions) arrayList.get(0);
        promo2 = (Promotions) arrayList.get(1);

        //image displayed in table layout from json
        Glide.with(this).load(promo.image).override(250, 250).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        Glide.with(this).load(promo2.image).override(250, 250).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView1);

        //get the number of rows from the database
        int rows = databaseData.numberOfRows();

        /* if there is no data inside database then
        call the method to insert the data in the database
         */
        if (rows <= 0) {

            if (databaseData.insertDataToBeSaved(promo.title, promo.description, promo.footer, promo.image, promo.businessURL))
                Log.d("DATA_", "INSERTED_1");

            if (databaseData.insertDataToBeSaved(promo2.title, promo2.description, promo2.footer, promo2.image, promo2.businessURL))
                Log.d("DATA_", "INSERTED_2");

            //image displayed in table layout from json
            textView.setText(promo.title);
            textView1.setText(promo2.title);

        }
    }
}
