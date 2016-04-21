package com.my.phonebook;

import java.io.InputStream;
import java.util.ArrayList;

import com.my.phonebook.adapter.MyListAdapter;
import com.my.phonebook.db.DBHelper;

import android.R.integer;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Fragment_contacts extends  Fragment{
	

	 /**获取库Phon表字段**/  
    private static final String[] PHONES_PROJECTION = new String[] {  
        Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,Phone.CONTACT_ID };  
     
    /**联系人显示名称**/  
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;  
      
    /**电话号码**/  
    private static final int PHONES_NUMBER_INDEX = 1;  
      
    /**头像ID**/  
    private static final int PHONES_PHOTO_ID_INDEX = 2;  
     
    /**联系人的ID**/  
    private static final int PHONES_CONTACT_ID_INDEX = 3;  
      
  
    private ArrayList<String> mContactsName = new ArrayList<String>();  //联系人名称
    private ArrayList<String> mContactsNumber = new ArrayList<String>();  //联系人电话
    private ArrayList<String> mContactsPhontoID = new ArrayList<String>();  //联系人头像id
    private ArrayList<String> mContactsID = new ArrayList<String>();  //联系人id
	
    
    String [] item_list = {"打电话","发短信","收藏","编辑","删除"};
    
    private DBHelper helper;
	private SQLiteDatabase db;
    
    Context mContext = null;  
    
    ListView mListView = null;  
    MyListAdapter myAdapter = null;  
    
    Long contactid;
  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
   	
    super.onCreate(savedInstanceState);  
    }  
  
    /**得到手机通讯录联系人信息**/  
    private void getPhoneContacts() {  
    ContentResolver resolver = mContext.getContentResolver();  
  
    // 获取手机联系人  
    Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);  
  
  
    if (phoneCursor != null) {  
        while (phoneCursor.moveToNext()) {  
  
        //得到手机号码  
        String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);  
        //当手机号码为空的或者为空字段 跳过当前循环  
        if (TextUtils.isEmpty(phoneNumber))  
            continue;  
          
        //得到联系人名称  
        String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);  
          
        //得到联系人ID  
         contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);  
  
        //得到联系人头像ID  
        Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);  
          
        mContactsName.add(contactName);  
        mContactsNumber.add(phoneNumber);  
        mContactsPhontoID.add(photoid.toString());  
        mContactsID.add(contactid.toString());
        }  
  
        phoneCursor.close();  
    }  
    }  
      
    /**得到手机SIM卡联系人人信息**/  
    private void getSIMContacts() {  
    ContentResolver resolver = mContext.getContentResolver();  
    // 获取Sims卡联系人  
    Uri uri = Uri.parse("content://icc/adn");  
    Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null,  
        null);  
  
    if (phoneCursor != null) {  
        while (phoneCursor.moveToNext()) {  
  
        // 得到手机号码  
        String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);  
        // 当手机号码为空的或者为空字段 跳过当前循环  
        if (TextUtils.isEmpty(phoneNumber))  
            continue;  
        // 得到联系人名称  
        String contactName = phoneCursor  
            .getString(PHONES_DISPLAY_NAME_INDEX);  
  
        //Sim卡中没有联系人头像  
          
        mContactsName.add(contactName);  
        mContactsNumber.add(phoneNumber);  
        }  
  
        phoneCursor.close();  
    }  
    }  
      
    

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view=inflater.inflate(R.layout.fragment, container,false);
		
		mListView=(ListView)view.findViewById(R.id.lv_lianxiren);
		 mContext = Fragment_contacts.this.getActivity(); 
		 
		    /**得到手机通讯录联系人信息**/  
		    getPhoneContacts();  
		  
		    myAdapter = new MyListAdapter(mContext,mContactsName,mContactsNumber,mContactsPhontoID,mContactsID);  
		    mListView.setAdapter(myAdapter); 
		  
		    mListView.setOnItemClickListener(new OnItemClickListener() {  
		  
		        @Override  
		        public void onItemClick(AdapterView<?> adapterView, View view,  
		            final int position, long id) {  

		    		showDialog1(position);
		        }

		 
		    });  
		return view;
	}
	
	
	/**
	 * 显示列表对话框
	 */
	private void showDialog1( final int position) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(mContactsName.get(position));//设置标题为点击的联系人的名字。。
		builder.setIcon(R.drawable.ic_launcher);//设置图标
		builder.setItems(item_list, new DialogInterface.OnClickListener() {	//弹出的列表对话框的条目下标从0开始	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch(which){
				//打电话
				case 0:
					 Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri  
					            .parse("tel:" + mContactsNumber.get(position)));  
					        startActivity(dialIntent);  
					break;
				//发短信	
				case 1:
					Toast.makeText(getActivity(), "我动了"+item_list[which]+"！",
							Toast.LENGTH_SHORT).show();
					break;
				//收藏
				case 2:
					/*
					 * 首先查询，表里面是否已经有这个数据，如果有，表示已经收藏，不能重复收藏，如果没有，就收藏
					 * 获得联系人的姓名，电话，头像，存储起来。插入数据库里的表里面
					 */
					helper = new DBHelper(mContext, "contact.db");
					db = helper.getWritableDatabase(); 
					
					if(haveCollected(position)){
						//提示，不能重复收藏
						Toast.makeText(getActivity(), "已经收藏过该联系人，不能重复收藏！！",
								Toast.LENGTH_SHORT).show();
					}
					else{
						//收藏
						collectContact(position);
						Toast.makeText(mContext, "成功收藏该联系人", Toast.LENGTH_SHORT).show();
					}
					
					
					
					break;
				//编辑
				case 3:
					Toast.makeText(getActivity(), "我动了"+item_list[which]+"！",
							Toast.LENGTH_SHORT).show();
					break;
				//删除
				case 4:
					Toast.makeText(getActivity(), "我动了"+item_list[which]+"！",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		});
		AlertDialog dialog = builder.create();//获取dialog
		dialog.show();//显示对话框
	} 
	
	/**
	 * 收藏联系人，并将其存入数据库的表中
	 * @param position
	 */
	private void collectContact( int position) {
		String contactsName=mContactsName.get(position);
		String contactsNum=mContactsNumber.get(position);
		String contactsPhotoId=mContactsPhontoID.get(position);
		String contactsId=mContactsID.get(position);
		
		ContentValues values = new ContentValues();
		
		values.put("name", contactsName);
		values.put("phonenum", contactsNum);
		values.put("photoid", contactsPhotoId);
		values.put("contactsid", contactsId);
		
		
		db.insert("contact", null, values);
		values.clear();
//			db.close();
	}
	
	public boolean haveCollected( int position) {
		boolean flag=false;
		String contactsId = mContactsID.get(position);
		Cursor cursor = db.query("contact", null, "contactsid = "+contactsId,
				null, null, null, null);
		if (cursor != null) {
			//本来是把flag= true;写在这里，
			/*理解为，已经收藏，数据库里有，cursor不为null，----没收藏，查不到，cursor是null
			 * 但是，测试的时候，我明明数据库里没有这条数据，但是cursor却不为null。。依旧将flag改为true。。
			 * 导致任何联系人都不能收藏。。无奈，只好在下面作一些判断，就算cursor不是null，也要判断得到的是不是该联系人的id
			 * 是的话，再将flag=true。
			 */
			while (cursor.moveToNext()) {
				String contactsid = cursor.getString(cursor
						.getColumnIndex("contactsid"));
				if(contactsId.equals(contactsid)){//相等就表示已经添加过该联系人。
					flag= true;
				}
			}
		}
		else {
			//没有收藏过
			flag= false;
		}
		cursor.close();
//		db.close();
		

		return flag;
	}
	
}
