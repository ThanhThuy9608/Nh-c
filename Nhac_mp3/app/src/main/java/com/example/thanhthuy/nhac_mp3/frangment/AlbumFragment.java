package com.example.thanhthuy.nhac_mp3.frangment;


import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thanhthuy.nhac_mp3.ListSongAlbumActivity;
import com.example.thanhthuy.nhac_mp3.layalbum.Albums;
import com.example.thanhthuy.nhac_mp3.layalbum.AllAlbum;
import com.example.thanhthuy.nhac_mp3.layalbum.TungAlbum;
import com.example.thanhthuy.nhac_mp3.layalbum.AlbumEntity;
import com.example.thanhthuy.nhac_mp3.R;
import com.example.thanhthuy.nhac_mp3.laymusic.SongMusic;
import com.example.thanhthuy.nhac_mp3.laymusic.Songs;

import java.util.ArrayList;
import java.util.List;

public class AlbumFragment extends Fragment {

    private ListView listViewAlbum;
    private AllAlbum allAlbum;
    public static ArrayList<AlbumEntity> listAlbum;

    public AlbumFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.fragment_tab_album, container, false);

        listViewAlbum = (ListView) v.findViewById(R.id.listViewAlbum);

//        Albums albums = new Albums();
//        listAlbum = (ArrayList<AlbumEntity>) albums.getAlbum(getActivity());
//
        // lấy tất cả album trong database từ thẻ nhớ đã lưu vào database

        allAlbum = new AllAlbum(getActivity());
        listAlbum = allAlbum.getAllAlbum();
        final TungAlbum arrayAdapter = new TungAlbum(getActivity(), R.layout.activity_dong_album, listAlbum);

        listViewAlbum.setAdapter(arrayAdapter);

        listViewAlbum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(),listAlbum.get(position).getId()+ " pos "+ position , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ListSongAlbumActivity.class);
                intent.putExtra("idAlbum", listAlbum.get(position).getId());
               // intent.putExtra("idPlayList", listAlbum.get(position).getClass());
                startActivity(intent);
            }
        });

        return v;


    }

}
