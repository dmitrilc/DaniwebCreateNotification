package com.example.daniwebcreatenotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    private val channelId by lazy {
        getString(R.string.channel_id) //Don't access me before resources are available.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()
        bindNotification()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //O = Oreo, not zero
            val channelName = getString(R.string.channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            //Create the NotificationChannel object
            val channel = NotificationChannel(
                channelId,
                channelName,
                importance)

            //Retrieve the NotificationManager from the system.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            //Registers the channel with NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification(): Notification {
        val icon = R.drawable.ic_baseline_notifications_24
        val title = getString(R.string.notification_title)
        val content = getString(R.string.notification_content)

        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(icon) //required
            .setContentTitle(title) //required
            .setContentText(content) //required
            .build()
    }

    private fun bindNotification() {
        val button = findViewById<Button>(R.id.button)
        val notificationId = R.id.notification_id
        val notification = createNotification()

        button.setOnClickListener {
            with(NotificationManagerCompat.from(this)) {
                notify(notificationId, notification)
            }
        }
    }

}