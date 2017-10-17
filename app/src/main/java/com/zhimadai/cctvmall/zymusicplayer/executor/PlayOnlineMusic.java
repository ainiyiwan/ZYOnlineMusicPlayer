package com.zhimadai.cctvmall.zymusicplayer.executor;

import android.app.Activity;

import com.zhimadai.cctvmall.zymusicplayer.http.DownloadInfo;
import com.zhimadai.cctvmall.zymusicplayer.http.HttpCallback;
import com.zhimadai.cctvmall.zymusicplayer.http.HttpClient;
import com.zhimadai.cctvmall.zymusicplayer.model.Music;
import com.zhimadai.cctvmall.zymusicplayer.model.OnlineMusic;


/**
 * 播放在线音乐
 * Created by wcy on 2016/1/3.
 */
public abstract class PlayOnlineMusic extends PlayMusic {
    private OnlineMusic mOnlineMusic;

    public PlayOnlineMusic(Activity activity, OnlineMusic onlineMusic) {
        super(activity, 3);
        mOnlineMusic = onlineMusic;
    }

    @Override
    protected void getPlayInfo() {
        String artist = mOnlineMusic.getArtist_name();
        String title = mOnlineMusic.getTitle();

        music = new Music();
        music.setType(Music.Type.ONLINE);
        music.setTitle(title);
        music.setArtist(artist);
        music.setAlbum(mOnlineMusic.getAlbum_title());


        // 获取歌曲播放链接
        HttpClient.getMusicDownloadInfo(mOnlineMusic.getSong_id(), new HttpCallback<DownloadInfo>() {
            @Override
            public void onSuccess(DownloadInfo response) {
                if (response == null || response.getBitrate() == null) {
                    onFail(null);
                    return;
                }

                String s = response.getBitrate().getFile_link();
                int d = response.getBitrate().getFile_duration() * 1000;
                music.setPath(response.getBitrate().getFile_link());
                music.setDuration(response.getBitrate().getFile_duration() * 1000);
                checkCounter();
            }

            @Override
            public void onFail(Exception e) {
                onExecuteFail(e);
            }
        });
    }

}
