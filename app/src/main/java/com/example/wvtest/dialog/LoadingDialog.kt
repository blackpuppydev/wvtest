package com.example.wvtest.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.example.wvtest.databinding.DialogLoadingBinding

class LoadingDialog(context: Context) : Dialog(context) {

    private var binding: DialogLoadingBinding = DialogLoadingBinding.inflate(layoutInflater)

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(binding.root)
        setCancelable(false)





    }
}