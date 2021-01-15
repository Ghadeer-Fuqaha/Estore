package com.example.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class show_item extends AppCompatActivity {

    String name,price,description,imgURL;
    TextView PName,PPrice,PDesc;
    int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);

        Intent intent = this.getIntent();
        Bundle extras = getIntent().getExtras();
        name = intent.getStringExtra("Name");
        price = intent.getStringExtra("Price");
        description = intent.getStringExtra("Description");
        imgURL = intent.getStringExtra("Image");








        PName = (TextView) findViewById(R.id.productName2);
        PPrice = (TextView) findViewById(R.id.productPrice2);
        PDesc = (TextView) findViewById(R.id.textDescription2);


        PName.setText(name);
        PPrice.setText(price+" JOD");
        PDesc.setText(description);


        ImageView image = (ImageView) findViewById(R.id.imageView2);

        Picasso.get().load(imgURL).into(image);



    }
}