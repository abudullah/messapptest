package com.example.pola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class forgotpassword extends AppCompatActivity implements View.OnClickListener{
    private EditText mail,password;
    Button  forgot;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth auth;
    private ProgressDialog loadingBar;
    String ma,pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        mail=findViewById(R.id.emailtoforgot);
        password=findViewById(R.id.passwordforgot);
        forgot=findViewById(R.id.forgot);
        forgot.setOnClickListener(this);
        auth=FirebaseAuth.getInstance();
        loadingBar=new ProgressDialog(this);



    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.forgot) {

            ma = mail.getText().toString().trim();
            pass = password.getText().toString().trim();
            FirebaseUser currentUser = auth.getCurrentUser();
            if (TextUtils.isEmpty(ma)) {
                Toast.makeText(this, "Please write your email number...", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(pass)) {
                Toast.makeText(this, "Please write your new pass", Toast.LENGTH_SHORT).show();
            }
            if (pass.length() < 6) {
                Toast.makeText(this, "password should be greater than 6", Toast.LENGTH_SHORT).show();
            }


            if (isNetworkAvailable()) {
                sendEmail(pass);
            } else {
                Toast.makeText(this, "please connect to your network...", Toast.LENGTH_SHORT).show();

            }
            // sendEmail(ma);

        }




    }
    void updatepassword (String newPassword)
    {
        loadingBar.setTitle("changing password");
        loadingBar.setMessage("Please wait, while we are checking the credentials.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            loadingBar.dismiss();
                            sendEmail(ma);


                        }
                    }
                });
        user.updatePassword(newPassword).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                show("fial to update"+e);
                loadingBar.dismiss();

            }
        });

    }
    void sendEmail(String emailAddress)
    {auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                    }

                }
            });
        auth.sendPasswordResetEmail(emailAddress).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                show("fail to send email"+e);
                loadingBar.dismiss();
            }
        });


    }
    void show(String e)
    {
        AlertDialog.Builder a=new AlertDialog.Builder(this);
        a.setMessage(e);
        a.setCancelable(true);
        a.show();
    }
    private boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
