package com.example.saurabh.mytouristguide.Activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.saurabh.mytouristguide.R;
import com.example.saurabh.mytouristguide.helper.DownloadHelper;

public class Display extends AppCompatActivity {

    ImageView imageView;
    Button btn1, btn2;
    String link, image;
    double lat, lon;
    String place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        place = getIntent().getStringExtra("place");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(place);
        init();


        link = getIntent().getStringExtra("link");
        image = getIntent().getStringExtra("image");
        lat = getIntent().getDoubleExtra("lat", 0.00);
        lon = getIntent().getDoubleExtra("lon", 0.00);
        methodListener();
    }

    private void init() {

        imageView = (ImageView) findViewById(R.id.img);
        btn1 = (Button) findViewById(R.id.info);
        btn2 = (Button) findViewById(R.id.direction);

    }

    private void methodListener() {

        DownloadHelper.loadImageWithGlideURL(this, image, imageView);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Display.this, HTMLDisplay.class);
                i.putExtra("link", link);
                i.putExtra("place",place);
                startActivity(i);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + lat + "," + lon);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                try {
                    startActivity(mapIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(Display.this, "No application found to locate", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
