package com.example.yangfan.musicplayertest;

import android.media.MediaPlayer;
import android.util.Log;

import com.example.yangfan.musicplayertest.MediaProcess;

import java.io.IOException;



/**
 * 作者：xjtu_yf
 * 日期：2018/10/6
 */
public class InitMusicProcess extends MediaProcess {
    private String path = "";
    private String name = "";
    private int time = 0;
    public InitMusicProcess(String path, String name){
        this.path = path;
        this.name = name;
    }
    public InitMusicProcess(String path, String name, int time){
        this.path = path;
        this.name = name;
        this.time = time;
    }
    @Override
    public String getPath(){
        return this.path;
    }
    @Override
    public String getName(){
        return this.name;
    }
    @Override
    public void process(MediaPlayer player) throws IOException {
        synchronized (player){
            player.reset();
            player.setDataSource(this.path);
            player.prepare();
            if(time != 0){
                player.seekTo(time);
            }
        }
    }
}
