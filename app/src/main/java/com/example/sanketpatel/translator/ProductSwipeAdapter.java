package com.example.sanketpatel.translator;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sanketpatel.translator.Utils.ViewUtils;

/**
 * translator
 * Created by Yash on 9/28/2018.
 */
public class ProductSwipeAdapter extends PagerAdapter {

    Context context;
    Product product;

    ProductSwipeAdapter(Context context, Product product) {
        this.product = product;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;
        if (position == 1) {
            ImageView imageView = new ImageView(context);
            if (!TextUtils.isEmpty(product.getUri())) {
                try {
                    Log.i("Infoo_img", product.getUri());
                    imageView.setImageURI(Uri.parse(product.getUri()));
                } catch (Exception e) {
                    imageView.setImageResource(R.drawable.img_broken_image);
                    e.printStackTrace();
                    Log.i("Infoo", "Image error " + e.getMessage());
                }
            }
            view = imageView;
        } else {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = (inflater).inflate(R.layout.layout_textview, container, false);
            TextView textView = layout.findViewById(R.id.viewProduct_text);
            textView.setText(product.getQuantity());
            view = layout;
        }
        container.addView(view);
        return view;
    }
}
