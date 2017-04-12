package com.example.thanhthuy.nhac_mp3;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EShPre {
	// SharedPreferences: một lớp cho phép lưu trữ và nhận dữ liệu theo key-value
	private SharedPreferences sharedPreferences;

	private static EShPre instance = null;
	
	public static EShPre gIns(Context context) {
		if (instance == null) {
			instance = new EShPre(context);
		}
		return instance;
	}
	
	private EShPre(Context context) {
		sharedPreferences = context.getSharedPreferences("Play", Context.MODE_PRIVATE);
	}

	public void clearAll(){
		Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();
	}

	Gson gson = new Gson();
	private String FAVORITE = "favorite";

	public String getFavorite() {
		return sharedPreferences.getString(FAVORITE, "");
	}

	public String[] getArrFavorite() {
		String[] arr = gson.fromJson(sharedPreferences.getString(FAVORITE, ""), String[].class);
		return arr;
	}

	public void setFavorite(String id) {
		String[] currentFavorite = gson.fromJson(getFavorite(), String[].class);
		List<String> list = new ArrayList<>();
		if (currentFavorite != null){
			list.addAll(Arrays.asList(currentFavorite));
		}
		list.add(id);
		String newFavorite = gson.toJson(list);
		Editor editor = sharedPreferences.edit();
		editor.putString(FAVORITE, newFavorite);
		editor.commit();
	}

	/**
	 * return true Nếu id đã tồn tại yêu thích
	 * return false Nếu id này chưa có trong yêu thích
	 * @return
     */
	public boolean checkFavoriteById(String id){
		String jsonfavorite = getFavorite();
		int indexOf = jsonfavorite.indexOf(id);
		if (indexOf == -1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * return true neu remove thành công
	 * return false nếu remove ko thành công
	 * @param id
     * @return
     */
	public boolean removeFavoriteById(String id){
		try {
			String[] currentFavorite = gson.fromJson(getFavorite(), String[].class);
			List<String> list = new ArrayList<>();
			if (currentFavorite != null){
				list.addAll(Arrays.asList(currentFavorite));
			}
			for (int i = 0; i < list.size(); i++) {
				if (id.equals(list.get(i))) {
					list.remove(i);
				}
			}
			String newFavorite = gson.toJson(list);
			Editor editor = sharedPreferences.edit();
			editor.putString(FAVORITE, newFavorite);
			editor.commit();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	
}
