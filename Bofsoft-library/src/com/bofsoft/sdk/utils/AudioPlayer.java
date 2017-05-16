package com.bofsoft.sdk.utils;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;

public class AudioPlayer {

    private MediaPlayer player;

    private int position = 0;
    private static AudioPlayer mAudioPlayer;

    public static AudioPlayer getInstence() {
        if (mAudioPlayer == null)
            mAudioPlayer = new AudioPlayer();
        return mAudioPlayer;
    }

    public AudioPlayer() {
        player = new MediaPlayer();
    }

    public void play(String path) {
        this.play(path, position);
    }

    /**
     * 播放
     *
     * @param path
     */
    public void play(String path, int position) {
        this.position = position;
        try {
            player.reset();// 把各项参数恢复到初始状态
            player.setDataSource(path);
            player.prepareAsync(); // 进行缓冲
            player.setOnPreparedListener(new PreparedListener(position));// 注册一个监听器
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 实现一个OnPrepareLister接口,当音乐准备好的时候开始播放
     */
    private final class PreparedListener implements OnPreparedListener {
        private int positon;

        public PreparedListener(int positon) {
            this.positon = positon;
        }

        @Override
        public void onPrepared(MediaPlayer mp) {
            player.start(); // 开始播放
            if (positon > 0) { // 如果音乐不是从头播放
                player.seekTo(positon);
            }
        }
    }

}
