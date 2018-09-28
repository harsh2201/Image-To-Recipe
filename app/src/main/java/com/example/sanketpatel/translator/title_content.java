package com.example.sanketpatel.translator;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gospelware.liquidbutton.LiquidButton;

import static com.example.sanketpatel.translator.MainActivity.mDatabase;

public class title_content extends AppCompatActivity {
    LiquidButton liquidButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title_content);
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_product_layout, null);

        final EditText nameField = (EditText) subView.findViewById(R.id.enter_name);

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("Add new product");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("ADD PRODUCT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                //          final int quantity = Integer.parseInt(quantityField.getText().toString());

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(title_content.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    String uri = "";
                    try {
                        uri = getIntent().getStringExtra("uri");
                    } catch (Exception e) {
                    }
                    Product newProduct = new Product(name, MainActivity.detectedTextView.getText().toString());
                    Log.i("Infoo", newProduct.toString());
                    newProduct.setUri(uri);
                    mDatabase.addProduct(newProduct);
                   //  Toast.makeText(title_content.this,"Text Recognization In Progress",Toast.LENGTH_LONG).show();
                    //refresh the activity
                    SmartyToast.makeText(getApplicationContext(),"Updating...",SmartyToast.LENGTH_SHORT,SmartyToast.UPDATE);
                   
                    liquidButton.startPour();


                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(title_content.this, "Task cancelled", Toast.LENGTH_LONG).show();
                finish();
            }
        });
        builder.show();

        liquidButton = (LiquidButton) findViewById(R.id.button);


//        liquidButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                LiquidButton btn = (LiquidButton) v;
//                btn.startPour();
//            }
//        });
        liquidButton.setFillAfter(true);
        liquidButton.setAutoPlay(true);
        liquidButton.setPourFinishListener(new LiquidButton.PourFinishListener() {
            @Override
            public void onPourFinish() {

                Toast.makeText(title_content.this, "Task Successfull", Toast.LENGTH_LONG).show();
                Intent i = new Intent(title_content.this, MainActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onProgressUpdate(float progress) {
                // textView.setText(String.format("%.2f", progress * 100) + "%");

                liquidButton.changeProgress(progress);
                liquidButton.finishPour();
            }
        });


        //Liquid Button


    }
}
