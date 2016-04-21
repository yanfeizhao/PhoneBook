package com.my.phonebook;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnCheckedChangeListener{
	
	private  RadioGroup group;
	private TextView tv_title;
	private Button btn_edit;
	private Button btn_add;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		group=(RadioGroup)findViewById(R.id.radiogroup);
		group.setOnCheckedChangeListener(this);
		
		tv_title=(TextView) findViewById(R.id.tv_title);
		btn_edit=(Button) findViewById(R.id.btn_edit);
		btn_add=(Button) findViewById(R.id.btn_add);
		
		
		Fragment_contacts  fragment_contacts=new Fragment_contacts();
		FragmentManager fragmentManager=getFragmentManager();
		FragmentTransaction beginTransaction=fragmentManager.beginTransaction();
		beginTransaction.add(R.id.frame,fragment_contacts);
		beginTransaction.commit();
		
	}
	
	public void as(View view){
		Toast.makeText(this, "编辑联系人", Toast.LENGTH_SHORT).show();
	}


	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId){
//		处理拨号事件，跳转到新的Activity
		case R.id.rbtn_dail:
			Intent intent=new Intent(this,Dail_Activity.class);
			startActivity(intent);
			
			break;
//	显示联系人，使用fragemnt显示在本页中间部分		
		case R.id.rbtn_contacts:
			tv_title.setText("联系人");
			Fragment_contacts  fragment_contacts=new Fragment_contacts();
			FragmentManager fragmentManager=getFragmentManager();
			FragmentTransaction beginTransaction=fragmentManager.beginTransaction();
			beginTransaction.replace(R.id.frame,fragment_contacts);
			beginTransaction.commit();
			break;
			
//  显示收藏的联系人。。
		case R.id.rbtn_collection:
			tv_title.setText("收藏");
//   同样的，在中间位置使用fragment显示已经收藏的联系人。
//			查询，从数据库中获得数据并且显示。。
			Fragment_Collection  fragment_Collection=new Fragment_Collection();
			FragmentManager fragmentManager2=getFragmentManager();
			FragmentTransaction beginTransaction2=fragmentManager2.beginTransaction();
			beginTransaction2.replace(R.id.frame,fragment_Collection);
			beginTransaction2.commit();
			break;
			
			
		}
		
	}

	

}
