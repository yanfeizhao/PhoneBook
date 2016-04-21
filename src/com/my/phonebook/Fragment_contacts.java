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
	

	 /**��ȡ��Phon���ֶ�**/  
    private static final String[] PHONES_PROJECTION = new String[] {  
        Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID,Phone.CONTACT_ID };  
     
    /**��ϵ����ʾ����**/  
    private static final int PHONES_DISPLAY_NAME_INDEX = 0;  
      
    /**�绰����**/  
    private static final int PHONES_NUMBER_INDEX = 1;  
      
    /**ͷ��ID**/  
    private static final int PHONES_PHOTO_ID_INDEX = 2;  
     
    /**��ϵ�˵�ID**/  
    private static final int PHONES_CONTACT_ID_INDEX = 3;  
      
  
    private ArrayList<String> mContactsName = new ArrayList<String>();  //��ϵ������
    private ArrayList<String> mContactsNumber = new ArrayList<String>();  //��ϵ�˵绰
    private ArrayList<String> mContactsPhontoID = new ArrayList<String>();  //��ϵ��ͷ��id
    private ArrayList<String> mContactsID = new ArrayList<String>();  //��ϵ��id
	
    
    String [] item_list = {"��绰","������","�ղ�","�༭","ɾ��"};
    
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
  
    /**�õ��ֻ�ͨѶ¼��ϵ����Ϣ**/  
    private void getPhoneContacts() {  
    ContentResolver resolver = mContext.getContentResolver();  
  
    // ��ȡ�ֻ���ϵ��  
    Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);  
  
  
    if (phoneCursor != null) {  
        while (phoneCursor.moveToNext()) {  
  
        //�õ��ֻ�����  
        String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);  
        //���ֻ�����Ϊ�յĻ���Ϊ���ֶ� ������ǰѭ��  
        if (TextUtils.isEmpty(phoneNumber))  
            continue;  
          
        //�õ���ϵ������  
        String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);  
          
        //�õ���ϵ��ID  
         contactid = phoneCursor.getLong(PHONES_CONTACT_ID_INDEX);  
  
        //�õ���ϵ��ͷ��ID  
        Long photoid = phoneCursor.getLong(PHONES_PHOTO_ID_INDEX);  
          
        mContactsName.add(contactName);  
        mContactsNumber.add(phoneNumber);  
        mContactsPhontoID.add(photoid.toString());  
        mContactsID.add(contactid.toString());
        }  
  
        phoneCursor.close();  
    }  
    }  
      
    /**�õ��ֻ�SIM����ϵ������Ϣ**/  
    private void getSIMContacts() {  
    ContentResolver resolver = mContext.getContentResolver();  
    // ��ȡSims����ϵ��  
    Uri uri = Uri.parse("content://icc/adn");  
    Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null,  
        null);  
  
    if (phoneCursor != null) {  
        while (phoneCursor.moveToNext()) {  
  
        // �õ��ֻ�����  
        String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);  
        // ���ֻ�����Ϊ�յĻ���Ϊ���ֶ� ������ǰѭ��  
        if (TextUtils.isEmpty(phoneNumber))  
            continue;  
        // �õ���ϵ������  
        String contactName = phoneCursor  
            .getString(PHONES_DISPLAY_NAME_INDEX);  
  
        //Sim����û����ϵ��ͷ��  
          
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
		 
		    /**�õ��ֻ�ͨѶ¼��ϵ����Ϣ**/  
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
	 * ��ʾ�б�Ի���
	 */
	private void showDialog1( final int position) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(mContactsName.get(position));//���ñ���Ϊ�������ϵ�˵����֡���
		builder.setIcon(R.drawable.ic_launcher);//����ͼ��
		builder.setItems(item_list, new DialogInterface.OnClickListener() {	//�������б�Ի������Ŀ�±��0��ʼ	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch(which){
				//��绰
				case 0:
					 Intent dialIntent = new Intent(Intent.ACTION_CALL, Uri  
					            .parse("tel:" + mContactsNumber.get(position)));  
					        startActivity(dialIntent);  
					break;
				//������	
				case 1:
					Toast.makeText(getActivity(), "�Ҷ���"+item_list[which]+"��",
							Toast.LENGTH_SHORT).show();
					break;
				//�ղ�
				case 2:
					/*
					 * ���Ȳ�ѯ���������Ƿ��Ѿ���������ݣ�����У���ʾ�Ѿ��ղأ������ظ��ղأ����û�У����ղ�
					 * �����ϵ�˵��������绰��ͷ�񣬴洢�������������ݿ���ı�����
					 */
					helper = new DBHelper(mContext, "contact.db");
					db = helper.getWritableDatabase(); 
					
					if(haveCollected(position)){
						//��ʾ�������ظ��ղ�
						Toast.makeText(getActivity(), "�Ѿ��ղع�����ϵ�ˣ������ظ��ղأ���",
								Toast.LENGTH_SHORT).show();
					}
					else{
						//�ղ�
						collectContact(position);
						Toast.makeText(mContext, "�ɹ��ղظ���ϵ��", Toast.LENGTH_SHORT).show();
					}
					
					
					
					break;
				//�༭
				case 3:
					Toast.makeText(getActivity(), "�Ҷ���"+item_list[which]+"��",
							Toast.LENGTH_SHORT).show();
					break;
				//ɾ��
				case 4:
					Toast.makeText(getActivity(), "�Ҷ���"+item_list[which]+"��",
							Toast.LENGTH_SHORT).show();
					break;
				}
			}
		});
		AlertDialog dialog = builder.create();//��ȡdialog
		dialog.show();//��ʾ�Ի���
	} 
	
	/**
	 * �ղ���ϵ�ˣ�������������ݿ�ı���
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
			//�����ǰ�flag= true;д�����
			/*���Ϊ���Ѿ��ղأ����ݿ����У�cursor��Ϊnull��----û�ղأ��鲻����cursor��null
			 * ���ǣ����Ե�ʱ�����������ݿ���û���������ݣ�����cursorȴ��Ϊnull�������ɽ�flag��Ϊtrue����
			 * �����κ���ϵ�˶������ղء������Σ�ֻ����������һЩ�жϣ�����cursor����null��ҲҪ�жϵõ����ǲ��Ǹ���ϵ�˵�id
			 * �ǵĻ����ٽ�flag=true��
			 */
			while (cursor.moveToNext()) {
				String contactsid = cursor.getString(cursor
						.getColumnIndex("contactsid"));
				if(contactsId.equals(contactsid)){//��Ⱦͱ�ʾ�Ѿ���ӹ�����ϵ�ˡ�
					flag= true;
				}
			}
		}
		else {
			//û���ղع�
			flag= false;
		}
		cursor.close();
//		db.close();
		

		return flag;
	}
	
}
