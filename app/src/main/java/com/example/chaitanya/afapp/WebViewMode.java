package com.example.chaitanya.afapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebViewMode extends AppCompatActivity {

    //web view initialization
    WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view);

        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());

        Bundle intent = getIntent().getExtras();

        //displaying the url in the web view according to the click event.
        webView.loadUrl(intent.getString("url"));
        webView.loadUrl(intent.getString("url1"));
    }
}
