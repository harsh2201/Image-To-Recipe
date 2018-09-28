package com.example.sanketpatel.translator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanketpatel.translator.Utils.ViewUtils;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.features_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.copy:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("brij", product.getQuantity());
                clipboard.setPrimaryClip(clip);

                Toast.makeText(getApplicationContext(),"Copied to Clipboard",Toast.LENGTH_LONG).show();
                return true;

            case R.id.share:
                String shareBody = product.getQuantity();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
