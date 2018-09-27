package com.example.sanketpatel.translator;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Sanket Patel on 27-09-2018.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>{

    private Context context;
    private List<Product> listProducts;

    private SqliteDatabase mDatabase;

    public ProductAdapter(Context context, List<Product> listProducts) {
        this.context = context;
        this.listProducts = listProducts;
        mDatabase = new SqliteDatabase(context);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_layout, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        final Product singleProduct = listProducts.get(position);

        holder.name.setText(singleProduct.getName());

//        holder.editProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                editTaskDialog(singleProduct);
//            }
//        });

//        holder.deleteProduct.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //delete row from database
//
//                mDatabase.deleteProduct(singleProduct.getId());
//
//                //refresh the activity page.
//                ((Activity)context).finish();
//                context.startActivity(((Activity) context).getIntent());
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return listProducts.size();
    }


    private void editTaskDialog(final Product product){
        LayoutInflater inflater = LayoutInflater.from(context);
//        View subView = inflater.inflate(R.layout.add_product_layout, null);
//
//        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
//        final EditText quantityField = (EditText)subView.findViewById(R.id.enter_quantity);
//
//        if(product != null){
//            nameField.setText(product.getName());
//            quantityField.setText(String.valueOf(product.getQuantity()));
//        }
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Edit product");
//        builder.setView(subView);
//        builder.create();
//
//        builder.setPositiveButton("EDIT PRODUCT", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            //    final String name = nameField.getText().toString();
//                final String name = MainActivity.detectedTextView.toString();
//                final int quantity = Integer.parseInt(quantityField.getText().toString());
//
//                if(TextUtils.isEmpty(name) || quantity <= 0){
//                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
//                }
//                else{
//                    mDatabase.updateProduct(new Product(product.getId(), name, quantity));
//                    //refresh the activity
//                    ((Activity)context).finish();
//                    context.startActivity(((Activity)context).getIntent());
//                }
//            }
//        });

        final String name = MainActivity.detectedTextView.toString();
      //  final int quantity = Integer.parseInt(quantityField.getText().toString());

//        if(TextUtils.isEmpty(name) || quantity <= 0){
//            Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
//        }
//        else{
            mDatabase.updateProduct(new Product(product.getId(), name, 0));
            //refresh the activity
            ((Activity)context).finish();
            context.startActivity(((Activity)context).getIntent());
        //}

//        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
//            }
//        });
//        builder.show();
    }
}

