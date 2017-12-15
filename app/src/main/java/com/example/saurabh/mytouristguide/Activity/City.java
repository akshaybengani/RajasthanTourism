package com.example.saurabh.mytouristguide.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.saurabh.mytouristguide.Adapter.TouristAdapter;
import com.example.saurabh.mytouristguide.Pojo.TouristPojo;
import com.example.saurabh.mytouristguide.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class City extends AppCompatActivity {

    ListView listView;
    ArrayList<TouristPojo> arrayList = new ArrayList<>();
    TouristAdapter adapter;
    String which;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        which = getIntent().getStringExtra("which");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(which);
        init();
        adapter = new TouristAdapter(this, R.layout.list_view, arrayList);
        listView.setAdapter(adapter);
        fetchDataFromServer(which);
    }


    private void init() {

        listView = (ListView) findViewById(R.id.dialogList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String place = arrayList.get(position).getName();
                String link = arrayList.get(position).getLink();
                String image = arrayList.get(position).getImage();
                double lat = arrayList.get(position).getLat();
                double lon = arrayList.get(position).getLon();

                Intent i = new Intent(City.this, Display.class);
                i.putExtra("link", link);
                i.putExtra("image", image);
                i.putExtra("lat", lat);
                i.putExtra("lon", lon);
                i.putExtra("place", place);
                startActivity(i);

            }

        });
    }

    private void fetchDataFromServer(String which) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading");
        progressDialog.show();
        progressDialog.setCancelable(false);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref1, ref2, ref3;
        ref1 = firebaseDatabase.getReference();
        ref2 = ref1.child("City");
        ref3 = ref2.child(which);
        ref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String id = childSnapshot.getKey();
                    TouristPojo pojo = childSnapshot.getValue(TouristPojo.class);
                    pojo.setId(id);
                    Log.d("1234", pojo.getId());
                    Log.d("pojo", pojo.toString());

                    arrayList.add(pojo);
                }
                adapter.notifyDataSetChanged();
                progressDialog.cancel();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //  Toast.makeText(Dashboard.this, "ERROR1", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
