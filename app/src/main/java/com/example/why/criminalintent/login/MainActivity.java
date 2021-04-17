package com.example.why.criminalintent.login;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.why.criminalintent.CrimeListActivity;
import com.example.why.criminalintent.R;
import com.example.why.criminalintent.utils.SendSmsTimerUtils;

import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText et_account;   //账号输入框
    private EditText et_password; //密码输入框
    private Button btn_login;      //登录按钮
    private String account;
    private String password;
    private Button yzbt;
    private TextView yz;
    private SendSmsTimerUtils mCountDownTimerUtils;

    SaveUser saveUser = new SaveUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //通过工具类SaveUser中的getUserInfo()方法获取账号和密码信息
        Map<String, String> userInfo = saveUser.getUserInfo(this);
        //自己的测试点
        if (userInfo != null) {
            Log.d("111111111111111111", userInfo.toString());
            et_account.setText(userInfo.get("account"));   //将获取的账号显示到界面上
            et_password.setText(userInfo.get("password")); //将获取的密码显示到界面上
        }
    }
    private void initView() {
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        //设置按钮的点击监听事件
        btn_login.setOnClickListener(this);
        yzbt = (Button)findViewById(R.id.yzbt);
        yzbt.setOnClickListener(this);
        yz = (TextView)findViewById(R.id.yz);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                //当点击登录按钮时，获取界面上输入的账号和密码
                account = et_account.getText().toString().trim();
                password = et_password.getText().toString().trim();
                //检验输入的账号和密码是否为空
                if (TextUtils.isEmpty(account)) {
                    Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, String> userInfo = saveUser.getUserInfo(this);
                //如果账户不存在或者发生更改 则保存或者更新
                if (!"why520".equals(yz.getText().toString().trim())){
                    Toast.makeText(MainActivity.this,"验证码错误！",Toast.LENGTH_LONG).show();
                } else if(!account.equals(userInfo.get("account"))||!password.equals(userInfo.get("password"))) {
                    init(account, password);
                }else {
                    //跳转界面
                    Intent intent = new Intent(this,CrimeListActivity.class);
                    this.startActivity(intent);
                    Toast.makeText(MainActivity.this,"登录成功！",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.yzbt:
                /**
                 * 第一步：初始化工具类关联需要实现倒计时功能的TextView
                 *
                 * 第一个参数：TextView控件(需要实现倒计时的TextView)
                 * 第二个参数：倒计时总时间，以毫秒为单位；
                 * 第三个参数：渐变事件，最低1秒，也就是说设置0-1000都是以一秒渐变，设置1000以上改变渐变时间
                 * 第四个个参数：点击textview之前的背景
                 * 第五个参数：点击textview之后的背景
                 */
                mCountDownTimerUtils = new SendSmsTimerUtils(yz, 5000, 1000,R.color.color2,R.color.color1);
                mCountDownTimerUtils.start();
                yzbt.setText("重新获取验证码");
        }
    }

    public void init( final String account, final String password) {
                final MyDialog dialog = new MyDialog(MainActivity.this);
                dialog.setTitle("提示");
                dialog.setMessage("账号信息发生了改变是否更新？");
                dialog.setNegtive("取消");
                dialog.setPositive("确定");
                dialog.setOnClickBottomListener(new MyDialog.
                        OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() { //确定按钮的点击事件
                        dialog.dismiss();
                        boolean isSaveSuccess = saveUser.saveUserInfo(MainActivity.this,account,password);
                        if (isSaveSuccess) {
                            et_account.setText(account);   //将更改的账号更新后显示到界面上
                            et_password.setText(password); //将获取的密码显示到界面上
                            Toast.makeText(MainActivity.this,"保存成功，快重新登录吧！",Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this,"保存失败",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onNegtiveClick() { //取消按钮的点击事件
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            final MyDialog dialog = new MyDialog(MainActivity.this);
            dialog.setTitle("提示");
            dialog.setMessage("您忍心退出吗？");
            dialog.setNegtive("取消");
            dialog.setPositive("确定");
            dialog.setOnClickBottomListener(new MyDialog.
                    OnClickBottomListener() {
                @Override
                public void onPositiveClick() { //确定按钮的点击事件
                    dialog.dismiss();
                    MainActivity.this.finish();
                }
                @Override
                public void onNegtiveClick() { //取消按钮的点击事件
                    dialog.dismiss();
                }
            });
            dialog.show();

        }

        return super.onKeyDown(keyCode, event);
    }
}
