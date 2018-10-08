package com.example.yangfan.musicplayertest;

/**
 * 作者：xjtu_yf
 * 日期：2018/10/6
 */
public class MusicStatus {
    private boolean isPlaying = false;
    private String musicName = "";
    private String currentTime = "0.00";
    private String duration = "0.00";
    private String path = "";

    public void setIsPlaying(boolean ip){
        this.isPlaying = ip;
    }
    public boolean getIsPlaying(){return this.isPlaying;}

    public void setMusicName(String str){this.musicName = str;}
    public String getMusicName(){return this.musicName;}

    public void setCurrentTime(String str){this.currentTime=str;}
    public String getCurrentTime(){return this.currentTime;}

    public void setDuration(String str){this.duration = str;}
    public String getDuration(){return this.duration;}

    public void setPath(String str){this.path = str;}
    public String getPath(){return this.path;}

    public void setParam(boolean isPlaying, String path, String name, String duration, String currentTime){
        this.isPlaying = isPlaying;
        this.path = path;
        this.musicName = name;
        this.duration = duration;
        this.currentTime = currentTime;
    }
}
