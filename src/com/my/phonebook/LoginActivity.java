package com.my.phonebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.my.phonebook.utils.SharedPrefsUtil;

public class LoginActivity extends Activity implements OnClickListener {

	private  static boolean flag=false;
	private EditText et_username;
	private EditText et_password;
	private CheckBox cb_remPwd;
	private Button btn_login;
	private Button btn_regist;
	private static String usernameSP;
	private static String passwordSP;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		et_username = (EditText) findViewById(R.id.et_name);
		et_password = (EditText) findViewById(R.id.et_pwd);
		cb_remPwd = (CheckBox) findViewById(R.id.cb_rem_pwd);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_regist=(Button) findViewById(R.id.btn_regist);
		
		cb_remPwd.setOnClickListener(this);
		btn_login.setOnClickListener(this);
		btn_regist.setOnClickListener(this);
		

		usernameSP = SharedPrefsUtil.getValue(this, "name", null);
		passwordSP = SharedPrefsUtil.getValue(this, "pwd", null);
		if (flag==true) {
			cb_remPwd.setChecked(true);
			et_username.setText(usernameSP);
			et_password.setText(passwordSP);
		}
	

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cb_rem_pwd:
			remeberPWD();
			break;
		case R.id.btn_login:
			login();
			break;
		case R.id.btn_regist:
			regist();
			break;
		default:
			break;
		}
	}



	private void regist() {
		String name_regist=et_username.getText().toString();
		String pwd_regist=et_password.getText().toString();
		if(name_regist.equals("")||pwd_regist.equals("")){
			toastMsg("请完整填写用户名和密码。");
		}
		else{
			SharedPrefsUtil.putValue(this, "name", name_regist);
			SharedPrefsUtil.putValue(this, "pwd", pwd_regist);
			toastMsg("注册成功！");
		}
	}

	private void login() {
		usernameSP = SharedPrefsUtil.getValue(this, "name", null);
		passwordSP = SharedPrefsUtil.getValue(this, "pwd", null);
			String name1 = et_username.getText().toString();
			String pwd1 = et_password.getText().toString();
			if(!name1.equals(usernameSP)){
				toastMsg("没有此用户名");
			}else if(!pwd1.equals(passwordSP)){
				toastMsg("密码错误！");
			}else{
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
			}
	}

	private void remeberPWD() {
		usernameSP = SharedPrefsUtil.getValue(this, "name", null);
		passwordSP = SharedPrefsUtil.getValue(this, "pwd", null);
		if (cb_remPwd.isChecked()) {
			flag=true;
			cb_remPwd.setChecked(true);
			if(et_username.getText().toString()==null){
				toastMsg("请先输入用户名！");
			}
			else {
				if(et_username.getText().toString().equals(usernameSP)){
					et_password.setText(passwordSP);
				}
				else {
					toastMsg("请输入正确的用户名");
				}
			}
			
		}
		else {
			flag=false;
			cb_remPwd.setChecked(false);
		}
	}
	
	public void toastMsg(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

}
