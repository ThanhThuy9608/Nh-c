package com.example.thanhthuy.nhac_mp3.frangment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.thanhthuy.nhac_mp3.R;
import com.example.thanhthuy.nhac_mp3.adapter.PlayListEntity;
import com.example.thanhthuy.nhac_mp3.database.PlayListIDU;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayListFragment extends Fragment {

    private String newPlayList;
    public static List<PlayListEntity> playListEntities;

    ListView lvPlayList;
    PlayListIDU playListIDU;
    public static PlayListEntity playListEntity;

    public PlayListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_list, container, false);
    }

}
