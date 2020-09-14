package com.workspace.githubusertwo.broadcast

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.workspace.githubusertwo.R
import com.workspace.githubusertwo.activity.SearchActivity
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val EXTRA_TYPE = "type"
        const val ID_REPEATING = 101
        val VIBRATE = longArrayOf(1000, 1000, 1000, 1000, 1000)

        //API 28 (Oreo)
        private const val CHANNEL_ID = "Channel_1"
        private const val CHANNEL_NAME = "Github Reminder"


    }

    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getIntExtra(EXTRA_TYPE, -1)

        if (type == ID_REPEATING) {

            showAlarmNotification(context, type)

        }


    }


    private fun showAlarmNotification(
        context: Context,
        notifId: Int
    ) {

        val intent = Intent(context, SearchActivity::class.java)
        val intentPending = PendingIntent.getActivity(context, 0, intent, 0)
        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val message = "Are You Lucky Today ! ?"
        val title = "Github User App"
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(intentPending)
            .setSmallIcon(R.drawable.ic_bell)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        oreoNotification(CHANNEL_ID, CHANNEL_NAME, builder, notificationManagerCompat)

        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)

    }


    private fun oreoNotification(
        channelId: String,
        channelName: String,
        builder: NotificationCompat.Builder,
        notificationManagerCompat: NotificationManager
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.enableVibration(true)
            channel.vibrationPattern = VIBRATE

            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }
    }


    fun setRepeatingAlarm(context: Context, time: String, notifId: Int) {

        val timeArray = time.split(":").toTypedArray()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_TYPE, notifId)


        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        Toast.makeText(context, "Repeating alarm set up", Toast.LENGTH_SHORT).show()
    }


    fun cancelAlarm(context: Context, notificationId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, 0)
        //Cancel Alarm Manager
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, "Turn Off", Toast.LENGTH_SHORT).show()
    }


}

