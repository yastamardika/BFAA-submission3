package com.ystmrdk.sub2_bfaa.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import com.ystmrdk.sub2_bfaa.R
import com.ystmrdk.sub2_bfaa.ui.MainActivity

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val NOTIF_ID = 100
    }

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        showNotification(
            context,
            NOTIF_ID,
            "Hei!",
            "Jo lali buka meneh ya sesuk!",
            "daily",
            "remider"
        )
    }

    private fun showNotification(
        context: Context,
        notifId: Int,
        title: String,
        message: String,
        channelId: String,
        channelName: String
    ) {
        Log.d("CEK", "showNotif")
        val notifManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val resultIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, resultIntent, 0)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(message)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .setGroupSummary(true)
            .setSound(alarmSound)
//            .apply {
//                setContentIntent(resultPendingIntent)
//            }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.enableVibration(false)
            channel.description = channelName
            builder.setChannelId(channelId)
            notifManager.createNotificationChannel(channel)
        }
        val notif = builder.build()
        notifManager.notify(notifId, notif)
    }


}
