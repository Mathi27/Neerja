package com.proudcoder.firebase_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class Noise extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    AudioManager audioManager;

    public void play(View view){
        mediaPlayer.start();
    }
    public void pause(View view){
        mediaPlayer.pause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noise);
        mediaPlayer  = MediaPlayer.create(this,R.raw.alertsound);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int Volume = audioManager.getStreamMaxVolume(audioManager.STREAM_MUSIC);
        int CurrentVolume = audioManager.getStreamVolume(audioManager.STREAM_MUSIC);

        SeekBar VolumeRocker = findViewById(R.id.seekBar);
        VolumeRocker.setMax(Volume);
        VolumeRocker.setProgress(CurrentVolume);

        VolumeRocker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(audioManager.STREAM_MUSIC,progress,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        final SeekBar Duration = findViewById(R.id.seekBar2);
        Duration.setMax(mediaPlayer.getDuration());

        Duration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mediaPlayer.seekTo(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Duration.setProgress(mediaPlayer.getCurrentPosition());
            }
        }, 0, 1000);





    }


    public void goback(View view){
        Intent intent = new Intent(Noise.this,MainActivity2.class);
        mediaPlayer.pause();
        startActivity(intent);
    }
}