package com.example.sanketpatel.translator;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanketpatel.translator.Utils.ViewUtils;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_GALLERY = 0;
    private static final int REQUEST_CAMERA = 1;
    StringBuilder detectedText;

    private static final String TAG = MainActivity.class.getSimpleName();
    ImageButton imageButton;
    private Uri imageUri;
    static TextView detectedTextView;


    FloatingActionButton fabAdd, fabOpenGallery, fabOpenCam;
    boolean isOpen = false;
    Animation fabopen, fabclose, fabforward, fabbackward;

    static SqliteDatabase mDatabase;

    List<Product> allProducts;
    ProductAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView productView = (RecyclerView) findViewById(R.id.product_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        productView.setLayoutManager(linearLayoutManager);
        productView.setHasFixedSize(true);

        mDatabase = new SqliteDatabase(this);
        allProducts = mDatabase.listProducts();

        if (allProducts.size() > 0) {
            productView.setVisibility(View.VISIBLE);
            mAdapter = new ProductAdapter(this, allProducts);

            // OnClickListener
            mAdapter.setOnClickListener(new ProductAdapter.OnClickListener() {
                @Override
                public void onClick(Product product) {
                    Intent i = new Intent(getApplicationContext(), ViewProductActivity.class);
                    i.putExtra("product", product);
                    startActivity(i);
                }
            });


            mDatabase.setOnDatabaseChangeListener(new SqliteDatabase.OnDatabaseChangeListener() {
                @Override
                public void itemAdded(Product product) {
                    allProducts.add(product);
                    mAdapter.notifyDataSetChanged();
                }

                @Override
                public void itemChanged(Product product) {
                    for (int i = 0; i < allProducts.size(); i++) {
                        if (product.getId() == allProducts.get(i).getId()) {
                            allProducts.set(i, product);
                            mAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }

                @Override
                public void itemRemoved(int itemID) {
                    for (int i = 0; i < allProducts.size(); i++) {
                        if ( allProducts.get(i).getId() == itemID) {
                            allProducts.remove(i);
                            break;
                        }
                    }
                }
            });

            productView.setAdapter(mAdapter);

        } else {
            productView.setVisibility(View.GONE);
            Toast.makeText(this, "There is no product in the database. Start adding now", Toast.LENGTH_LONG).show();
        }


        CropImage
                .activity()
                .setActivityTitle("Crop Image")
                .setAllowCounterRotation(false)
                .setAllowRotation(false);

        imageButton = (ImageButton) findViewById(R.id.copy);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("brij", detectedText);
                clipboard.setPrimaryClip(clip);
            }
        });

        detectedTextView = (TextView) findViewById(R.id.detected_text);
        detectedTextView.setMovementMethod(new ScrollingMovementMethod());
        init();


    }

    private void init() {

        fabAdd = findViewById(R.id.main_addFAB);
        fabOpenCam = findViewById(R.id.main_openCamFAB);
        fabOpenGallery = findViewById(R.id.main_openGalerryFAB);
        fabOpenCam.setVisibility(View.GONE);
        fabOpenGallery.setVisibility(View.GONE);


        fabforward = AnimationUtils.loadAnimation(this, R.anim.rotoate_forward);
        fabbackward = AnimationUtils.loadAnimation(this, R.anim.rotoate_backward);
        fabopen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabclose = AnimationUtils.loadAnimation(this, R.anim.fab_close);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
            }
        });

        fabOpenCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                animateFab();
                String filename = System.currentTimeMillis() + ".jpg";

                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, filename);
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                Intent intent = new Intent()
                        .setAction(MediaStore.ACTION_IMAGE_CAPTURE)
                        .putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

                startActivityForResult(intent, REQUEST_CAMERA);
            }
        });

        fabOpenGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
                Intent intent = new Intent().setType("image/*").setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_GALLERY);
            }

        });
    }

    private void animateFab() {
        if (isOpen) {
            fabOpenGallery.startAnimation(fabclose);
            fabOpenCam.startAnimation(fabclose);
            fabAdd.startAnimation(fabbackward);
            ViewUtils.setClickable(false, fabOpenGallery, fabOpenCam);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ViewUtils.setAlpha(1, fabOpenCam, fabOpenGallery);
                }
            }, 300);
        } else {
            ViewUtils.setVisible(fabOpenCam, fabOpenGallery);

            fabAdd.startAnimation(fabforward);
            fabOpenGallery.startAnimation(fabopen);
            fabOpenCam.startAnimation(fabopen);

            ViewUtils.setClickable(true, fabOpenGallery, fabOpenCam);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ViewUtils.setAlpha(1, fabOpenCam, fabOpenGallery);
                }
            }, 300);
        }
        isOpen = !isOpen;
    }


    private void inspectFromBitmap(Bitmap bitmap, Uri uri) {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(this).build();
        try {
            if (!textRecognizer.isOperational()) {
                new AlertDialog.
                        Builder(this).
                        setMessage("Text recognizer could not be set up on your device").show();
                return;
            }

            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> origTextBlocks = textRecognizer.detect(frame);
            List<TextBlock> textBlocks = new ArrayList<>();
            for (int i = 0; i < origTextBlocks.size(); i++) {
                TextBlock textBlock = origTextBlocks.valueAt(i);
                textBlocks.add(textBlock);
            }
            Collections.sort(textBlocks, new Comparator<TextBlock>() {
                @Override
                public int compare(TextBlock o1, TextBlock o2) {
                    int diffOfTops = o1.getBoundingBox().top - o2.getBoundingBox().top;
                    int diffOfLefts = o1.getBoundingBox().left - o2.getBoundingBox().left;
                    if (diffOfTops != 0) {
                        return diffOfTops;
                    }
                    return diffOfLefts;
                }
            });

            detectedText = new StringBuilder();
            for (TextBlock textBlock : textBlocks) {
                if (textBlock != null && textBlock.getValue() != null) {
                    detectedText.append(textBlock.getValue());
                    detectedText.append("\n");
                }
            }

            detectedTextView.setText(detectedText);
            detectedTextView.setTextColor(Color.BLACK);

            // sending uri
            Intent i = new Intent(MainActivity.this, title_content.class);
            i.putExtra("uri", uri.toString());
            i.putExtra("text", detectedText.toString());
            startActivity(i);
//            Product newProduct = new Product(detectedText.toString(), 0);
//            mDatabase.addProduct(newProduct);
//
//            //refresh the activity
//            finish();


        } finally {
            textRecognizer.release();
        }
    }

    private void inspect(Uri uri) {
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = getContentResolver().openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inSampleSize = 2;
            options.inScreenDensity = DisplayMetrics.DENSITY_LOW;
            bitmap = BitmapFactory.decodeStream(is, null, options);
            inspectFromBitmap(bitmap, uri);
        } catch (FileNotFoundException e) {
            Log.w(TAG, "Failed to find the file: " + uri, e);
        } finally {
            if (bitmap != null) {
                bitmap.recycle();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.w(TAG, "Failed to close InputStream", e);
                }
            }
        }
    }

    private Uri saveBitmap(Bitmap bitmap) {
        Uri uri = null;
        String path = Environment.getExternalStorageDirectory().toString();
        String filename = new SimpleDateFormat("MMddyyyy_HHmmss")
                .format(Calendar.getInstance().getTime()) + ".png";
        File dir = new File(path, getPackageName());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File outputFile = new File(dir.getAbsolutePath(), filename);
        try {
            outputFile.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            // CropImage.activity(Uri.fromFile(outputFile)).start(MainActivity.this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Uri.fromFile(outputFile);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_GALLERY:
                if (resultCode == RESULT_OK) {
//                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");


                    CropImage.activity(data.getData()).start(MainActivity.this);


                }
                break;
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    if (imageUri != null) {
//                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                        CropImage.activity(imageUri).start(MainActivity.this);


                    }
                }
                break;
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    inspect(result.getUri());
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }


    }

    private int addTaskDialog() {
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
                    Toast.makeText(MainActivity.this, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(getIntent());
                    finish();
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
        return 1;
    }


}
