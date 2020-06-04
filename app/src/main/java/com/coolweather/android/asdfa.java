package com.coolweather.android;

import Android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //获取拖动结束之后的位置
                int progress = seekBar.getProgress();
                //跳转到某个位置播放
                mediaPlayer.seekTo(progress);
            }
        });
    }

    public void isPlayOrPause(View view){
//把View强转为ImageButton
        ImageButton imageButton = (ImageButton) view;

        if(mediaPlayer==null) {
            //实例化MediaPlayer
            mediaPlayer = MediaPlayer.create(this, R.raw.yao);
            //开始播放
            mediaPlayer.start();
            //图标修改为暂停的图标
            imageButton.setImageResource(android.R.drawable.ic_media_pause);
            //获取音乐的总时长
            int duration = mediaPlayer.getDuration();
            //设置进度条的最大值为音乐的总时长
            seekBar.setMax(duration);

            new MyThre().start();

        }//判断如果音乐在播放
        else if(mediaPlayer.isPlaying()){
            //就暂停音乐
            mediaPlayer.pause();
            //图标修改为播放的图标
            imageButton.setImageResource(android.R.drawable.ic_media_play);
        }else{
            mediaPlayer.start();
            //图标修改为暂停的图标
            imageButton.setImageResource(android.R.drawable.ic_media_pause);
        }
    }

    //写内部类
    class MyThre extends Thread{
        @Override
        public void run() {
            super.run();
            while(seekBar.getProgress()<=seekBar.getMax()){

                //获取当前音乐播放的位置
                int currentPosition = mediaPlayer.getCurrentPosition();

                //让进度条动起来
                seekBar.setProgress(currentPosition);
            }
        }
    }
}