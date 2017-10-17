package com.zhimadai.cctvmall.zymusicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.zhimadai.cctvmall.zymusicplayer.adapter.PlaylistAdapter;
import com.zhimadai.cctvmall.zymusicplayer.application.AppCache;
import com.zhimadai.cctvmall.zymusicplayer.common.constants.Extras;
import com.zhimadai.cctvmall.zymusicplayer.enums.LoadStateEnum;
import com.zhimadai.cctvmall.zymusicplayer.model.SongListInfo;
import com.zhimadai.cctvmall.zymusicplayer.util.NetworkUtils;
import com.zhimadai.cctvmall.zymusicplayer.util.ViewUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnlineActivity extends AppCompatActivity {

    @BindView(R.id.lv_song_list)
    ListView lvSongList;
    private LinearLayout llLoading;
    private LinearLayout llLoadFail;
    private List<SongListInfo> mSongLists;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online);
        ButterKnife.bind(this);
        llLoading = (LinearLayout) findViewById(R.id.ll_loading);
        llLoadFail = (LinearLayout) findViewById(R.id.ll_load_fail);
        init();
    }

    private void init() {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            ViewUtils.changeViewState(lvSongList, llLoading, llLoadFail, LoadStateEnum.LOAD_FAIL);
            return;
        }
        mSongLists = AppCache.getSongListInfos();
        if (mSongLists.isEmpty()) {
            String[] titles = getResources().getStringArray(R.array.online_music_list_title);
            String[] types = getResources().getStringArray(R.array.online_music_list_type);
            for (int i = 0; i < titles.length; i++) {
                SongListInfo info = new SongListInfo();
                info.setTitle(titles[i]);
                info.setType(types[i]);
                mSongLists.add(info);
            }
        }
        PlaylistAdapter adapter = new PlaylistAdapter(mSongLists);
        lvSongList.setAdapter(adapter);
        lvSongList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SongListInfo songListInfo = mSongLists.get(position);
                Intent intent = new Intent(OnlineActivity.this, OnlineMusicActivity.class);
                intent.putExtra(Extras.MUSIC_LIST_TYPE, songListInfo);
                startActivity(intent);
            }
        });
    }
}
