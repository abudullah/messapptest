package com.example.pola;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button signup ,login,logout,forgotten;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       signup= findViewById(R.id.signup);
       login=findViewById(R.id.login);
       signup.setOnClickListener(this);
        login.setOnClickListener(this);
        logout= findViewById(R.id.presstoExplore);
        forgotten= findViewById(R.id.forgotpassword);
        logout.setOnClickListener(this);
        forgotten.setOnClickListener(this);
        persmission(Manifest.permission.ACCESS_NETWORK_STATE);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.signup)
        {
            Intent intent=new Intent (this,signup.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        if(v.getId()==R.id.login)
        {
            Intent intent=new Intent (this,login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        if(v.getId()==R.id.presstoExplore)
        {
            Intent intent=new Intent (this,explore.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        if(v.getId()==R.id.forgotpassword)
        {
            Intent intent=new Intent (this,forgotpassword.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }


    }
    void persmission (String  permissi)
    {

        if (ContextCompat.checkSelfPermission(this,permissi
        )
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    permissi)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{permissi},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }
}
