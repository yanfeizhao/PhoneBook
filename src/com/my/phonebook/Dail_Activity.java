package com.my.phonebook;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Dail_Activity extends Activity implements OnClickListener {

	private ImageButton btn0;
	private ImageButton btn1;
	private ImageButton btn2;
	private ImageButton btn3;
	private ImageButton btn4;
	private ImageButton btn5;
	private ImageButton btn6;
	private ImageButton btn7;
	private ImageButton btn8;
	private ImageButton btn9;
	private ImageButton btn_star;
	private ImageButton btn_jing;
	private ImageButton btn_contacts;// ����᷵����ϵ�˽���
	private Button btn_dail;// ����绰
	private ImageButton btn_del;// ���˳���
	private TextView showPhoneNum;
	private ListView mListView_show_dail;// ������ȥnew

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dail);

		// new Dail_Activity().init();
		btn0 = (ImageButton) findViewById(R.id.num_0);
		btn1 = (ImageButton) findViewById(R.id.num_1);
		btn2 = (ImageButton) findViewById(R.id.num_2);
		btn3 = (ImageButton) findViewById(R.id.num_3);
		btn4 = (ImageButton) findViewById(R.id.num_4);
		btn5 = (ImageButton) findViewById(R.id.num_5);
		btn6 = (ImageButton) findViewById(R.id.num_6);
		btn7 = (ImageButton) findViewById(R.id.num_7);
		btn8 = (ImageButton) findViewById(R.id.num_8);
		btn9 = (ImageButton) findViewById(R.id.num_9);
		btn_jing = (ImageButton) findViewById(R.id.jing);
		btn_star = (ImageButton) findViewById(R.id.xing);
		btn_contacts = (ImageButton) findViewById(R.id.btn_lixiren);
		btn_dail = (Button) findViewById(R.id.btn_hujiao);
		btn_del = (ImageButton) findViewById(R.id.btn_del);

		showPhoneNum = (TextView) findViewById(R.id.tv_phonenum);

		btn0.setOnClickListener(this);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
		btn4.setOnClickListener(this);
		btn5.setOnClickListener(this);
		btn6.setOnClickListener(this);
		btn7.setOnClickListener(this);
		btn8.setOnClickListener(this);
		btn9.setOnClickListener(this);
		btn_star.setOnClickListener(this);
		btn_jing.setOnClickListener(this);
		btn_contacts.setOnClickListener(this);
		btn_dail.setOnClickListener(this);
		btn_del.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.num_0:
			showPhoneNum.setText(showPhoneNum.getText() + "0");
			break;
		case R.id.num_1:
			showPhoneNum.setText(showPhoneNum.getText() + "1");
			break;
		case R.id.num_2:
			showPhoneNum.setText(showPhoneNum.getText() + "2");
			break;
		case R.id.num_3:
			showPhoneNum.setText(showPhoneNum.getText() + "3");
			break;
		case R.id.num_4:
			showPhoneNum.setText(showPhoneNum.getText() + "4");
			break;
		case R.id.num_5:
			showPhoneNum.setText(showPhoneNum.getText() + "5");
			break;
		case R.id.num_6:
			showPhoneNum.setText(showPhoneNum.getText() + "6");
			break;
		case R.id.num_7:
			showPhoneNum.setText(showPhoneNum.getText() + "7");
			break;
		case R.id.num_8:
			showPhoneNum.setText(showPhoneNum.getText() + "8");
			break;
		case R.id.num_9:
			showPhoneNum.setText(showPhoneNum.getText() + "9");
			break;
		case R.id.xing:
			showPhoneNum.setText(showPhoneNum.getText() + "*");
			break;
		case R.id.jing:
			showPhoneNum.setText(showPhoneNum.getText() + "#");
			break;
		case R.id.btn_lixiren:
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			break;
		case R.id.btn_hujiao:
			// ���������ϵͳ�Ĵ�绰���ܡ���ʵ�ִ�绰����Ӧ���Ȼ�ȡ������ĵ绰���룬Ȼ���绰������
			String phone_number = showPhoneNum.getText().toString();
			phone_number = phone_number.trim();// ɾ���ַ����ײ���β���Ŀո�
			if (phone_number != null && !phone_number.equals("")) {
				// ��װһ������绰��intent�����ҽ��绰�����װ��һ��Uri������
				Intent intent1 = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + phone_number));
				Dail_Activity.this.startActivity(intent1);// �ڲ���
			}
			break;
		case R.id.btn_del:
			String num = showPhoneNum.getText().toString();
			if (num.length() != 0) {
				showPhoneNum.setText(num.substring(0, num.length() - 1));
			}
			break;

		}

	}

	// /**
	// * ��ʼ��------ͨ��findViewById���ҵ�Ψһ�ؼ�----�����������ʼ������oncreate����Ͳ���ʹ��ԭ���Ǿֲ���ʼ������
	// */
	/*
	 * public void init(){ btn0 = (ImageButton) findViewById(R.id.num_0); btn1 =
	 * (ImageButton) findViewById(R.id.num_1); btn2 = (ImageButton)
	 * findViewById(R.id.num_2); btn3 = (ImageButton) findViewById(R.id.num_3);
	 * btn4 = (ImageButton) findViewById(R.id.num_4); btn5 = (ImageButton)
	 * findViewById(R.id.num_5); btn6 = (ImageButton) findViewById(R.id.num_6);
	 * btn7 = (ImageButton) findViewById(R.id.num_7); btn8 = (ImageButton)
	 * findViewById(R.id.num_8); btn9 = (ImageButton) findViewById(R.id.num_9);
	 * btn_jing = (ImageButton) findViewById(R.id.jing); btn_star =
	 * (ImageButton) findViewById(R.id.xing); btn_contacts = (ImageButton)
	 * findViewById(R.id.btn_lixiren); btn_dail = (Button)
	 * findViewById(R.id.btn_hujiao); btn_del = (ImageButton)
	 * findViewById(R.id.btn_del); showPhoneNum = (TextView)
	 * findViewById(R.id.tv_phonenum); }
	 */

}
