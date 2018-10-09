package com.example.yangfan.musicplayertest;

/**
 * 作者：xjtu_yf
 * 日期：2018/10/6
 */
public class MusicStatus {
    private boolean isPlaying = false;
    private String musicName = "";
    private int currentTime = 0;
    private int duration = 0;
    private String path = "";

    public void setIsPlaying(boolean ip){
        this.isPlaying = ip;
    }
    public boolean getIsPlaying(){return this.isPlaying;}

    public void setMusicName(String str){this.musicName = str;}
    public String getMusicName(){return this.musicName;}

    public void setCurrentTime(int num){this.currentTime=num;}
    public int getCurrentTime(){return this.currentTime;}

    public void setDuration(int num){this.duration = num;}
    public int getDuration(){return this.duration;}

    public void setPath(String str){this.path = str;}
    public String getPath(){return this.path;}

    public void setParam(boolean isPlaying, String path, String name, int duration, int currentTime){
        this.isPlaying = isPlaying;
        this.path = path;
        this.musicName = name;
        this.duration = duration;
        this.currentTime = currentTime;
    }
}
