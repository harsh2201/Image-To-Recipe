package com.example.sanketpatel.translator;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductViewHolder extends RecyclerView.ViewHolder {

    public View layout;

    public TextView name;
    public TextView desc;

    public ImageView deleteProduct;
    public ImageView editProduct;

    public ProductViewHolder(View itemView) {
        super(itemView);
        layout = itemView;
        name = (TextView)itemView.findViewById(R.id.product_name);
        desc = (TextView)itemView.findViewById(R.id.product_desc);
        //deleteProduct = (ImageView)itemView.findViewById(R.id.delete_product);
        //editProduct = (ImageView)itemView.findViewById(R.id.edit_product);
    }
}
