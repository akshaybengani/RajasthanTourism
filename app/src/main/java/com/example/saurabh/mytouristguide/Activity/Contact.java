package com.example.saurabh.mytouristguide.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.saurabh.mytouristguide.R;

public class Contact extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Contact Us");
    }
}
