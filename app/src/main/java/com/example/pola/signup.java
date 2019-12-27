package com.example.pola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signup extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText name,password,Email,phonenumber;
    private ProgressDialog loadingBar;
   private Button signup;
   private DatabaseReference myRef;
   private String phonumber,nam;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name=findViewById(R.id.name);
        password=findViewById(R.id.password);
        Email=findViewById(R.id.email);
        signup=findViewById(R.id.tosignup);
        signup.setOnClickListener(this);
        phonenumber=findViewById(R.id.phonenumber);
       // persmission(Manifest.permission.ACCESS_NETWORK_STATE);


        // Initialize Firebase Auth
        try {
            myRef = database.getReference("mess");
        }catch(Exception e)
        {
            show("fuck you"+e);
        }

        mAuth = FirebaseAuth.getInstance();

        loadingBar = new ProgressDialog(this);

    }
    void signupuser(String email,String password) {
        loadingBar.setTitle("Sign up Account");
        loadingBar.setMessage("Please wait, while we are checking the credentials.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            loadingBar.dismiss();
                            HashMap<String,Object>datas=new HashMap<>();
                            datas.put("Name",nam);
                            datas.put("phone",phonumber);


                            FirebaseUser user = mAuth.getCurrentUser();
                            String userid=user.getUid();


                            myRef.child("user").child(userid).updateChildren(datas)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {



                                                //  loadingBar.dismiss();


                                            } else {

                                                String message = task.getException().toString();
                                                show("" + task.getException().toString());

                                            }
                                        }
                                    });


                            Intent intent = new Intent(signup.this, explore.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);


                        } else {
                            loadingBar.dismiss();
                            show("sorry email you put must be wrong");
                            // If sign in fails, display a message to the user.

                        }

                        // ...
                    }
                });
    }
  private  void show(String e)
    {
        AlertDialog.Builder a=new AlertDialog.Builder(this);
        a.setMessage(e);
        a.setCancelable(true);
        a.show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.tosignup)
        {
            String em,pass;
            nam=name.getText().toString().trim();
            em=Email.getText().toString().trim();
            pass=password.getText().toString().trim();
            phonumber=phonenumber.getText().toString().trim();
            if (TextUtils.isEmpty(em))
            {
                Toast.makeText(this, "Please write your email...", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(nam))
            {
                Toast.makeText(this, "Please write your  name...", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(pass))
            {
                Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
            }
            if(pass.length()<=6 )
            {
                Toast.makeText(this, "put password between greaterthan 6...", Toast.LENGTH_SHORT).show();
            }


              if(isNetworkAvailable())
              {
              signupuser(em,pass);
              }
              else{
                  show("please connect to internet");
              }

        }



        }
    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    }


