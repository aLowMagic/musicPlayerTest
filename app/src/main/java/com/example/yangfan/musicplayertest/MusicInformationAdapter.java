package com.example.yangfan.musicplayertest;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * 作者：xjtu_yf
 * 日期：2018/10/7
 */
public class MusicInformationAdapter extends ArrayAdapter<MusicInformation> {
    private List<MusicInformation> musicList;
    private int currentMusic = 0;

    public MusicInformationAdapter(Context context, int resource, List<MusicInformation> musicList){
        super(context, resource, musicList);
        this.musicList = musicList;
    }
    @Override
    public int getCount(){
        return musicList.size();
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View view= LayoutInflater
                .from(getContext())
                .inflate(R.layout.music_list,parent,false);

        MusicInformation ml = musicList.get(position);

        TextView musicName = view.findViewById(R.id.music_name);
        TextView author =  view.findViewById(R.id.music_author);
        musicName.setText(ml.getMusicName());
        if(position == getCurrentMusic()){
            view.findViewById(R.id.music_item).setBackgroundColor(Color.parseColor("#ccccff"));
        }
        else{
            view.findViewById(R.id.music_item).setBackgroundColor(Color.parseColor("#ffffff"));
        }
        return view;
    }

    public void setCurrentMusic(int i){
        this.currentMusic = i;
    }
    public int getCurrentMusic(){
        return this.currentMusic;
    }
}
