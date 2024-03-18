package com.yangna.lbdsp.videoCom;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class MyPagerAdapter extends PagerAdapter {

    ArrayList<PlayerInfo> playerInfoList = new ArrayList<>();


    @Override
    public int getCount() {
        return 0;
    }

    public PlayerInfo findPlayerInfo(int position) {
        for (int i = 0; i < playerInfoList.size(); i++) {
            PlayerInfo playerInfo = playerInfoList.get(i);
            if (playerInfo.pos == position) {
                return playerInfo;
            }
        }
        return null;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return false;
    }
}
