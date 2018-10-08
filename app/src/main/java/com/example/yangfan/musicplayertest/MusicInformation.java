package com.example.yangfan.musicplayertest;

/**
 * 作者：xjtu_yf
 * 日期：2018/10/7
 */
public class MusicInformation {
    private String musicName;
    private String author = "variety musician";
    private String path = "";

    public void setMusicName(String name){
        this.musicName = name;
    }
    public String getMusicName(){
        return this.musicName;
    }

    public void setAuthor(String author){
        this.author = author;
    }
    public String getAuthor(){
        return this.author;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
