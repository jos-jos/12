package com.example.why.criminalintent.guide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.example.why.criminalintent.R;
import com.example.why.criminalintent.login.MainActivity;

import java.util.ArrayList;
import java.util.List;

import javax.swing.text.html.ImageView;

/*
 *Crated by 王浩宇 on 2020/06/05
 */
public class GuideActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ViewPager vp;
    private List<ImageView> imageViews;
    private int[] imgs= {R.drawable.why1, R.drawable.why2, R.drawable.why3,R.drawable.splash1};
    private Button btn;
    private ImageView[] dotViews;
    private VpAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        vp= findViewById(R.id.guide_vp);
        btn= findViewById(R.id.guide_btn);
        btn.setOnClickListener(this);
        //初始化图片
        initImgs();
        //初始化底部圆点指示器
        initDots();
        adapter= new VpAdapter(imageViews);
        vp.setAdapter(adapter);
        vp.addOnPageChangeListener(this);
    }

    /**
     * 改变首次打开的状态
     */
    private void setisFirst() {
        SharedPreferences.Editor editor= getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putString("isFirst", "1");
        editor.commit();
    }

    /**
     * 初始化底部圆点指示器
     */
    private void initDots() {
        LinearLayout layout= findViewById(R.id.guide_ll);
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(20, 20);
        params.setMargins(10, 0, 10, 0);
        dotViews= new ImageView[imgs.length];
        for (int i= 0; i< imageViews.size(); i++){
            ImageView imageView= new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setImageResource(R.drawable.dotselector);
            if (i== 0){
                imageView.setSelected(true);
            }else{
                imageView.setSelected(false);
            }
            dotViews[i]= imageView;
            final int finalI = i;
            dotViews[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vp.setCurrentItem(finalI);
                }
            });
            layout.addView(imageView);
        }
    }

    /**
     * 初始化图片
     */
    private void initImgs() {
        ViewPager.LayoutParams params= new ViewPager.LayoutParams();
        imageViews= new ArrayList<ImageView>();
        for (int i= 0; i< imgs.length; i++){
            ImageView imageView= new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setImageResource(imgs[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViews.add(imageView);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.guide_btn:
                setisFirst();
                Intent intent= new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int arg0) {
        for (int i= 0; i< dotViews.length; i++){
            if (arg0== i){
                dotViews[i].setSelected(true);
            }else {
                dotViews[i].setSelected(false);
            }

            if (arg0== dotViews.length- 1){
                btn.setVisibility(View.VISIBLE);
            }else {
                btn.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}