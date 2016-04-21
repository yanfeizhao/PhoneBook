package com.my.phonebook.adapter;

import java.io.InputStream;
import java.util.ArrayList;

import com.my.phonebook.R;

import android.R.integer;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListAdapter extends BaseAdapter{
		private Context mContext = null;  
	    private ArrayList<String> mContactsName ;  
	    private ArrayList<String> mContactsNumber ;  
	    private ArrayList<String> mContactsPhontoID ;  
	    private ArrayList<String> mContactsID ;  
	    
	  
	   
	      
    public MyListAdapter(Context context,ArrayList<String> ContactsName ,ArrayList<String> ContactsNumber,ArrayList<String> ContactsPhontoid,ArrayList<String> Contactsid) {  
        mContext = context;  
        mContactsName=ContactsName;
        mContactsNumber=ContactsNumber;
        mContactsPhontoID =ContactsPhontoid;
        mContactsID=Contactsid;
    }  
  
    public int getCount() {  
        //���û�������  
        return mContactsName.size();  
    }  
  
    @Override  
    public boolean areAllItemsEnabled() {  
        return false;  
    }  
  
    public Object getItem(int position) {  
        return position;  
    }  
  
    public long getItemId(int position) {  
        return position;  
    }  
  
    public View getView(int position, View convertView, ViewGroup parent) {  
        ImageView iamge = null;  
        TextView title = null;  
        TextView text = null;  
        if (convertView == null || position < mContactsNumber.size()) {  
        convertView = LayoutInflater.from(mContext).inflate(  
            R.layout.contactslist_item, null);  
        iamge = (ImageView) convertView.findViewById(R.id.color_image);  
        title = (TextView) convertView.findViewById(R.id.color_title);  
        text = (TextView) convertView.findViewById(R.id.color_text);  
        }  
        //������ϵ������  
        title.setText(mContactsName.get(position));  
        //������ϵ�˺���  
        text.setText(mContactsNumber.get(position));  
        
        
        //������ϵ��ͷ��  
        
        ContentResolver resolver = mContext.getContentResolver(); 
        Bitmap contactPhoto = null;  
       Long  photoid=Long.parseLong(mContactsPhontoID.get(position));
       Long contactid=Long.parseLong(mContactsID.get(position));
        //photoid ����0 ��ʾ��ϵ����ͷ�� ���û�и���������ͷ�������һ��Ĭ�ϵ�  
        if(photoid > 0 ) {  
            Uri uri =ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI,contactid);  
            InputStream input = ContactsContract.Contacts.openContactPhotoInputStream(resolver, uri);  
            contactPhoto = BitmapFactory.decodeStream(input);  
        }else {  
            contactPhoto = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);  
        }  
        iamge.setImageBitmap(contactPhoto);  
        
        return convertView;  
    }  
  
    
}
