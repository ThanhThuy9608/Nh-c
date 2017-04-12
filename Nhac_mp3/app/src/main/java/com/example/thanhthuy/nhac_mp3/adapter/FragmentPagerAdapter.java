package com.example.thanhthuy.nhac_mp3.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.thanhthuy.nhac_mp3.frangment.FragmentNgheSi;
import com.example.thanhthuy.nhac_mp3.frangment.MusicFragment;
import com.example.thanhthuy.nhac_mp3.frangment.AlbumFragment;
import com.example.thanhthuy.nhac_mp3.frangment.PlayListFragment;


public class FragmentPagerAdapter extends FragmentStatePagerAdapter {
    public FragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag=null;
        switch (position){
            case 0:
                frag=new MusicFragment();
                break;
            case 1:
                frag=new AlbumFragment();
                break;
            case 2:
                frag = new FragmentNgheSi();
                break;
            case 3:
                frag = new PlayListFragment();
                break;
        }
        return frag;
    }
    @Override
    public int getCount() {
        return 4;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title="Bài hát";
                break;
            case 1:
                title="Album";
                break;
            case 2:
                title="Nghệ sĩ";
                break;
            case 3:
                title = "PlayList";
                break;
        }
        return title;
    }

}
