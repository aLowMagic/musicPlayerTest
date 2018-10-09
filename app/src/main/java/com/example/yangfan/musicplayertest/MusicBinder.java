package com.example.yangfan.musicplayertest;

import android.os.Binder;

/**
 * 作者：xjtu_yf
 * 日期：2018/10/1
 */
public class MusicBinder extends Binder {
    private boolean isPlaying = false;
    private boolean needNextMusic = false;
    private int currentTime = 0;
    private int duration = 0;
    private String musicName = "";
    private String path = "";

    public boolean getPlayStatus(){
        return isPlaying;
    }
    public void setPlayStatus(boolean play){
        this.isPlaying = play;
    }

    public boolean getNeedNextMusic(){
        boolean messager = needNextMusic;
        needNextMusic = false;
        return messager;
    }
    public void setNeedNextMusic(boolean need){
        this.needNextMusic = need;
    }

    public int getCurrentTime(){
        return currentTime;
    }
    public void setCurrentTime(int time){
        this.currentTime = time;
    }

    public int getDuration(){
        return this.duration;
    }
    public void setDuration(int time){
        this.duration = time;
    }

    public String getMusicName(){return this.musicName;}
    public void setMusicName(String name){this.musicName = name;}

    public String getPath(){return this.path;}
    public void setPath(String path){this.path = path;}
}

