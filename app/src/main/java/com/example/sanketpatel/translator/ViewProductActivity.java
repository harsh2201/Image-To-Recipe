package com.example.sanketpatel.translator;

import android.graphics.Color;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.sanketpatel.translator.Utils.ViewUtils;

import java.util.Objects;

public class ViewProductActivity extends AppCompatActivity {
    Product product;
    ViewPager viewPager;
    Button showImageButton, showTextButton;
    TextView textView;
    ImageView imageView;
    ScrollView scrollView;

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

        textView = findViewById(R.id.viewProduct_text);
        imageView = findViewById(R.id.viewProduct_imageView);

        try {
            imageView.setImageURI(Uri.parse(product.getUri()));
        } catch (Exception e) {
        }

        scrollView = findViewById(R.id.viewProduct_scrollView);
        textView.setText(product.getQuantity());
        showImageButton = findViewById(R.id.viewProduct_showImageButton);
        showTextButton = findViewById(R.id.viewProduct_showTextButton);
        showTextButton.setTextColor(Color.GRAY);

        //init();

    }

    public void onShowTextButtonClick(View view) {
        ViewUtils.setVisibilityGone(imageView);
        ViewUtils.setVisible(scrollView);
        ViewUtils.setEnable(showImageButton);
        ViewUtils.setDisable(view);
        showImageButton.setTextColor(Color.GRAY);
        ((Button)view).setTextColor(Color.WHITE);
    }

    public void onShowImageButtonClick(View view) {
        ViewUtils.setVisibilityGone(scrollView);
        ViewUtils.setVisible(imageView);
        ViewUtils.setEnable(showTextButton);
        ViewUtils.setDisable(view);
        showTextButton.setTextColor(Color.GRAY);
        ((Button)view).setTextColor(Color.WHITE);
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
