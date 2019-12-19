package com.example.myapplication.Count;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity1;
import com.example.myapplication.service.MyService;
import com.example.myapplication.MyDatabaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.ui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class CountActivity extends AppCompatActivity {
    private MyService.MyBinder mBinder;
    String inputTotal = "";
    String costTotal = "";
    TextView tv_costTotal;
    ListView lv_count;
    private List<CostBean> costBeanList = new ArrayList<>();
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MyService.MyBinder) service;
            Toast.makeText(CountActivity.this,"服务连接成功",Toast.LENGTH_SHORT).show();

            mBinder.Count();
            inputTotal = mBinder.getInputTotal();
            costTotal = mBinder.getCostTotal();
            MainActivity1 activity1 = new MainActivity1();
            costBeanList = activity1.getList();

            Log.e("e_tag",mBinder.getList().get(1).getAllType());
            //costBeanList = mBinder.getList();
            for(int i=0;i<costBeanList.size();i++) {
                Log.e("m_tag", costBeanList.get(i).toString());
            }
            tv_costTotal.setText(costTotal);
            lv_count = findViewById(R.id.lv_cost);
            lv_count.setAdapter(new ListViewAdapter(CountActivity.this,costBeanList));
            //Toast.makeText(CountActivity.this,inputTotal+" "+costTotal,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        unbindService(conn);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
        //启动服务
        Intent it = new Intent(CountActivity.this,MyService.class);
        bindService(it,conn, Context.BIND_AUTO_CREATE);



        ImageButton w = findViewById(R.id.w);
        w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CountActivity.this, MainActivity.class);
                startActivity(intent);
               unbindService(conn);
            }

        });

        ImageButton r = findViewById(R.id.r);
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CountActivity.this, CountActivity.class);
               startActivity(intent);
               unbindService(conn);
            }
        });

    }

    private void initView() {
        tv_costTotal = findViewById(R.id.tv_costCount);
        Button btn_clear = findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper dbHelper = new MyDatabaseHelper(CountActivity.this, "user_info3.db", null, 1);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                db.execSQL("DROP TABLE IF EXISTS user_info3");
                Toast.makeText(CountActivity.this,"已清空账单",Toast.LENGTH_SHORT).show();

            }
        });

    }



}
