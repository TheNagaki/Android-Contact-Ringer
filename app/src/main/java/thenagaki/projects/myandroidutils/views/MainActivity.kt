package thenagaki.projects.myandroidutils.views

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import thenagaki.projects.myandroidutils.R
import thenagaki.projects.myandroidutils.databinding.ActivityMainBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var notificationManager: NotificationManager
    private lateinit var notificationChannel: NotificationChannel
    private lateinit var builder: Notification.Builder
    private val channelId = "com.thenagaki.projects.myapplication"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        binding.startAppButton.setOnClickListener {
            Thread {
                while (true) {
                    displayRunningNotification()
                    Thread.sleep(5000)
                }
            }.start()
        }

        binding.addListenerBtn.setOnClickListener {
            startActivity(Intent(this, AddListenerActivity::class.java))
        }
    }

    private fun displayRunningNotification() {
        // pendingIntent is an intent for future use i.e after the notification is clicked, this intent will come into action
        val intent = Intent(this, MainActivity::class.java)

        // FLAG_UPDATE_CURRENT specifies that if a previous PendingIntent already exists, then the
        // current one will update it with the latest intent
        // 0 is the request code, using it later with the same method again will get back the same pending intent for future reference
        // intent passed here is to our afterNotification class
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        notificationChannel =
            NotificationChannel(
                channelId,
                "This notification should run while the application is working.",
                NotificationManager.IMPORTANCE_HIGH
            )
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.GREEN
        notificationChannel.enableVibration(false)
        notificationManager.createNotificationChannel(notificationChannel)

        val hour = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        val currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))

        builder = Notification.Builder(this, channelId)
            .setContentText("App currently enabled ( $hour the $currentDate )")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    this.resources,
                    R.drawable.ic_launcher_foreground
                )
            )
            .setContentIntent(pendingIntent)
            .setOngoing(true)
        notificationManager.notify(1234, builder.build())
    }
}