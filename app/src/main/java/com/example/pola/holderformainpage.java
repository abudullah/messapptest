package com.example.pola;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class holderformainpage extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView mainpageadress,mainpageprize;
    ImageView imageViewformainpage;


    public holderformainpage(@NonNull View itemView) {

        super(itemView);
        mainpageadress=itemView.findViewById(R.id.mainpageaddress);
        mainpageprize=itemView.findViewById(R.id.mainpageprize) ;
        imageViewformainpage=itemView.findViewById(R.id.imageviewformainpage);
        itemView.setOnClickListener(this);


        }



    @Override
    public void onClick(View v) {


    }
}
