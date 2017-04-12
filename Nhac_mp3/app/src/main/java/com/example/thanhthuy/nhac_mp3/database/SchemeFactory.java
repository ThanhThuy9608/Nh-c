package com.example.thanhthuy.nhac_mp3.database;

import com.example.thanhthuy.nhac_mp3.layalbum.AlbumScheme;

public class SchemeFactory {
	public IScheme getScheme(EScheme scheme){
		switch (scheme) {
			case Song:
				return new SongScheme();
			case Album:
				return new AlbumScheme();
			case PlayList:
				return new PlaylistScheme();
			case Song_Playlist:
				return new SongPlaylistScheme();
		
		default:
			return null;
		}
	};
}
