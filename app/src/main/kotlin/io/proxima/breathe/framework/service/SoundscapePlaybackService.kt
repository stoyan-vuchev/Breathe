package io.proxima.breathe.framework.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.IBinder
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SoundscapePlaybackService : Service() {

    // @Inject
    // lateinit var exoPlayer: ExoPlayer

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        println("SoundscapePlaybackService: $intent")

        val uri: String? by lazy { intent?.extras?.getString("uri") }
        val title: String? by lazy { intent?.extras?.getString("title") }
        val image: String? by lazy { intent?.extras?.getString("image") }

        println("SoundscapePlaybackService: $uri, $title")

        uri?.let { nonNullUri ->

            title?.let { nonNullTitle ->

                val mediaItem =
                    MediaItem.Builder()
                        .setMediaId("media-1")
                        .setUri(Uri.parse(nonNullUri))
                        .setMediaMetadata(
                            MediaMetadata.Builder()
                                .setArtist("David Bowie")
                                .setTitle("Heroes")
                                //   .setArtworkUri(image!!)
                                .build()
                        )
                        .build()


                createNotification(nonNullTitle)

                //  exoPlayer.release()
                //  exoPlayer.setMediaItem(mediaItem)
                //  exoPlayer.prepare()
                // exoPlayer.play()


            } ?: Unit

        } ?: Unit

        return START_STICKY

    }

    override fun onDestroy() {
        // exoPlayer.release()
        super.onDestroy()
    }

    private fun createNotification(
        title: String
    ) {

        val channelId = "soundscape_playback_service_channel"

        val channel = NotificationChannel(
            channelId,
            "Soundscape Playback Service",
            NotificationManager.IMPORTANCE_LOW
        )

        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)

//        val notification: Notification = NotificationCompat.Builder(this, channelId)
//            .setContentTitle(title)
//            .setContentText("Now playing $title")
//            .setSmallIcon(R.drawable.sound_wave)
//            .setContentIntent(
//                PendingIntent.getActivity(
//                    this,
//                    0,
//                    Intent(
//                        ++
//
//                                // Andd we lost the intent, gotta find and copy again :*)

    }

}