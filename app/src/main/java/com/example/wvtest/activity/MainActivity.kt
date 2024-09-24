package com.example.wvtest.activity

import android.content.Context
import android.graphics.Typeface
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout.LayoutParams
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wvtest.R
import com.example.wvtest.adapter.HorizontalAdapter
import com.example.wvtest.api.repository.ContentRepository
import com.example.wvtest.databinding.ActivityMainBinding
import com.example.wvtest.databinding.SingleCardBinding
import com.example.wvtest.dialog.LoadingDialog
import com.example.wvtest.dialog.StandardDialog
import com.example.wvtest.model.ContentShelfs

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    var mode:Boolean = false
    lateinit var countDownTimer:CountDownTimer
    lateinit var standardDialog: StandardDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loadingDialog = LoadingDialog(this@MainActivity)

        //show loading
        loadingDialog.show()

        //check network
        countDownTimer = object : CountDownTimer(2000,1000){
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                if(!checkNetwork()){
                    //show dialog network error
                    loadingDialog.dismiss()
                    standardDialog = object : StandardDialog(this@MainActivity){
                        override fun onClickButton() {
                            loadingDialog.show()
                            standardDialog.dismiss()
                            countDownTimer.start()
                        }
                    }
                    standardDialog.show("please check network")


                } else {
                    loadingDialog.dismiss()
                    ContentRepository.newInstance().getContent { content ->
                        if (content != null) setContent(content)
                    }

                    binding.switchDark.setOnClickListener {
                        switchMode(mode)
                    }
                }
            }

        }

        countDownTimer.start()

        binding.refresh.setOnRefreshListener {
            ContentRepository.newInstance().getContent { content ->
                binding.content.removeAllViews()
                if (content != null) setContent(content)
                else {
                    loadingDialog.show()
                    countDownTimer.start()
                }
                binding.refresh.isRefreshing = false
            }
        }

    }


    private fun setContent(content:ArrayList<ContentShelfs>){

        for (i in 0 until content.size){
            when (content[i].type){
                "banner_1" -> {
                    addBanner(content[i])
                }
                "carousel" -> {
                    addCarousel(content[i])
                }
                "eventSingleCard" -> {
                    addSingleCard(content[i])
                }
            }
        }
    }




    private fun checkNetwork(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!
            .state == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.CONNECTED
    }


    private fun addBanner(content:ContentShelfs){

        val flipper = ViewFlipper(this)
        flipper.apply {
            isAutoStart = true
            flipInterval = 3000
            inAnimation = AnimationUtils.loadAnimation(applicationContext,android.R.anim.slide_in_left)
            outAnimation = AnimationUtils.loadAnimation(applicationContext,android.R.anim.slide_out_right)
        }

        for (i in 0 until content.items.size){

            val image = ImageView(this)
            Glide.with(image.context)
                .load(content.items[i].coverUrl)
                .error(R.drawable.errorpic)
                .into(image)

            image.layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,500)

            flipper.addView(image)
        }

        binding.content.addView(flipper)
    }

    private fun addCarousel(content:ContentShelfs){

        val listView = RecyclerView(this)
        listView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        listView.adapter = object : HorizontalAdapter(content.items){
            override fun onSelectItem() {
                //do something
            }
        }

        binding.content.apply {
            addView(addTextHeader("Header"))
            addView(listView)
        }
    }

    private fun addSingleCard(content:ContentShelfs){

        val bindingCard:SingleCardBinding = SingleCardBinding.inflate(LayoutInflater.from(this))
        bindingCard.apply {
            Glide.with(image.context).load(content.coverUrl).into(image)
            title.text = content.title
            subtitle.text = content.subTitle
        }

        binding.content.apply {
            addView(addTextHeader("Header"))
            addView(bindingCard.root)
        }
    }


    private fun addTextHeader(headerText:String):TextView{
        val paramsTextViewHeader = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        paramsTextViewHeader.setMargins(6,5,5,6)

        val header = TextView(this)
        header.text = headerText
        header.setTypeface(header.typeface, Typeface.BOLD)
        header.layoutParams = paramsTextViewHeader

        return header
    }


    private fun switchMode(dark:Boolean){

        mode = when(dark){
            true -> {
                //to light
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchDark.setImageResource(R.drawable.baseline_todark_6_24)
                false
            }
            false -> {
                //to night
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchDark.setImageResource(R.drawable.baseline_brightness_6_24)
                true
            }
        }

    }

}