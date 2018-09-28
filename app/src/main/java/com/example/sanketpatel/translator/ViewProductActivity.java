package com.example.sanketpatel.translator;

import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class ViewProductActivity extends AppCompatActivity {
    Product product;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product);
        product = (Product) getIntent().getSerializableExtra("product");
        setTitle(product.getName());
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        } catch (Exception e) {
        }
        try {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
        }

        TextView textView = this.findViewById(R.id.viewProduct_text);
        ImageView imageView = findViewById(R.id.viewProduct_imageView);

        try {
            imageView.setImageURI(Uri.parse(product.getUri()));
        } catch (Exception e) {
        }

        textView.setText(product.getQuantity());

        //init();

    }

    // Swipe layout
    //void init() {
    //    viewPager = findViewById(R.id.viewProduct_viewPager);
    //
    //    ProductSwipeAdapter adapter = new ProductSwipeAdapter(this, product);
    //    viewPager.setAdapter(adapter);
    //
    //
    //
    //}
}
