package com.kevin.hanakotoba

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import java.text.SimpleDateFormat
import java.util.*

const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "It's time to water your flower !"

class Notification : BroadcastReceiver() {

    @SuppressLint("LaunchActivityFromNotification", "UnspecifiedImmutableFlag")
    override fun onReceive(context: Context, intent: Intent) {

        val mainActivity = Intent(context, GardenActivity::class.java)

        mainActivity.putExtra("FromNotif", true)
        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    0,
                    mainActivity,
                    FLAG_UPDATE_CURRENT
                )
            )
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()


        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        manager.notify(createID(), notification)
    }

    private fun createID(): Int {
        val now = Date()
        return SimpleDateFormat("ddHHmmss", Locale.FRANCE).format(now).toInt()
    }

}
