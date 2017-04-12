package com.example.thanhthuy.nhac_mp3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.thanhthuy.nhac_mp3.adapter.PlayListEntity;

import java.util.List;

public class PlayListAdapter extends BaseAdapter {
    Context myContext;
    int myLayout;
    List<PlayListEntity> listPlayList;

    public PlayListAdapter(Context context, int layout, List<PlayListEntity> playListEntityListList) {
        myContext = context;
        myLayout = layout;
        listPlayList = playListEntityListList;
    }

    @Override
    public int getCount() {
        return listPlayList.size();
    }

    @Override
    public Object getItem(int position) {
        return listPlayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout, null);

        TextView txtvP = (TextView) convertView.findViewById(R.id.textViewDongPlayList);
        txtvP.setText(listPlayList.get(position).getName());

        return convertView;
    }

    public int getIdByName(String newPlayList) {
        return 0;
    }
}
