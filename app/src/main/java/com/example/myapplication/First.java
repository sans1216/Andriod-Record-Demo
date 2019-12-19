package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class First extends AppCompatActivity implements View.OnClickListener {
    private View view1,view2,view3;
    private List<View> viewList;
    private ViewPager mViewPager1;
    private Button btnGo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_main2);

        initView();
        btnGo.setOnClickListener(this);

    }

    private void initView() {
        mViewPager1 = (ViewPager) findViewById(R.id.view2);
        LayoutInflater lf = getLayoutInflater().from(this);
        view1 = lf.inflate(R.layout.page1,null);
        view2 = lf.inflate(R.layout.page2,null);
        view3 = lf.inflate(R.layout.page3,null);


        viewList = new ArrayList<View>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        btnGo = view3.findViewById(R.id.btnGo);
        mViewPager1.setAdapter(new MyViewPagerAdapter1(viewList));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGo:
                Intent intent = new Intent();
                intent.setClass(First.this, Login.class);
                startActivity(intent);
                break;
            default:
                break;

        }
    }
}
