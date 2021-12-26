package com.kevin.hanakotoba

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import kotlin.random.Random

const val notificationID  = 1
const val channelID =  "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageEXtra"

class Notification : BroadcastReceiver(){


    @SuppressLint("LaunchActivityFromNotification")
    override fun onReceive(context: Context, intent : Intent) {

        val notification  = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setContentText(intent.getStringExtra(messageExtra))
        /*    .setContentIntent(
                PendingIntent.getActivity(
                context, // Context from onReceive method.
                0,
                Intent(context, MainActivity::class.java), // Activity you want to launch onClick.
                0
            )
            )*/
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()


        val uniqueID = 100
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(Random.nextInt(0, 100),notification)
    }

}