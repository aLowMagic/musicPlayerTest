package com.example.yangfan.musicplayertest;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import com.example.yangfan.musicplayertest.MediaProcess;

import java.io.IOException;
import java.text.DecimalFormat;

public class MusicPlayerService extends Service {
    private MediaPlayer player = new MediaPlayer();
    final MusicBinder mrBinder = new MusicBinder();
    private String musicName = "";
    private String musicPath = "";

    public MusicPlayerService() {
    }

    @Override
    public void onCreate(){
        super.onCreate();
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mrBinder.setNeedNextMusic(true);
            }
        });
        Log.i("msg", "service \'musicPlayerService\' created succeed");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        player.stop();
        Log.i("msg", "service \'musicPlayerService\'  destroied");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try{
            MediaProcess mp = (MediaProcess) intent.getSerializableExtra("msgClass");
            musicName = mp.getName();
            musicPath = mp.getPath();
            mp.process(player);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public IBinder onBind(Intent intent) {


        new Thread(){
            @Override
            public void run(){
                while(true){
                    try {
                        sleep(200);
                        synchronized(player){
                            mrBinder.setPlayStatus(player.isPlaying());
                            if(player.isPlaying()){
                                mrBinder.setCurrentTime(player.getCurrentPosition());
                                mrBinder.setDuration(player.getDuration());
                            }
                            mrBinder.setMusicName(musicName);
                            mrBinder.setPath(musicPath);
                            Log.i("send", mrBinder.getCurrentTime()+"");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        return mrBinder;
    }
}
