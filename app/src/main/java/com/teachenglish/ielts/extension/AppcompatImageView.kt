package com.teachenglish.ielts.extension

import android.util.Base64
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide

fun ImageView.setImageFromURL(url: Any, @DrawableRes placeHolder: Int = 0) {
    if(url is Int){
        this.cropToPadding = true
        Glide.with(this.context)
            .load(url)
            .centerCrop()
            .placeholder(placeHolder)
            .into(this)
    }else{
        setImageFromURL(url.toString())
    }


}

fun ImageView.setImageFromURL(base64String: String, @DrawableRes placeHolder: Int = 0) {
    this.cropToPadding = true
    val imageByteArray =Base64.decode(base64String,Base64.DEFAULT)

    Glide.with(this.context)
        .asBitmap()
        .load(imageByteArray)
        .centerCrop()
        .placeholder(placeHolder)
        .into(this)
}

fun ImageView.loadImageCircle(url: Any) {
    Glide.with(this.context)
        .load(url)
        .circleCrop()
        .into(this)
}