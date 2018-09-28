package com.example.sanketpatel.translator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SmartyToast {
    public static final int LENGTH_SHORT=0;
    public static final int LENGTH_LONG=1;

    public static final int DONE=0;
    public static final int WARNING=1;
    public static final int ERROR=2;
    public static final int SAVED=3;
    public static final int CONNECTED=4;
    public static final int UPDATE=5;

    public static Toast makeText(Context context, String msg, int length, int type) {
        Toast toast = new Toast(context);

        switch (type) {

            case 5:{
                View layout=LayoutInflater.from(context).inflate(R.layout.updating_layout,null,false);
                TextView textView= (TextView) layout.findViewById(R.id.updating_message);
                textView.setText("Initializing Text Recognization");
                toast.setView(layout);
                break;
            }
        }
        toast.setDuration(length+5000);
        toast.show();
        return toast;
    }
}
