package com.example.yangfan.musicplayertest;

import android.Manifest;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    private MusicBinder binder;
    private List<File> musicList = new ArrayList<File>();
    private int currentMusic = 0;
    private MusicStatus musicStatus = new MusicStatus();
    private boolean isRunning = true;
    private Intent intent = new Intent();
    private MusicServiceConnection serviceConnection = new MusicServiceConnection();
    private Button btnPlay;
    private SeekBar processBar;
    private TextView musicNameText;
    private TextView musicProcessText;
    private ListView music_listiview;
    private boolean isSeeking = false;
    private MusicInformationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlay = findViewById(R.id.playButton);
        processBar = findViewById(R.id.processBar);
        musicNameText = findViewById(R.id.musicName);
        musicProcessText = findViewById(R.id.processTime);
        music_listiview = findViewById(R.id.music_list);
        getMusics();
        requestPerm();
        loadMusicList();
        musicListChoice();
        Map initData = getMediaData();
        intent.setClass(this, MusicPlayerService.class);
        if(initData.get("path")=="" && initData.get("name")==""){
            //第一次进入程序，需要给mediaplayer一个新文件以刷新mediaplayer的状态
            intent.putExtra("msgClass", new InitMusicProcess(musicList.get(currentMusic).toString(), musicList.get(currentMusic).getName()));
            //后续更新前台状态
        }
        startService(intent);
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
        addSeekBarFunc(processBar);
    }

    @Override
    protected void onDestroy() {
        isRunning = false;
        SharedPreferences sp = getSharedPreferences("data.xml", MODE_PRIVATE);
        SharedPreferences.Editor dataEditor = sp.edit();
        dataEditor.remove("path");
        dataEditor.remove("name");
        dataEditor.putString("path", musicStatus.getPath());
        dataEditor.putString("name", musicStatus.getMusicName());
        dataEditor.putString("duration", musicStatus.getDuration());
        dataEditor.putString("currentTime", musicStatus.getCurrentTime());
        unbindService(serviceConnection);
        super.onDestroy();
    }

    public class MusicServiceConnection implements ServiceConnection{
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder = (MusicBinder) service;
            new Thread(new Runnable() {
                Handler handler = new Handler();
                @Override
                public void run(){
                    while(isRunning){
                        try {
                            sleep(300);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                musicStatus.setParam(binder.getPlayStatus(), binder.getPath(), binder.getMusicName(), binder.getDuration(), binder.getCurrentTime());
                                if(!isSeeking){
                                    setUI();
                                }
                                if(binder.getNeedNextMusic()){
                                    nextMusic();
                                }
                            }
                        });
                    }
                }
            }).start();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    public void requestPerm() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void nextMusic(View v){
        int num = (++currentMusic)%musicList.size();
        intent.putExtra("msgClass", new NextMusic(musicList.get(num).toString(), musicList.get(num).getName()));
        startService(intent);
    }
    public void nextMusic(){
        int num = (++currentMusic)%musicList.size();
        intent.putExtra("msgClass", new NextMusic(musicList.get(num).toString(), musicList.get(num).getName()));
        startService(intent);
    }

    public void getMusics(){
        String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/Music/";
        File mp3Dir=new File(path);
        if(mp3Dir.isDirectory()){
            File[] fs=mp3Dir.listFiles();
            for (File f:fs){
                Log.i("msg",f.getAbsolutePath());
                musicList.add(f);
            }
        }
        else{
            Log.i("msg", "_______________");
        }
    }

    public Map getMediaData(){
        SharedPreferences sp = getSharedPreferences("data.xml", MODE_PRIVATE);
//        SharedPreferences.Editor dataEditor = sp.edit();
        String loadMusicPath = sp.getString("path", "");
        String loadMusicName = sp.getString("musicName", "");
        Map data = new HashMap();
        data.put("path", loadMusicPath);
        data.put("name", loadMusicName);
        return data;
    }

    public void setUI(){
        if(musicStatus.getIsPlaying()){
            btnPlay.setText("点击暂停");
            btnPlay.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    intent.putExtra("msgClass", new PauseMusicProcess(musicStatus.getPath().toString(), musicStatus.getMusicName()));
                    startService(intent);
                }
            });
        }
        if(!musicStatus.getIsPlaying()){
            btnPlay.setText("点击播放");
            btnPlay.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    intent.putExtra("msgClass", new StartMusicProcess(musicStatus.getPath().toString(), musicStatus.getMusicName()));
                    startService(intent);
                }
            });
        }
        musicNameText.setText(musicStatus.getMusicName());
        processBar.setMax((int)(Double.parseDouble(musicStatus.getDuration())*60000));
        musicProcessText.setText(musicStatus.getCurrentTime());
        processBar.setProgress((int)(Double.parseDouble(musicStatus.getCurrentTime())*60000));
        adapter.setCurrentMusic(currentMusic);
        adapter.notifyDataSetChanged();
    }

    public void loadMusicList(){
        List<MusicInformation> viewList= new ArrayList<>();
        for(int i=0; i<this.musicList.size(); i++){
            MusicInformation item = new MusicInformation();
            item.setMusicName(this.musicList.get(i).getName());
            //获取作者名函数暂时搁置
            item.setAuthor("");
            item.setPath(this.musicList.get(i).getName());
            viewList.add(item);
        }

        ListView musicListTable =findViewById(R.id.music_list);
        adapter = new MusicInformationAdapter(this, R.layout.music_list, viewList);
        adapter.setCurrentMusic(currentMusic);
        musicListTable.setAdapter(adapter);
    }

    public void refreshCurrentMusic(){
        String path = musicStatus.getPath();
        Iterator iter = musicList.iterator();
        int i = 0;
        while(path != iter.next()){
            i++;
        }
        if(i > musicList.size()){
            i = 0;
        }
        currentMusic = i;
    }

    public void addSeekBarFunc(SeekBar bar){
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                DecimalFormat fmt = new DecimalFormat("######0.00");
                musicProcessText.setText(fmt.format((double)processBar.getProgress()/60000));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeeking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                intent.putExtra("msgClass",
                        new SeekMusicProcess(musicStatus.getPath(), musicStatus.getMusicName(), processBar.getProgress()));
                startService(intent);
                isSeeking = false;
            }
        });
    }

    public void musicListChoice(){
        music_listiview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentMusic = i;
                intent.putExtra("msgClass", new NextMusic(musicList.get(currentMusic).toString(), musicList.get(currentMusic).getName()));
                startService(intent);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
