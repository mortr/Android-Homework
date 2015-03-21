package by.soloviev.mplayer;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

/**
 * Created by USER on 20.03.2015.
 */
public class MPlayerService extends Service {
    private static MediaPlayer mMediaPlayer;
    public final static String ACTION_PLAY = "play";
    public final static String ACTION_PAUSE = "pause";
    public final static String ACTION_STOP = "stop";

    @Override
    public IBinder onBind(Intent intent) {
        switch (intent.getAction()) {
            case ACTION_PLAY:
                play();
                break;

            case ACTION_STOP:
                stop();
                break;

            case ACTION_PAUSE:
                pause();
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown action '" + intent.getAction() + "'.");

        }
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            try {
                AssetFileDescriptor file = getAssets().openFd("SZ.mp3");
                mMediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        switch (intent.getAction()) {
            case ACTION_PLAY:
                play();
                break;

            case ACTION_STOP:
                stop();
                break;

            case ACTION_PAUSE:
                pause();
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown action '" + intent.getAction() + "'.");

        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void pause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    private void play() {
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    private void stop() {
        if (!(mMediaPlayer == null)) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
    }
}
