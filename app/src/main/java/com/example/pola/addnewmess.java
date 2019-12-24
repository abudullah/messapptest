package com.example.pola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class addnewmess extends AppCompatActivity implements View.OnClickListener {

ImageView homeimage,homeimage1;
String saveCurrentDate, saveCurrentTime;
  private  EditText title,homedescription,homelocation,homerent;
 private   Button addnewhomeimage;
    FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;

 int GalleryPick=1;
 private Uri ImageUri;

    private StorageReference homerentstorage;
    private ProgressDialog loadingBar;
    String downloadImageUrl;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference mess = database.getReference("mess");
    DatabaseReference usertodelete=mess.child("usertodelete");
    DatabaseReference division=mess.child("location");
    DatabaseReference searchbydate=mess.child("searchbydate");






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewmess);
        homeimage=findViewById(R.id.home_image);


        title=findViewById(R.id.titleforrent);
        homedescription=findViewById(R.id.homedescription);
        homelocation=findViewById(R.id.home_location);
        homerent=findViewById(R.id.home_rent);
        addnewhomeimage=findViewById(R.id.addnewhomeimage);
        addnewhomeimage.setOnClickListener(this);
        homeimage.setOnClickListener(this);
        loadingBar=new ProgressDialog(this);
        homerentstorage = FirebaseStorage.getInstance().getReference();
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

    }
    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
           ImageUri = data.getData();
           homeimage.setImageURI(ImageUri);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.home_image)
        {
            OpenGallery();
        }
        if(v.getId()==R.id.addnewhomeimage)
        {

            StoreProductInformation();
            try {
                SaveProductInfoToDatabase();
            }catch(Exception e)
            {
                show(""+e);
            }

        }


    }
    private void StoreProductInformation()
    {
        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Dear Admin, please wait while we are adding the new product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();




        final StorageReference filePath = homerentstorage.child(ImageUri.getLastPathSegment() + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                show("fuck you");

                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(addnewmess.this, "Add have been added...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            show(""+task.getException().getMessage().toString());
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(addnewmess.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }
    private void SaveProductInfoToDatabase()
    {  final String ti,homedes,homloca,hom;

        ti=title.getText().toString();
        homedes=homedescription.getText().toString();
        homloca=homelocation.getText().toString();
        hom=homerent.getText().toString();
        String uid = currentFirebaseUser.getUid().toString();
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("title", ti);
        productMap.put("homedescription",homedes );
        productMap.put("homelocation", homloca);
        productMap.put("homerent", hom);
        productMap.put("uid",uid);
        productMap.put("URL", downloadImageUrl);
       productMap.put("Currenttime", saveCurrentTime);
        productMap.put("currentdate", saveCurrentDate);

if(currentFirebaseUser!=null) {
    String hell = currentFirebaseUser.getUid().toString();
    usertodelete.child(hell).push().updateChildren(productMap)
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


    division.child(productMap.get("homelocation").toString()).push().updateChildren(productMap)
            .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {


                        loadingBar.dismiss();
                        show("fuck successfull for division");

                    } else {
                        loadingBar.dismiss();
                        String message = task.getException().toString();
                        show("" + task.getException().toString());
                    }
                }
            });



}else{
    show("this user is not logged in");
}
    }
    void show(String s)
    {
        AlertDialog.Builder d=new AlertDialog.Builder(this);
        d.setMessage(s);
        d.setCancelable(true);
        d.show();
    }
}
