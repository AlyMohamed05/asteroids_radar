package com.silverbullet.asteroidsradar.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.silverbullet.asteroidsradar.R

@Suppress("unused")
@BindingAdapter("isAsteroidHazardous")
fun ImageView.bindIsHazardous(isPotentiallyHazardous: Boolean) {
    contentDescription =
        if (isPotentiallyHazardous) {
            setImageResource(R.drawable.asteroid_hazardous)
            "This is a potentially hazardous asteroid"
        } else {
            setImageResource(R.drawable.asteroid_safe)
            "This is a safe asteroid"
        }
}

@Suppress("unused")
@BindingAdapter("absoluteMagnitude")
fun TextView.bindAbsoluteMagnitude(absoluteMagnitude: Double) {
    text = "${absoluteMagnitude.toString()} au"
}

@Suppress("unused")
@BindingAdapter("estimatedDiameter")
fun TextView.bindEstimatedDiameter(estimatedDiameter: Double) {
    text = "${estimatedDiameter.toString()} km"
}

@Suppress("unused")
@BindingAdapter("relativeVelocity")
fun TextView.bindRelativeVelocity(relativeVelocity: Double){
    text = "${relativeVelocity.toString()} km/s"
}

@Suppress("unused")
@BindingAdapter("distanceFromDistance")
fun TextView.bindDistanceFromEarth(distanceFromEarth: Double){
    text = "${distanceFromEarth.toString()} au"
}