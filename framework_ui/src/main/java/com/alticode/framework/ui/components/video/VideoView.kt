package com.alticode.framework.ui.components.video

import android.content.Context
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.alticode.framework.ui.R
import com.alticode.platform.delegation.text
import com.alticode.platform.log.AppLog
import com.alticode.platform.utils.toTime
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SeekParameters
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.google.android.material.slider.Slider
import java.io.File
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class VideoView(context: Context?, attrs: AttributeSet?) : LinearLayout(context, attrs),
    Player.EventListener {

    init {
        orientation = VERTICAL
        LayoutInflater.from(context).inflate(R.layout.view_video, this, true)
    }

    val exoPlayerView: StyledPlayerView by lazy { findViewById(R.id.exoPlayerView) }
    val btnPlay: ImageView by lazy { findViewById(R.id.btnPlay) }
    val progressSlider: Slider by lazy { findViewById(R.id.progressSlider) }
    private val tvDuration: TextView by lazy { findViewById(R.id.tvDuration) }

    private var playbackState by playerState()
    var isPlaying by isPlaying()
    var remainDuration by tvDuration.text()

    inner class ProgressHandler : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            postDelayed(progressRunnable, 1_000)
            val currentPosition = exoPlayerView.player?.currentPosition?.toFloat() ?: 0f
            val duration = exoPlayerView.player?.duration?.toFloat() ?: 1f
            progressSlider.value = currentPosition
            remainDuration = (duration - currentPosition).toLong().toTime()
        }
    }

    val progressHandler = ProgressHandler()
    val progressRunnable = Runnable { progressHandler.sendMessage(progressHandler.obtainMessage()) }

    fun setVideoPath(path: String, playWhenReady: Boolean = true) {
        val player = SimpleExoPlayer.Builder(context).build().apply {
            val mediaUri = Uri.fromFile(File(path))
            val mediaItem = MediaItem.fromUri(mediaUri)
            this.setMediaItem(mediaItem)
            this.playWhenReady = playWhenReady
            this.prepare()

            addListener(this@VideoView)
            setSeekParameters(SeekParameters.CLOSEST_SYNC)
        }
        exoPlayerView.player = player

        // Seek
        progressSlider.addOnChangeListener { _, value, fromUser ->
            if (fromUser) {
                player.seekTo(value.toLong())
            }
        }

        // Play/Pause
        btnPlay.setOnClickListener {
            if (isPlaying) player.pause()
            else player.play()
        }
    }

    override fun onPlaybackStateChanged(state: Int) {
        this.playbackState = state
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        this.isPlaying = isPlaying
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        exoPlayerView.player?.let {
            it.stop()
            it.release()
        }
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        if (!hasWindowFocus) exoPlayerView.player?.pause()
    }
}

fun VideoView.playerState(): ReadWriteProperty<Any, Int> {
    return object : ReadWriteProperty<Any, Int> {
        override fun getValue(thisRef: Any, property: KProperty<*>): Int {
            return exoPlayerView.player?.playbackState ?: Player.STATE_IDLE
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) {
            when (value) {
                Player.STATE_IDLE -> {
                }
                Player.STATE_BUFFERING -> {
                }
                Player.STATE_READY -> {
                    val currentPosition = exoPlayerView.player?.currentPosition?.toFloat() ?: 0f
                    val duration = exoPlayerView.player?.duration?.toFloat() ?: 1f
                    progressSlider.valueFrom = 0f
                    progressSlider.valueTo = duration
                    remainDuration = (duration - currentPosition).toLong().toTime()
                }
                Player.STATE_ENDED -> {
                    btnPlay.setImageResource(R.drawable.ic_replay)
                }
            }
        }
    }
}

fun VideoView.isPlaying(): ReadWriteProperty<Any, Boolean> {
    return object : ReadWriteProperty<Any, Boolean> {
        override fun getValue(thisRef: Any, property: KProperty<*>): Boolean {
            return exoPlayerView.player?.isPlaying ?: false
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) {
            btnPlay.setImageResource(if (value) R.drawable.ic_paused else R.drawable.ic_play)
            if (isPlaying) {
                progressHandler.postDelayed(progressRunnable, 1_000)
            } else {
                progressHandler.removeCallbacks(progressRunnable)
            }
        }
    }
}
