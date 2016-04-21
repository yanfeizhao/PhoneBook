package com.my.phonebook.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences�洢���ݷ�ʽ������
 */
public class SharedPrefsUtil {
	
	
	public final static String FILE_NAMW = "login";//�����û�����������ļ���
//-----�洢����-----
	public static void putValue(Context context, String key, int value) {
		Editor sp = context.getSharedPreferences(FILE_NAMW, Context.MODE_PRIVATE)
				.edit();
		sp.putInt(key, value);
		sp.commit();
	}

	public static void putValue(Context context, String key, boolean value) {
		Editor sp = context.getSharedPreferences(FILE_NAMW, Context.MODE_PRIVATE)
				.edit();
		sp.putBoolean(key, value);
		sp.commit();
	}

	public static void putValue(Context context, String key, String value) {
		Editor sp = context.getSharedPreferences(FILE_NAMW, Context.MODE_PRIVATE)
				.edit();
		sp.putString(key, value);
		sp.commit();
	}
	
	
	
//----------��ȡ����-------------
	public static int getValue(Context context, String key, int defValue) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAMW,
				Context.MODE_PRIVATE);
		int value = sp.getInt(key, defValue);
		return value;
	}

	public static boolean getValue(Context context, String key, boolean defValue) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAMW,
				Context.MODE_PRIVATE);
		boolean value = sp.getBoolean(key, defValue);
		return value;
	}

	public static String getValue(Context context, String key, String defValue) {
		SharedPreferences sp = context.getSharedPreferences(FILE_NAMW,
				Context.MODE_PRIVATE);
		String value = sp.getString(key, defValue);
		return value;
	}
	
 
}