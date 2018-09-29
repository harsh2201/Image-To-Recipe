package com.example.sanketpatel.translator;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    TextView titleEditText, detectedEditText;
    Product product;
    static SqliteDatabase database;

    boolean anyChanges = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        titleEditText = findViewById(R.id.edit_titleEditText);
        detectedEditText = findViewById(R.id.edit_detectedEditText);
        database = new SqliteDatabase(this);
        product = (Product) getIntent().getSerializableExtra("product");
        titleEditText.setText(product.getName());
        detectedEditText.setText(product.getQuantity());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        String name = "", text = "";
        if (!TextUtils.isEmpty(titleEditText.getText())) {
            name = titleEditText.getText().toString().trim();
        }
        if (!TextUtils.isEmpty(detectedEditText.getText())) {
            text = detectedEditText.getText().toString().trim();
        }

        if (!(name.equals(product.getName()) && text.equals(product.getQuantity()))) {
            anyChanges = true;
        } else {
            anyChanges = false;
        }


        if (anyChanges) {

            AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);

            builder.setTitle("Discard changes")
                    .setMessage("Are you sure you want to discard this entry?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            updateProduct();
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else {
            super.onBackPressed();
        }
    }

    private void updateProduct() {
        if (TextUtils.isEmpty(titleEditText.getText())) {
            Toast.makeText(getApplicationContext(), "Enter title", Toast.LENGTH_SHORT).show();
        } else {
            String name = titleEditText.getText().toString().trim();

            String text = "";
            if (!TextUtils.isEmpty(detectedEditText.getText())) {
                text = detectedEditText.getText().toString().trim();
            }
            product.setName(name);
            product.setQuantity(text);
            database.updateProduct(product);
            Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_save) {
            updateProduct();

        } else if (id == R.id.menu_discard) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
