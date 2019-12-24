package com.example.pola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class forgotpassword extends AppCompatActivity implements View.OnClickListener{
    private EditText mail,password;
    Button  forgot;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseAuth auth;
    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        mail=findViewById(R.id.emailtoforgot);
        password=findViewById(R.id.passwordforgot);
        forgot=findViewById(R.id.forgot);
        forgot.setOnClickListener(this);
        auth=FirebaseAuth.getInstance();



    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.forgot)
        {
            String ma,pass;
            ma=mail.getText().toString();
            pass=password.getText().toString();
            FirebaseUser currentUser = auth.getCurrentUser();
            if (TextUtils.isEmpty(ma))
            {
                Toast.makeText(this, "Please write your email number...", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(pass))
            {
                Toast.makeText(this, "Please write your email number...", Toast.LENGTH_SHORT).show();
            }

            if(currentUser!=null) {
                updatepassword(pass);
                sendEmail(ma);
            }

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

                        }
                    }
                });

    }
    void sendEmail(String emailAddress)
    {auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        loadingBar.dismiss();
                    }
                }
            });

    }

}
