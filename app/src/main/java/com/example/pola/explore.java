package com.example.pola;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class explore extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
   private ArrayList<homerentdepcription>arraylistforfirstpage;
   private RecyclerView recyclerView;
   private RecyclerView.LayoutManager layoutManager;
   private DatabaseReference productsref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        recyclerView=findViewById(R.id.recyclerviewformain);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

       arraylistforfirstpage=new ArrayList<homerentdepcription>();
       productsref= FirebaseDatabase.getInstance().getReference().child("allpost");
       if(productsref!=null) {
           show("" + productsref.getPath());
       }





        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "please add your addvertise", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(explore.this, addnewmess.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView userName = headerView.findViewById(R.id.nameforheader);
        TextView UserEmail = headerView.findViewById(R.id.nameforemail);
        ImageView profile = headerView.findViewById(R.id.imageView);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.explore, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    void getdataformserver() {


    }
  private void  show(String massage)
  {
      AlertDialog.Builder a=new AlertDialog.Builder(this);
      a.setMessage(massage);
      a.setCancelable(true);
      a.show();
  }
    @Override
    protected void onStart()
    {
        super.onStart();
        try {

            FirebaseRecyclerOptions<homerentdepcription> options =
                    new FirebaseRecyclerOptions.Builder<homerentdepcription>()
                            .setQuery(productsref, homerentdepcription.class)
                            .build();


            FirebaseRecyclerAdapter<homerentdepcription, holderformainpage> adapter =
                    new FirebaseRecyclerAdapter<homerentdepcription, holderformainpage>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull holderformainpage holder, int position, @NonNull final homerentdepcription model) {
                            holder.mainpageadress.setText(model.getHomerent());
                            holder.mainpageprize.setText(model.getHomelocation());
                            try {
                                Picasso.with(explore.this).load(model.getURL()).into(holder.imageViewformainpage);
                            } catch (Exception e) {
                                show("" + e);
                            }


                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                        }

                        @NonNull
                        @Override
                        public holderformainpage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.addingtofirebasemainpage, parent, false);
                            holderformainpage holder = new holderformainpage(view);
                            return holder;
                        }
                    };
            recyclerView.setAdapter(adapter);
            adapter.startListening();
        }catch (Exception e)
        {
            show("Exception e"+e);
        }
    }






}







