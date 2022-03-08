package br.com.app.blocodenotas.broadcast

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import androidx.core.text.HtmlCompat
import br.com.app.blocodenotas.R
import br.com.app.blocodenotas.controller.NotasDetailsActivity
import br.com.app.blocodenotas.model.Notas


class Reminder: BroadcastReceiver() {

    companion object{
        const val BUNDLE_NOTAS = "BUNDLE_NOTAS"
        const val NOTAS = "NOTAS"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val pm = context?.getSystemService(Context.POWER_SERVICE) as PowerManager

        val wl :PowerManager.WakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Reminder:wakeLock")
        wl.acquire(10 * 60 * 1000L /* 10 minutes*/)

        val bundle = intent?.getBundleExtra(BUNDLE_NOTAS)
        var nota = Notas()

        bundle?.let{nota = it.getParcelable(NOTAS)!! }

        val notifyIntent = Intent(context, NotasDetailsActivity::class.java)
        notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        notifyIntent.putExtra(BUNDLE_NOTAS, bundle)

        val pendingIntent =
            PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val mBuilder = NotificationCompat.Builder(context,"Reminder:notification")
        mBuilder.apply {
            setSmallIcon(R.drawable.small_ic_launcher)
            setContentTitle(nota.title)
            setContentText(HtmlCompat.fromHtml(nota.data, HtmlCompat.FROM_HTML_MODE_LEGACY))
            setStyle(NotificationCompat.BigTextStyle().bigText(HtmlCompat.fromHtml(nota.data, HtmlCompat.FROM_HTML_MODE_LEGACY)))
            priority = NotificationCompat.PRIORITY_HIGH
            setContentIntent(pendingIntent)
            setVibrate(arrayOf<Long>(100, 250, 100, 500).toLongArray())
            setTicker(context.getString(R.string.app_name))
            setDefaults(Notification.DEFAULT_SOUND)
        }
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        if (notificationManager != null) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                val notificationChannel = NotificationChannel("Reminder:notification", "Reminder", NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.description = "No Description"
                notificationManager.createNotificationChannel(notificationChannel)
            }

            notificationManager.notify(R.string.app_name, mBuilder.build())
        }
        wl.release()
    }

    fun setAlarm(context :Context, dateTime: Long, repeat: Boolean, data: Bundle){
        val am = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val i = Intent(context, Reminder::class.java)
        i.putExtra(BUNDLE_NOTAS, data)
        val pi = PendingIntent.getBroadcast(context, 0, i, 0)

        if (repeat) {
            var elapse: Long = dateTime - System.currentTimeMillis()
            if (elapse < 0) elapse = 1

            am.setRepeating(AlarmManager.RTC_WAKEUP, dateTime, elapse, pi)
        } else {
            am.set(AlarmManager.RTC_WAKEUP, dateTime, pi)
        }
    }

    fun cancelAlarm(context: Context) {
        val intent = Intent(context, Reminder::class.java)
        val sender = PendingIntent.getBroadcast(context, 0, intent, 0)
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        alarmManager?.cancel(sender)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager?.cancel(R.string.app_name)
    }
}