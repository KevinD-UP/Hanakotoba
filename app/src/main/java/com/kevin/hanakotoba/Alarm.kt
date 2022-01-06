package com.kevin.hanakotoba

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class Alarm(val activity: Context) {


    @RequiresApi(Build.VERSION_CODES.M)
    fun scheduleNotification(flower_name: String, id: String, calendar: Calendar) {
        val intent = Intent(activity, Notification::class.java)
        val message = "Time to water !"
        intent.putExtra(titleExtra, flower_name)
        intent.putExtra(messageExtra, message)
        intent.setAction(id)

        val pendingIntent = PendingIntent.getBroadcast(
            activity,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )


        val alarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
        showAlert(calendar.timeInMillis, flower_name, message)
    }

    fun cancelAlarm(flower_name: String, id: String) {
        val intent = Intent(activity, Notification::class.java)
        val title = flower_name
        val message = "It's a flower"
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)
        intent.setAction(id)
        intent.putExtra("TAG", flower_name + id)

        val pendingIntent = PendingIntent.getBroadcast(
            activity,
            notificationID,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }


    private fun showAlert(time: Long, title: String, message: String) {
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(activity)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(activity)

        AlertDialog.Builder(activity)
            .setTitle("Notification Scheduled")
            .setMessage(title + "\n" + dateFormat.format(date) + " " + timeFormat.format(date))
            .setPositiveButton("Okay") { _, _ -> }
            .show()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel() {
        val name = "Notif channel"
        val desc = "A description of the channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc

        val notificationManager =
            activity.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }
}


