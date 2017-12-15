package com.example.saurabh.mytouristguide.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.saurabh.mytouristguide.Pojo.UserPojo;
import com.example.saurabh.mytouristguide.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter adapter;
    String which;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
getSupportActionBar().setTitle("Rajasthan Tourism");
        navigationDrawerCreate(toolbar);
       emailVerification();
        init();
        adapter = new ArrayAdapter(this, R.layout.dialog_list_view, arrayList);
        listView.setAdapter(adapter);
        fetchDataFromServer();
    }

    private void navigationDrawerCreate(Toolbar toolbar) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        final TextView navName = (TextView) header.findViewById(R.id.nav_Name);
        final TextView navMail = (TextView) header.findViewById(R.id.nav_Mail);

        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final String Uid = currentFirebaseUser.getUid();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref1, ref2;
        ref1 = firebaseDatabase.getReference();
        ref2 = ref1.child("User");
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String id = childSnapshot.getKey();
                    if (Uid.equals(id)) {
                        UserPojo user = childSnapshot.getValue(UserPojo.class);

                        Log.d("890", "usr: " + user.getUsername());
                        navName.setText(user.getUsername());
                        navMail.setText(user.getEmailId());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_logout) {
            SharedPreferences preferences =
                    getSharedPreferences("MyFile", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(Dashboard.this, Login.class));
            finish();

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(Dashboard.this,About.class));

        } else if (id == R.id.nav_contact) {
            startActivity(new Intent(Dashboard.this,Contact.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void init() {

        listView = (ListView) findViewById(R.id.list);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                which = arrayList.get(position).toString();
                Log.d("12345", which);
                Intent i = new Intent(Dashboard.this, City.class);
                i.putExtra("which", which);
                startActivity(i);
            }
        });
    }


    private void fetchDataFromServer() {

        final ProgressDialog progressDialog = new ProgressDialog(Dashboard.this);
        progressDialog.setMessage("loading");
        progressDialog.show();
        progressDialog.setCancelable(false);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference("City");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String id = childSnapshot.getKey();
                    arrayList.add(id);
                    Log.d("123", "id:" + id);
                }
                adapter.notifyDataSetChanged();
                progressDialog.cancel();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Toast.makeText(Dashboard.this, "ERROR2", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void emailVerification() {

        FirebaseUser user = mAuth.getInstance().getCurrentUser();
        if(  user.isEmailVerified())
        {

        }
        else{
            SharedPreferences preferences =
                    getSharedPreferences("MyFile", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(Dashboard.this,Login.class));
            Toast.makeText(this, "verify your email", Toast.LENGTH_SHORT).show();
        }
    }




}
