package com.example.dowanloadfile.notification

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.example.dowanloadfile.R

class NotificationBroadcastReceiver(var context: Context) :
    BroadcastReceiver() {

    var selectedButton: Int? = null

    override fun onReceive(p0: Context?, p1: Intent?) {
        val id = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

            if (selectedButton == R.id.glide_rb) {
                notificationManager.pushNotification(
                    context.getString(R.string.success),
                    R.color.teal_200,
                    context.getString(R.string.glide_txt),
                    context
                )
            }

                if(selectedButton == R.id.load_rb) {
                notificationManager.pushNotification(
                    context.getString(R.string.fail),
                    android.R.color.holo_red_dark,
                    context.getString(R.string.load_txt),
                    context
                )
            }

            if (selectedButton == R.id.retrofit_rb) {
                notificationManager.pushNotification(
                    context.getString(R.string.success),
                    R.color.teal_200,
                    context.getString(R.string.retrofit_txt),
                    context
                )
            }
        }

}