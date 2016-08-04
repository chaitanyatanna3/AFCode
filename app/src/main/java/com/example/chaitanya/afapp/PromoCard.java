package com.example.chaitanya.afapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;


public class PromoCard extends AppCompatActivity {

    //image view to display the image
    ImageView imageView;

    //text view to display title, description and footer
    TextView textViewTitle, textViewDescription, textViewFooter;

    //button to the take teh user to the webview
    Button buttonURL;

    //database initialization
    DatabaseData databaseData;

    //onCreate initialization
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_view_promotion);

        imageView = (ImageView) findViewById(R.id.imageDetail);
        textViewTitle = (TextView) findViewById(R.id.textTitle);
        textViewDescription = (TextView) findViewById(R.id.textDescription);
        textViewFooter = (TextView) findViewById(R.id.textFooter);
        buttonURL = (Button) findViewById(R.id.btn_webview);
        databaseData = new DatabaseData(this);

        //get the whole parcelaber class from the main activity using intent
        Intent intent = getIntent();
        Log.d("HELLO WORLD", intent.getExtras().getString("promo") );

        //display the data according to click event
        if(intent.getExtras().getString("promo").equals("first")) {
            Promotions promotion1 = (Promotions) intent.getParcelableExtra("promotion1");
            String title = promotion1.title;
            String desc = promotion1.description;
            String footer = promotion1.footer;
            String image = promotion1.image;
            final String url = promotion1.businessURL;

            insertData(title, desc, footer, image, url);

            if (!databaseData.checkIsDataAlreadyInDBorNot(title)) {

                databaseData.insertDataToBeSaved(title, desc, footer, image, url);
            }

            Glide.with(this).load(image).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            textViewTitle.setText(title);
            textViewDescription.setText(desc);
            textViewFooter.setText(footer);
            buttonURL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentButton = new Intent(PromoCard.this, WebViewMode.class);
                    intentButton.putExtra("url", url);
                    startActivity(intentButton);
                }
            });

        }else {
            Promotions promotion2 = (Promotions) intent.getParcelableExtra("promotion2");
            String title1 = promotion2.title;
            String desc1 = promotion2.description;
            String footer1 = promotion2.footer;
            String image1 = promotion2.image;
            final String url1 = promotion2.businessURL;

            insertData(title1, desc1, footer1, image1, url1);

            if (!databaseData.checkIsDataAlreadyInDBorNot(title1)) {

                databaseData.insertDataToBeSaved(title1, desc1, footer1, image1, url1);
            }
            Glide.with(this).load(image1).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
            textViewTitle.setText(title1);
            textViewDescription.setText(desc1);
            textViewFooter.setText(footer1);
            buttonURL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentButton = new Intent(PromoCard.this, WebViewMode.class);
                    intentButton.putExtra("url1", url1);
                    startActivity(intentButton);
                }
            });
        }

    }

    //inserting the data from the database
    public void insertData(String title, String description, String footer, String image, String url) {
        if (databaseData.insertDataToBeSaved(title, description, footer, image, url)){

            Log.d("HELLO", title);
        }
    }
}
