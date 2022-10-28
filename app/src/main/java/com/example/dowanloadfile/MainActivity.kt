package com.example.dowanloadfile

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import com.example.dowanloadfile.databinding.ActivityMainBinding
import com.example.dowanloadfile.notification.NotificationBroadcastReceiver

class MainActivity : AppCompatActivity() {
    companion object {
        private const val URL =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"
         const val CHANNEL_ID = "channelId"
         const val CHANNEL_NAME = "channelName"
    }

    private var downloadID: Long = 0
    var selectedRb = 0

    lateinit var binding: ActivityMainBinding

    lateinit var  receiver:NotificationBroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
         receiver = NotificationBroadcastReceiver(this@MainActivity)

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        createNotificationChannel(CHANNEL_ID, CHANNEL_NAME)
        binding.downloadButton.setOnClickListener {
            download()
        }
    }


    private fun download() {
        val request = DownloadManager.Request(Uri.parse(URL))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID = downloadManager.enqueue(request)// enqueue puts the download request in the queue.
    }


    fun onRadioButtonChecked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            this.selectedRb = if(checked) view.getId() else 0
            binding.downloadButton.setSelectedRB(this.selectedRb)
            receiver.selectedButton = this.selectedRb
        }
    }
    private fun createNotificationChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val descriptionText = getString(R.string.app_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(channelId, channelName, importance)
            mChannel.description = descriptionText
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.setShowBadge(false)
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannel(mChannel)
        }
    }

}