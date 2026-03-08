package com.karthik.pro.engr.github.api.core.notification

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.ForegroundInfo
import com.karthik.pro.engr.github.api.core.constants.NotificationConstants.CHANNEL_ID
import com.karthik.pro.engr.github.api.core.constants.NotificationConstants.NOTIF_ID
import com.karthik.pro.engr.github.api.core.constants.NotificationConstants.UPLOADS

object UploadNotification {

    fun createChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(CHANNEL_ID, UPLOADS, IMPORTANCE_LOW)
            notificationChannel.setShowBadge(false)
            (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
                .createNotificationChannel(notificationChannel)
        }
    }

    fun buildNotification(context: Context, title: String, progress: Int): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setSmallIcon(R.drawable.stat_sys_upload)
            .setOnlyAlertOnce(true)
            .build()
    }

    fun createForegroundInfo(context: Context, title: String, progress: Int): ForegroundInfo {
        val notification = buildNotification(context, title, progress)
        return ForegroundInfo(NOTIF_ID, notification)
    }

}