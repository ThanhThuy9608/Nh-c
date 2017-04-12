package com.example.thanhthuy.nhac_mp3.frangment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thanhthuy.nhac_mp3.ChoiNhacActivity;
import com.example.thanhthuy.nhac_mp3.EShPre;
import com.example.thanhthuy.nhac_mp3.MainActivity;
import com.example.thanhthuy.nhac_mp3.Player;
import com.example.thanhthuy.nhac_mp3.SongPlayListEntity;
import com.example.thanhthuy.nhac_mp3.adapter.BaiHatAdapter;
import com.example.thanhthuy.nhac_mp3.adapter.PlayListEntity;
import com.example.thanhthuy.nhac_mp3.database.PlayListIDU;
import com.example.thanhthuy.nhac_mp3.database.SongPlayListIDR;
import com.example.thanhthuy.nhac_mp3.laymusic.SongMusic;
import com.example.thanhthuy.nhac_mp3.R;
import com.google.gson.Gson;

import java.util.List;

import static com.example.thanhthuy.nhac_mp3.MainActivity.linearLayourABM;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicFragment extends Fragment {

    ListView lv;
    String[] items;

    private static List<PlayListEntity> listPlayListBH;
    // public static PlayListAdapter playListAdapter;
    private String newPlayList;
    private SongPlayListIDR songPlayListIDR;
    Intent intent;
    private PlayListIDU playListIDU;
    public MusicFragment() {
        // Required empty public constructor
    }

    /**
     * Đọc từ SD Card
     * Environment.getExternalStorageDirectory().getAbsolutePath():
     * để lấy đường dẫn trên SD Card
     */
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        playListIDU = new PlayListIDU(getActivity());

        songPlayListIDR = new SongPlayListIDR(getActivity());

        final View v = inflater.inflate(R.layout.fragment_music, container, false);
        lv = (ListView) v.findViewById(R.id.lvBaiHat);
        final BaiHatAdapter adapter = new BaiHatAdapter(getActivity(), R.layout.bai_hat, MainActivity.listSongEntity);
        lv.setAdapter(adapter);

        final String listSerializedToJson = new Gson().toJson(MainActivity.listSongEntity);

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                // SongMusic item = (SongMusic) parent.getItemAtPosition(position);
//                Intent intent = new Intent(getActivity(), ChoiNhacActivity.class);
//                Gson gson = new Gson();
//
//                // chuyển chuỗi listbaihat thành string
//                String json = gson.toJson(listSerializedToJson);
//                intent.putExtra("arr_song", json);
//                intent.putExtra("position", position);
//
//                //getActivity().startActivity(intent);
//                startActivity(intent);
//            }
//        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                intent = new Intent(getActivity(), Player.class);
                intent.putExtra("songIndex", position);
                intent.putExtra("list", listSerializedToJson);
                getActivity().startService(intent);
            }
        });


        /**
         * Đang lỗi cần sửa....
         */
        linearLayourABM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity(), ChoiNhacActivity.class);
                Gson gson = new Gson();

                // chuyển chuỗi listbaihat thành string
                String json = gson.toJson(listSerializedToJson);
                intent.putExtra("arr_song", json);
                //intent.putExtra("position", position);

                //getActivity().startActivity(intent);
                startActivity(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, long id) {

                final EShPre shPre = EShPre.gIns(getActivity());
                CharSequence[] choose = null;
                final String idSong = String.valueOf(MainActivity.listSongEntity.get(position).getId());
                if (shPre.checkFavoriteById(idSong)) {
                    choose = new CharSequence[]{"Thêm vào Playlist"};
                } else {
                    choose = new CharSequence[]{"Thêm vào Playlist"};
                }

                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setItems(choose, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int pos) {
                        if (pos == 0) {
                            showDialogPlaylist(inflater, idSong);

                        } else if (pos == 1) {

                            if (shPre.checkFavoriteById(idSong)) {
                                shPre.removeFavoriteById(idSong);
                            } else {
                                shPre.setFavorite(String.valueOf(idSong));
                            }
                        }
                    }
                });
                builder.show();

                return false;
            }
        });

        return v;
    }


    private void showDialogPlaylist(LayoutInflater inflater, final String id_Song) {
        listPlayListBH = playListIDU.getAll();
        View view1 = inflater.inflate(R.layout.fragment_play_list, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("Thêm hoặc chọn PlayList");
        // alertDialog.setIcon(R.drawable.ic_favorite_add);
        alertDialog.setCancelable(false);
        ListView lvPlayListDialog = (ListView) view1.findViewById(R.id.listViewDialogAddPlayList);

//        playListAdapter = new PlayListAdapter(getActivity(), R.layout.tung_play_list, listPlayListBH);
//        lvPlayListDialog.setAdapter(playListAdapter);

        lvPlayListDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int id_PlayList = listPlayListBH.get(position).getId();
                SongPlayListEntity songPlay = new SongPlayListEntity();
                songPlay.setIdSong(Integer.parseInt(id_Song));
                songPlay.setIdPlaylist(id_PlayList);

                if (songPlayListIDR.insertKeysearch(songPlay) == 1) {
                    Toast.makeText(getActivity(), "Thêm Thành công bài hát vào Album : "
                            + listPlayListBH.get(position).getName().toUpperCase(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Bài hát đã tồn tại trong Playlist ", Toast.LENGTH_SHORT).show();
                }

                alertDialog.dismiss();
            }
        });


        final EditText editTextAdd = (EditText) view1.findViewById(R.id.editTextDialogAdd);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newPlayList = editTextAdd.getText().toString().trim();
                if (!newPlayList.equals("")) {
                    if (songPlayListIDR.insertPlayList(newPlayList) == 0) {

                        SongPlayListIDR al = new SongPlayListIDR(getActivity());

                        SongPlayListEntity song = new SongPlayListEntity();
                        song.setIdSong(Integer.parseInt(id_Song));
                        song.setIdPlaylist(al.getIdByName(newPlayList));
                        songPlayListIDR.insertKeysearch(song);

                        Toast.makeText(getActivity(), "Đã thêm Bài hát vào PlayList: " + newPlayList.toUpperCase() + " thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Lỗi thêm PlayList " + newPlayList + " !!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Thêm PlayList không thành công do chuỗi rỗng!!!", Toast.LENGTH_SHORT).show();
                }
            }

        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.setView(view1);
        alertDialog.show();

    }
}


