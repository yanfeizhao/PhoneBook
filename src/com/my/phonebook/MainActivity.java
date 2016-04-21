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
		Toast.makeText(this, "�༭��ϵ��", Toast.LENGTH_SHORT).show();
	}


	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId){
//		�������¼�����ת���µ�Activity
		case R.id.rbtn_dail:
			Intent intent=new Intent(this,Dail_Activity.class);
			startActivity(intent);
			
			break;
//	��ʾ��ϵ�ˣ�ʹ��fragemnt��ʾ�ڱ�ҳ�м䲿��		
		case R.id.rbtn_contacts:
			tv_title.setText("��ϵ��");
			Fragment_contacts  fragment_contacts=new Fragment_contacts();
			FragmentManager fragmentManager=getFragmentManager();
			FragmentTransaction beginTransaction=fragmentManager.beginTransaction();
			beginTransaction.replace(R.id.frame,fragment_contacts);
			beginTransaction.commit();
			break;
			
//  ��ʾ�ղص���ϵ�ˡ���
		case R.id.rbtn_collection:
			tv_title.setText("�ղ�");
//   ͬ���ģ����м�λ��ʹ��fragment��ʾ�Ѿ��ղص���ϵ�ˡ�
//			��ѯ�������ݿ��л�����ݲ�����ʾ����
			Fragment_Collection  fragment_Collection=new Fragment_Collection();
			FragmentManager fragmentManager2=getFragmentManager();
			FragmentTransaction beginTransaction2=fragmentManager2.beginTransaction();
			beginTransaction2.replace(R.id.frame,fragment_Collection);
			beginTransaction2.commit();
			break;
			
			
		}
		
	}

	

}
