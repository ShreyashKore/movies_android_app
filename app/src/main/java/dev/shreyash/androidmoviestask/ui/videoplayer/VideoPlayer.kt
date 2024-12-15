package dev.shreyash.androidmoviestask.ui.videoplayer

import android.content.pm.ActivityInfo
import android.view.WindowInsetsController
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dev.shreyash.androidmoviestask.MainActivity

/**
 * Loads a video player with given key in full-screen; Supports YouTube videos only.
 */
@Composable
fun VideoPlayer(videoKey: String) {
    // TODO: Add support for sources other than YouTube
    val activity = LocalContext.current as MainActivity
    val windowInsetsController = remember {
        WindowCompat.getInsetsController(activity.window, activity.window.decorView)
    }
//    val exoPlayer = remember {
//        ExoPlayer.Builder(activity).build().apply {
//            setMediaItem(MediaItem.fromUri("https://www.youtube.com/watch?v=$videoKey"))
//            prepare()
//            playWhenReady = true
//        }
//    }

    DisposableEffect(Unit) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        windowInsetsController.apply {
            hide(android.view.WindowInsets.Type.systemBars())
            systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        onDispose {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            windowInsetsController.show(android.view.WindowInsets.Type.systemBars())
//            exoPlayer.release()
        }
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    AndroidView(
        // todo fix vertical padding issue
        modifier = Modifier.safeContentPadding(),
        factory = { context ->
            YouTubePlayerView(context).apply {
                lifecycle.addObserver(this)
                getYouTubePlayerWhenReady(
                    object: YouTubePlayerCallback {
                        override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(videoKey, 0f)
                        }
                    }
                )
            }
//            PlayerView(context).apply {
//                player = exoPlayer
//                useController = true
//            }
        }
    )
}