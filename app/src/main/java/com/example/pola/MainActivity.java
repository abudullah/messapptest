package com.example.pola;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
}
