package com.example.ziko_.escapers;

//updated
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
    }

    public void login(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.login_dialog);
        dialog.show();
    }
    public void signup(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.login_dialog);
        dialog.show();
    }

    public void forgotPassword(View view) {
    }

    public void validate_login(View view) {
    }
}
