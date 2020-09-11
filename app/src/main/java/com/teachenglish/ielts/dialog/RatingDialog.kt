package com.teachenglish.ielts.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.teachenglish.ielts.R
import com.teachenglish.techenglish.utils.AndroidUtilities

class RatingDialog(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_rating)

        window?.attributes?.width = (AndroidUtilities.getWidthScreen()*0.8).toInt()
    }


    companion object{
        fun showDialog(context: Context){
            RatingDialog(context).show()
        }
    }
}