package com.example.yangfan.musicplayertest;

import android.media.MediaPlayer;
import android.util.Log;

import com.example.yangfan.musicplayertest.MediaProcess;

/**
 * 作者：xjtu_yf
 * 日期：2018/10/2
 */
public class StartMusicProcess extends MediaProcess{
    private String path = "";
    private String name = "";
    public StartMusicProcess(String path, String name){
        this.path = path;
        this.name = name;
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
    public void process(MediaPlayer player) throws Exception{
        player.start();
    }
}
