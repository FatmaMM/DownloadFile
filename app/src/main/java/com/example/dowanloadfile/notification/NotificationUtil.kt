package com.example.dowanloadfile.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.dowanloadfile.MainActivity.Companion.CHANNEL_ID
import com.example.dowanloadfile.R
import com.example.dowanloadfile.details_screen.DetailsActivity


fun NotificationManager.pushNotification(
    status: String,
    color: Int,
    fileName: String,
    applicationContext: Context
) {

    val contentIntent = Intent(applicationContext, DetailsActivity::class.java)
    contentIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

    contentIntent.putExtra(
        "status",
        status
    )

    contentIntent.putExtra(
        "colorId",
        color
    )

    contentIntent.putExtra(
       "fileName",
        fileName
    )


    var pendingIntent: PendingIntent? = null
    pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.getActivity(applicationContext, 0, contentIntent, PendingIntent.FLAG_MUTABLE)
    } else {
        PendingIntent.getActivity(applicationContext, 0, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    val style = NotificationCompat.InboxStyle()

    val builder = NotificationCompat.Builder(applicationContext,CHANNEL_ID )
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(applicationContext.getString(R.string.notification_description))
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentIntent(pendingIntent)
        .addAction(0, applicationContext.getString(R.string.notification_button), pendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setStyle(style)
        .setAutoCancel(true)

    notify(0, builder.build())
}