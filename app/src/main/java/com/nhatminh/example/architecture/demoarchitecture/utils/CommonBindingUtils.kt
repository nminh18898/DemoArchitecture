package com.nhatminh.example.architecture.demoarchitecture.utils

import android.view.View
import androidx.databinding.BindingAdapter

class CommonBindingUtils {

    companion object{

        /**
         * set visibility of view base on boolean
         * params view: view to set visibility
         * params isHidden: true mean GONE, false mean VISIBLE
         */
        @BindingAdapter("hidden")
        @JvmStatic
        fun bindVisibility(view : View, isHidden : Boolean){
            view.visibility = if(isHidden)View.GONE else View.VISIBLE
        }
    }
}