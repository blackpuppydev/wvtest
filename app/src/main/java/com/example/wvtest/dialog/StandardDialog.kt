package com.example.wvtest.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.example.wvtest.databinding.DialogStandardBinding

abstract class StandardDialog(context: Context):Dialog(context) {

    private var binding: DialogStandardBinding = DialogStandardBinding.inflate(layoutInflater)

    init{
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(binding.root)
        setCancelable(true)

        binding.btnCancel.setOnClickListener {
            onClickButton()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    private fun setMessage(title: String?) {
        binding.title.text = title
    }

    fun show(title: String?){
        setMessage(title)
        setCancelable(false)
        show()
    }

    abstract fun onClickButton()

}