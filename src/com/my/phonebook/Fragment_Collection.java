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

	private ArrayList<String> mContactsName = new ArrayList<String>(); // ��ϵ������
	private ArrayList<String> mContactsNumber = new ArrayList<String>(); // ��ϵ�˵绰
	private ArrayList<String> mContactsPhontoId = new ArrayList<String>(); // ��ϵ��ͷ��ID
	private ArrayList<String> mContactsId = new ArrayList<String>(); // ��ϵ��ID

	private Context mContext = null;
	private ListView mListView = null;
	private MyListAdapter myAdapter = null;

	String[] item_list = { "��绰", "������", "ȡ���ղ�" };

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
		// ��������ݿ�����ϵ�˵���Ϣ
		getContactsData();

		myAdapter = new MyListAdapter(mContext, mContactsName, mContactsNumber,
				mContactsPhontoId, mContactsId);
		mListView.setAdapter(myAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					final int position, long id) {
				showDialog(position);//����¼����ǵ���һ��dialog��Ȼ�����һϵ�еĲ�����
			}
		});

		return view;

	}

	/**
	 * ��ʾ��һ��dialog����ʾ�����Զ��Ѿ��ղص���ϵ�˵�һϵ�в�������ʵ����Щ������
	 * @param position
	 */
	private void showDialog(final int position) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(mContactsName.get(position));// ���ñ���Ϊ�������ϵ�˵����֡���
		builder.setIcon(R.drawable.ic_launcher);// ����ͼ��
		builder.setItems(item_list, new DialogInterface.OnClickListener() { // �������б�Ի������Ŀ�±��0��ʼ
			@Override
			public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:// ��绰
							Intent dialIntent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"
											+ mContactsNumber.get(position)));
							startActivity(dialIntent);
							break;
						case 1:// ������
							Toast.makeText(getActivity(),"�Ҷ���" + item_list[which] + "��",Toast.LENGTH_SHORT).show();
							break;
						
						case 2:// ȡ�� �ղ�
							helper = new DBHelper(mContext, "contact.db");
							db = helper.getWritableDatabase();

							String contactsId = mContactsId.get(position);
							// ɾ�����ݿ���Ķ���
							db.delete("contact", "contactsid = ?",new String[] { contactsId });
							
							//ͬʱҪ���»��棨��ʾ�Ѿ��ղص���ϵ�˵Ļ��棩
							mContactsName.remove(position);
							mContactsNumber.remove(position);
							mContactsPhontoId.remove(position);
							mContactsId.remove(position);
							myAdapter.notifyDataSetChanged();//������Ҫߺ��
							 
							 db.close();
							
							Toast.makeText(mContext, "�ɹ�ȡ���ղظ���ϵ��",Toast.LENGTH_SHORT).show();
							
							break;
						default:
							break;
						}

					}
				});
		AlertDialog dialog = builder.create();// ��ȡdialog
		dialog.show();// ��ʾ�Ի���
	}

	/**
	 * �����ݿ����Ѿ��ղص���ϵ�˵���Ϣ���洢������List����
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
