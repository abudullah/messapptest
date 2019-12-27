package com.example.pola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class login extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText password,email;
    private Button  button;
    private ProgressDialog loadingBar;
    TextView logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        password=findViewById(R.id.passwordtologin);
        email=findViewById(R.id.emailtologin);
        button=findViewById(R.id.loginto);
        button.setOnClickListener(this);
        loadingBar = new ProgressDialog(this);
        logout=findViewById(R.id.logoutplease);
        logout.setOnClickListener(this);




        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }
    void login(String email, String password)
    {
        loadingBar.setTitle("Login Account");
        loadingBar.setMessage("Please wait, while we are checking the credentials.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            loadingBar.dismiss();

                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(login.this, explore.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);



                        } else {
                            loadingBar.dismiss();
                            // If sign in fails, display a message to the user.




                        }

                        // ...
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.loginto)

        {
            String em=email.getText().toString();
            String pas=password.getText().toString();
            if (TextUtils.isEmpty(em))
            {
                Toast.makeText(this, "Please write your email number...", Toast.LENGTH_SHORT).show();
            }
            if (TextUtils.isEmpty(pas))
            {
                Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
            }
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(isNetworkAvailable()) {
                if (currentUser == null) {
                    login(em, pas);
                }
                else{
                    Toast.makeText(this, "log out first please", Toast.LENGTH_SHORT).show();

                }
            }
            else {
                Toast.makeText(this, "connect to internet please ", Toast.LENGTH_SHORT).show();
            }

        }

        if(v.getId()==R.id.logoutplease)
        {FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser!=null) {  loadingBar.setTitle("Logging out Account");
                loadingBar.setMessage("Please wait, while we logging out you....");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                FirebaseAuth.getInstance().signOut();
            }
            if(currentUser==null)
            {  loadingBar.dismiss();
                Toast.makeText(this, "successfully logged-out", Toast.LENGTH_SHORT).show();

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
