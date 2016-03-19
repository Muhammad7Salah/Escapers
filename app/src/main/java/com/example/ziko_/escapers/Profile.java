package com.example.ziko_.escapers;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    TextView name;
    TextView email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle b = getIntent().getExtras();
        int id = b.getInt("id");
        String google_name=b.getString("name");
        String google_email=b.getString("email");
        name=(TextView) findViewById(R.id.Profile_name);
        email=(TextView) findViewById(R.id.Profile_email);
        name.setText(google_name);
        email.setText(google_email);

        
    }

}
