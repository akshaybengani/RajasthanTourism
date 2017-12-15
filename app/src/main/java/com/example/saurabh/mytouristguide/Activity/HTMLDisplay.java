package com.example.saurabh.mytouristguide.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.saurabh.mytouristguide.R;


public class HTMLDisplay extends AppCompatActivity {

    private WebView webView;
    String place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_htmldisplay);
        place = getIntent().getStringExtra("place");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(place);
        init();

        String link = getIntent().getStringExtra("link");


        if (link != null) {

            webView.loadUrl(link);

            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setAppCacheEnabled(true);
            webView.getSettings().setLoadsImagesAutomatically(true);



            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return false;
                }
            });
        }
    }

    private void init() {
        webView = (WebView) findViewById(R.id.webView);
    }
}

