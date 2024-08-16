package com.ahmetmuhittingurkan.todoapp.entity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import androidx.core.app.NotificationCompat
import com.ahmetmuhittingurkan.todoapp.R

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val notId = intent.getIntExtra("notId", -1)
        val notBaslik = intent.getStringExtra("notBaslik") ?: "Hatırlatma"

        if (notId != -1) {
            // Bildirimi oluşturma
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channelId = "not_reminder_channel"
            val channelName = "Not Reminder"

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(channel)
            }

            val notification = NotificationCompat.Builder(context, channelId)
                .setContentTitle("Hatırlatıcı")
                .setContentText("Notunuz: $notBaslik")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setAutoCancel(true)
                .build()

            notificationManager.notify(notId, notification)
        }
    }
}
