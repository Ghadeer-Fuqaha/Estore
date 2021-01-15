package com.example.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.exam.R;
import com.example.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;



public class ProductAdapter extends ArrayAdapter<Product> {

    //the hero list that will be displayed
    private List<Product> productList;

    //the context object
    private Context mCtx;

    //here we are getting the herolist and context
    //so while creating the object of this adapter class we need to give productlist and context
    public ProductAdapter(List<Product> productlist, Context mCtx) {
        super(mCtx, R.layout.list_item, productlist);
        this.productList = productlist;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View row = inflater.inflate(R.layout.list_item, null, true);

        //getting text views
        TextView ProductName= row.findViewById(R.id.productName);
        TextView ProductPrice = row.findViewById(R.id.productPrice);
        TextView ProductDesc = row.findViewById(R.id.textDescription);


        //Getting the hero for the specified position
        Product product = productList.get(position);

        //setting hero values to textviews
        ProductName.setText(product.getName());
        ProductPrice.setText(product.getPrice()+" JOD");
        ProductDesc.setText(product.getDescription());



        ImageView imgView = (ImageView) row.findViewById(R.id.imageView);
        String url = "http://10.0.2.2/ElectronicStore/uploads/" + product.getImgPath();

        //Loading image using Picasso
        Picasso.get().load(url).into(imgView);


        //returning the listitem
        return row;
    }
}
