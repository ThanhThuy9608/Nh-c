package com.example.thanhthuy.nhac_mp3.laymusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.thanhthuy.nhac_mp3.R;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by TTTTT on 10/19/2016.
 */
public class TungBaiHat extends BaseAdapter {
    Context myContext;
    int myLayout;
    List<SongMusic> listBaiHat;

    public TungBaiHat(Context context, int layout, List<SongMusic> baiHatList){
        myContext = context;
        myLayout = layout;
        listBaiHat = baiHatList;

    }
    @Override
    public int getCount() {
        return listBaiHat.size();
    }

    @Override
    public Object getItem(int position) {
        return listBaiHat.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(myLayout, null);
        // ánh xạ và gán giá trị
        TextView txtTenBaiHat = (TextView) convertView.findViewById(R.id.titlebaihat);
        txtTenBaiHat.setText(listBaiHat.get(position).getNameMusic());

        TextView txtTenCaSi = (TextView) convertView.findViewById(R.id.artist);
        txtTenCaSi.setText(listBaiHat.get(position).getArtist());

        TextView txtDuration = (TextView) convertView.findViewById(R.id.duration);
        txtDuration.setText(listBaiHat.get(position).getDuration());

        long ltime = Long.parseLong(String.valueOf(listBaiHat.get(position).getDuration()));
        txtDuration.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes((long) ltime),
                TimeUnit.MILLISECONDS.toSeconds((long) ltime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) ltime))));

        return convertView;
    }
}

