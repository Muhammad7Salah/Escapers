package com.example.ziko_.escapers;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Welcome extends AppCompatActivity {

    final Dialog dialog =new Dialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void validate_login(View view) {

        dialog.setContentView(R.layout.login_dialog);
        dialog.show();
    }
    public void signup(View view) {

        dialog.setContentView(R.layout.signup_dialog);
        dialog.show();
    }

    public void forgotPassword(View view) {
    }

}
