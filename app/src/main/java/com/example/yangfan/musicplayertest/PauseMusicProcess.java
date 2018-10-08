package com.example.yangfan.musicplayertest;

import android.media.MediaPlayer;

/**
 * 作者：xjtu_yf
 * 日期：2018/10/3
 */
public class PauseMusicProcess extends MediaProcess {
    private String path = "";
    private String name = "";
    public PauseMusicProcess(String path, String name){
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
    public void process(MediaPlayer player) throws Exception {
        if(player.isPlaying()){
            player.pause();
        }
        else if(player!=null && !player.isPlaying()){
            player.start();
        }
    }
}
