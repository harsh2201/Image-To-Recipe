package com.example.sanketpatel.translator.Utils;


import android.view.View;

public class ViewUtils {

    public static void setEnable(View... views) {
        for (View view : views) {
            view.setEnabled(true);
        }
    }

    public static void setDisable(View... views) {
        for (View view : views) {
            view.setEnabled(false);
        }
    }

    public static void setVisible(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void setInvisible(View... views) {
        for (View view : views) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void setVisibilityGone(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    public static void setVisibility(int visibility, View... views) {
        for (View view : views) {
            view.setVisibility(visibility);
        }
    }

    public static void setClickable(boolean clickable, View ... views){
        for (View view : views){
            view.setClickable(clickable);
        }
    }

    public static  void setAlpha(float alpha, View ... views){
        for (View view : views){
            view.setAlpha(alpha);
        }
    }
}
