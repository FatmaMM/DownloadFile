package com.example.dowanloadfile.details_screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.dowanloadfile.MainActivity
import com.example.dowanloadfile.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val extras: Bundle? = intent.extras
        extras?.let {
            val status = it.getString("status")
            val color = it.getInt("colorId", 0)
            val message = it.getString("fileName")
            initializeViews(status!!, color, message!!)
        }
    }

    private fun initializeViews(statusText: String, colorId: Int, fileName: String) {
        binding.statusValue.text = statusText
        binding.statusValue.setTextColor(ContextCompat.getColor(this,colorId))
        binding.fileNameValue.text = fileName
    }

    fun okButtonClicked(view:View) {
        startActivity(Intent(applicationContext, MainActivity::class.java))
    }
}