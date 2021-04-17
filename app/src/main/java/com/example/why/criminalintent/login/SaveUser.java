package com.example.why.criminalintent.login;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SaveUser{
    // 保存账号和登录密码到data.xml文件中
    public boolean saveUserInfo(Context context, String account,
                                       String password) {
        SharedPreferences sp = context.getSharedPreferences("data",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("userName", account);
        edit.putString("pwd", password);
        edit.commit();
        return true;
    }
    //从data.xml文件中获取存储的账号和密码
    public Map<String, String> getUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("data",
                Context.MODE_PRIVATE);
        String account = sp.getString("userName", null);
        String password = sp.getString("pwd", null);
        Map<String, String> userMap = new HashMap<String, String>();
        userMap.put("account", account);
        userMap.put("password", password);
        return userMap;
    }
}
