package com.silverbullet.asteroidsradar.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.silverbullet.asteroidsradar.R

@Suppress("unused")
@BindingAdapter("isPotentiallyHazardous")
fun ImageView.bindIsPotentiallyHazardous(isHazardous: Boolean){
    contentDescription = if(isHazardous){
        setImageResource(R.drawable.ic_hazardous)
        "This Asteroid is potentially hazardous"
    }else{
        setImageResource(R.drawable.ic_safe)
        "This Asteroid is safe"
    }
}