package com.example.yangfan.musicplayertest;

import android.media.MediaPlayer;
import android.os.Binder;
import android.provider.MediaStore;

import java.io.Serializable;

/**
 * 作者：xjtu_yf
 * 日期：2018/10/1
 */
public abstract class MediaProcess implements Serializable{
    public abstract String getPath();
    public abstract String getName();
    public abstract void process(MediaPlayer player) throws Exception;
}
