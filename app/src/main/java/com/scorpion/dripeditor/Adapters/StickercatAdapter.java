package com.scorpion.dripeditor.Adapters;


import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.scorpion.dripeditor.Activity.StickerTabActivity;
import com.scorpion.dripeditor.Activity.StickermainActivity;


public class StickercatAdapter extends FragmentPagerAdapter {
    Context context;
    String[] tabTitles = new String[]{"Cat Face"};

    public StickercatAdapter(FragmentManager fm, Context con) {
        super(fm);
        this.context = con;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                StickerTabActivity.chk=0;
                return new StickermainActivity();

            default:
                StickerTabActivity.chk=0;
                return new StickermainActivity();
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

}
