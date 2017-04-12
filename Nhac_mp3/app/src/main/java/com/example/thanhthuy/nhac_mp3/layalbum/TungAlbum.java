package com.example.thanhthuy.nhac_mp3.layalbum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.thanhthuy.nhac_mp3.R;

import java.util.List;

public class TungAlbum extends BaseAdapter {
    Context myContext;
    int myLayout;
    List<AlbumEntity> listAlbum;

    public TungAlbum(Context context, int layout, List<AlbumEntity> baiHatList) {
        myContext = context;
        myLayout = layout;
        listAlbum = baiHatList;
    }

    @Override
    public int getCount() {
        return listAlbum.size();
    }

    @Override
    public Object getItem(int position) {
        return listAlbum.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout, null);

        TextView txtvAlbum = (TextView) convertView.findViewById(R.id.textViewDongAlbum);
        txtvAlbum.setText(listAlbum.get(position).getName());

        return convertView;
    }

}
