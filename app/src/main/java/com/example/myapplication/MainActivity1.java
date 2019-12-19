package com.example.myapplication;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Count.CostBean;
import com.example.myapplication.Count.CountActivity;
import com.example.myapplication.Count.ListViewAdapter;
import com.example.myapplication.service.MyService;
import com.example.myapplication.ui.MainActivity;
import com.example.myapplication.view.BackupTask;
import com.example.myapplication.view.SlideMenu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity1 extends AppCompatActivity {
    private ImageView btn_back;
    private SlideMenu slideMenu;

    private MyService.MyBinder mBinder;
    String inputTotal = "";
    String costTotal = "";
    TextView tv_costTotal;
    ListView lv_count;
    public List<CostBean> costBeanList2 = new ArrayList<>();
    public List<CostBean> costBeanList = new ArrayList<>();
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MyService.MyBinder) service;
            Toast.makeText(MainActivity1.this,"服务连接成功",Toast.LENGTH_SHORT).show();
            mBinder.Count();
            inputTotal = mBinder.getInputTotal();
            costTotal = mBinder.getCostTotal();
            costBeanList = mBinder.getList();
            tv_costTotal.setText(costTotal);
            lv_count = findViewById(R.id.lv_cost);
            lv_count.setAdapter(new ListViewAdapter(MainActivity1.this,costBeanList));
            //Toast.makeText(CountActivity.this,inputTotal+" "+costTotal,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Bmob.initialize(this, "2f3ba93bd3be68a95b6572d4b1cf456a");
        initView();
        //启动服务
        Intent it = new Intent(MainActivity1.this,MyService.class);
        bindService(it,conn, Context.BIND_AUTO_CREATE);

        ImageView login = findViewById(R.id.loginp);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity1.this, Login.class);
                startActivity(intent);
            }
        });


        ImageButton w = findViewById(R.id.w);
        w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity1.this, MainActivity.class);
                startActivity(intent);
                unbindService(conn);
            }
        });

        ImageButton r = findViewById(R.id.r);
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity1.this, MainActivity1.class);
                startActivity(intent);
                unbindService(conn);
            }
        });

        //数据库备份
        TextView backup = findViewById(R.id.backup);
        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i =0;i<costBeanList.size();i++){
                   CostBean costBean =   costBeanList.get(i);

                   costBean.save(MainActivity1.this,new SaveListener() {
                       @Override
                       public void onSuccess() {
                           Toast.makeText(MainActivity1.this,"已备份数据库",Toast.LENGTH_SHORT).show();
                       }
                       @Override
                       public void onFailure(int i, String s) {
                           Toast.makeText(MainActivity1.this,"备份失败",Toast.LENGTH_SHORT).show();
                       }
                   });
                }

            }
        });
        //数据库恢复
        TextView restroe = findViewById(R.id.restroe);
        restroe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobQuery<CostBean> query = new BmobQuery<>("CostBean");
                query.findObjects(v.getContext(), new FindListener<CostBean>() {
                    @Override
                    public void onSuccess(List<CostBean> list) {
                        MyDatabaseHelper dbHelper = new MyDatabaseHelper(getApplicationContext(), "user_info3.db", null, 1);
                        SQLiteDatabase db = dbHelper.getReadableDatabase();
                        db.execSQL("DROP TABLE IF EXISTS user_info3");
                        db.execSQL("CREATE TABLE IF NOT EXISTS user_info3"+"("
                                + "bw VARCHAR, "
                                + "address VARCHAR, "
                                + "date VARCHAR,"
                                + "cost DOUBLE,"
                                + "AllType VARCHAR,"
                                + "type VARCHAR"
                                +")");

                        for (int i = 0; i < list.size(); i++) {
                            ContentValues values = new ContentValues();
                            values.put("bw","1");
                            values.put("address","zucc");
                            values.put("date",list.get(i).getDate());
                            values.put("cost",list.get(i).getCostAmount());
                            values.put("AllType",list.get(i).getAllType());
                            values.put("type",list.get(i).getCostType());

                            db.insert("user_info3",null,values);

                    }
                        mBinder.setList(costBeanList);
                       // Log.e("e_tag",mBinder.getList().get(1).getAllType());
                        Toast.makeText(MainActivity1.this,"恢复成功",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onError(int i, String s) {
                        Toast.makeText(MainActivity1.this,"恢复失败",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void initView() {
        tv_costTotal = findViewById(R.id.tv_costCount);
        Button btn_clear = findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper dbHelper = new MyDatabaseHelper(MainActivity1.this, "user_info3.db", null, 1);
                SQLiteDatabase db = dbHelper.getReadableDatabase();
                db.execSQL("DROP TABLE IF EXISTS user_info3");
                db.execSQL(MyDatabaseHelper.CREATE_DB);
                ContentValues values = new ContentValues();
                values.put("bw",0);
                values.put("address",0);
                values.put("date",0);
                values.put("cost",0);
                values.put("AllType","收入");
                values.put("type",0);
                db.insert("user_info3",null,values);
                ContentValues values2 = new ContentValues();
                values2.put("bw",0);
                values2.put("address",0);
                values2.put("date",0);
                values2.put("cost",0);
                values2.put("AllType","支出");
                values2.put("type",0);
                db.insert("user_info3",null,values);

                Toast.makeText(MainActivity1.this,"已清空账单",Toast.LENGTH_SHORT).show();

            }
        });

    }
    public List<CostBean> getList(){
        return costBeanList2;
    }





}