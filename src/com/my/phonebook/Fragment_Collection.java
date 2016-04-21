package com.my.phonebook;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.my.phonebook.adapter.MyListAdapter;
import com.my.phonebook.db.DBHelper;

public class Fragment_Collection extends Fragment {

	private ArrayList<String> mContactsName = new ArrayList<String>(); // 联系人名称
	private ArrayList<String> mContactsNumber = new ArrayList<String>(); // 联系人电话
	private ArrayList<String> mContactsPhontoId = new ArrayList<String>(); // 联系人头像ID
	private ArrayList<String> mContactsId = new ArrayList<String>(); // 联系人ID

	private Context mContext = null;
	private ListView mListView = null;
	private MyListAdapter myAdapter = null;

	String[] item_list = { "打电话", "发短信", "取消收藏" };

	private DBHelper helper;
	private SQLiteDatabase db;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment, container, false);

		mListView = (ListView) view.findViewById(R.id.lv_lianxiren);
		mContext = Fragment_Collection.this.getActivity();

		helper = new DBHelper(mContext, "contact.db");
		db = helper.getWritableDatabase();
		// 这里从数据库获得联系人的信息
		getContactsData();

		myAdapter = new MyListAdapter(mContext, mContactsName, mContactsNumber,
				mContactsPhontoId, mContactsId);
		mListView.setAdapter(myAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					final int position, long id) {
				showDialog(position);//点击事件就是弹出一个dialog，然后进行一系列的操作。
			}
		});

		return view;

	}

	/**
	 * 显示出一个dialog，显示出可以对已经收藏的联系人的一系列操作，并实现这些操作。
	 * @param position
	 */
	private void showDialog(final int position) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(mContactsName.get(position));// 设置标题为点击的联系人的名字。。
		builder.setIcon(R.drawable.ic_launcher);// 设置图标
		builder.setItems(item_list, new DialogInterface.OnClickListener() { // 弹出的列表对话框的条目下标从0开始
			@Override
			public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:// 打电话
							Intent dialIntent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"
											+ mContactsNumber.get(position)));
							startActivity(dialIntent);
							break;
						case 1:// 发短信
							Toast.makeText(getActivity(),"我动了" + item_list[which] + "！",Toast.LENGTH_SHORT).show();
							break;
						
						case 2:// 取消 收藏
							helper = new DBHelper(mContext, "contact.db");
							db = helper.getWritableDatabase();

							String contactsId = mContactsId.get(position);
							// 删除数据库里的东西
							db.delete("contact", "contactsid = ?",new String[] { contactsId });
							
							//同时要更新画面（显示已经收藏的联系人的画面）
							mContactsName.remove(position);
							mContactsNumber.remove(position);
							mContactsPhontoId.remove(position);
							mContactsId.remove(position);
							myAdapter.notifyDataSetChanged();//这句很重要吆。
							 
							 db.close();
							
							Toast.makeText(mContext, "成功取消收藏该联系人",Toast.LENGTH_SHORT).show();
							
							break;
						default:
							break;
						}

					}
				});
		AlertDialog dialog = builder.create();// 获取dialog
		dialog.show();// 显示对话框
	}

	/**
	 * 从数据库获得已经收藏的联系人的信息，存储在三个List里面
	 */
	private void getContactsData() {

		Cursor cursor = db
				.query("contact", null, null, null, null, null, "_id");
		if (cursor != null) {
			while (cursor.moveToNext()) {
				String id = cursor.getString(cursor.getColumnIndex("_id"));
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String num = cursor
						.getString(cursor.getColumnIndex("phonenum"));
				String contactsid = cursor.getString(cursor
						.getColumnIndex("contactsid"));
				String photoid = cursor.getString(cursor
						.getColumnIndex("photoid"));

				mContactsName.add(name);
				mContactsNumber.add(num);
				mContactsPhontoId.add(photoid);
				mContactsId.add(contactsid);
			}
			cursor.close();

		}
		db.close();
	}

}
