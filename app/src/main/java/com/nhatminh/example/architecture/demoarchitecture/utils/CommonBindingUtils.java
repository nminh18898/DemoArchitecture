package com.nhatminh.example.architecture.demoarchitecture.utils;

import android.view.View;

import androidx.databinding.BindingAdapter;

public class CommonBindingUtils {

    @BindingAdapter("hidden")
    public static void bindVisibility(View view, boolean isHidden){
        view.setVisibility(isHidden?View.GONE:View.VISIBLE);
    }

}
