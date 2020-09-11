package com.teachenglish.ielts.extension

import android.content.Context
import android.widget.Toast

fun Context.toastSort(message:String?){
    Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
}