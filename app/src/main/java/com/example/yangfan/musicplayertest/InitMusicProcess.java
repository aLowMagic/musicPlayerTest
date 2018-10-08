package com.example.yangfan.musicplayertest;

import android.media.MediaPlayer;

import com.example.yangfan.musicplayertest.MediaProcess;

import java.io.IOException;



/**
 * 作者：xjtu_yf
 * 日期：2018/10/6
 */
public class InitMusicProcess extends MediaProcess {
    private String path = "";
    private String name = "";
    public InitMusicProcess(String path, String name){
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
    public void process(MediaPlayer player) throws IOException {
        player.setDataSource(this.path);
        player.prepare();
    }
}
