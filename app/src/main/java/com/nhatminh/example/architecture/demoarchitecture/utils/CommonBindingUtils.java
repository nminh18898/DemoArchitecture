package com.nhatminh.example.architecture.demoarchitecture.utils;

import android.view.View;
import android.widget.EditText;

import androidx.databinding.BindingAdapter;

import com.nhatminh.example.architecture.demoarchitecture.search.viewmodel.SearchViewModel;

public class CommonBindingUtils {

    @BindingAdapter("hidden")
    public static void bindVisibility(View view, boolean isHidden){
        view.setVisibility(isHidden?View.GONE:View.VISIBLE);
    }

}
