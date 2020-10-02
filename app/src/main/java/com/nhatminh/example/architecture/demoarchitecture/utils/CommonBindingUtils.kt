package com.nhatminh.example.architecture.demoarchitecture.utils

import android.view.View
import androidx.databinding.BindingAdapter

class CommonBindingUtils {

    companion object{

        @BindingAdapter("hidden")
        @JvmStatic
        fun bindVisibility(view : View, isHidden : Boolean){
            view.visibility = if(isHidden)View.GONE else View.VISIBLE
        }
    }
}