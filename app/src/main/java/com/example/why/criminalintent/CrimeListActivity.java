package com.example.why.criminalintent;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.widget.Button;

import com.example.why.criminalintent.login.MyDialog;

public class CrimeListActivity extends SingleFragmentActivity
        implements CrimeListFragment.Callbacks, CrimeFragment.Callbacks {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onCrimeSelected(Crime crime) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = CrimePagerActivity.newIntent(this, crime.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = CrimeFragment.newInstance(crime.getId());

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }

    @Override
    public void onCrimeUpdated(Crime crime) {
        CrimeListFragment listFragment = (CrimeListFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.fragment_container);
        listFragment.updateUI();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            final MyDialog dialog = new MyDialog(CrimeListActivity.this);
            dialog.setTitle("提示");
            dialog.setMessage("您确定要退出吗？");
            dialog.setNegtive("取消");
            dialog.setPositive("确定");
            dialog.setOnClickBottomListener(new MyDialog.
                    OnClickBottomListener() {
                @Override
                public void onPositiveClick() { //确定按钮的点击事件
                    dialog.dismiss();
                    CrimeListActivity.this.finish();
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
