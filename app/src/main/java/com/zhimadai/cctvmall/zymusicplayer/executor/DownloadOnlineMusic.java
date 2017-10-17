package com.zhimadai.cctvmall.zymusicplayer.executor;

import android.app.Activity;

import com.zhimadai.cctvmall.zymusicplayer.http.DownloadInfo;
import com.zhimadai.cctvmall.zymusicplayer.http.HttpCallback;
import com.zhimadai.cctvmall.zymusicplayer.http.HttpClient;
import com.zhimadai.cctvmall.zymusicplayer.model.OnlineMusic;


/**
 * 下载音乐
 * Created by wcy on 2016/1/3.
 */
public abstract class DownloadOnlineMusic extends DownloadMusic {
    private OnlineMusic mOnlineMusic;

    public DownloadOnlineMusic(Activity activity, OnlineMusic onlineMusic) {
        super(activity);
        mOnlineMusic = onlineMusic;
    }

    @Override
    protected void download() {
        final String artist = mOnlineMusic.getArtist_name();
        final String title = mOnlineMusic.getTitle();

        // 获取歌曲下载链接
        HttpClient.getMusicDownloadInfo(mOnlineMusic.getSong_id(), new HttpCallback<DownloadInfo>() {
            @Override
            public void onSuccess(DownloadInfo response) {
                if (response == null || response.getBitrate() == null) {
                    onFail(null);
                    return;
                }

                downloadMusic(response.getBitrate().getFile_link(), artist, title, null);
                onExecuteSuccess(null);
            }

            @Override
            public void onFail(Exception e) {
                onExecuteFail(e);
            }
        });
    }
}
