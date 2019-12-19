package com.example.myapplication.service;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.myapplication.Count.CostBean;
import com.example.myapplication.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {

    List<CostBean> costBeanList = new ArrayList<>();
    String costTotal;
    String inputTotal;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e("m_tag","MyService == onBind");
        return new MyBinder();
    }
    public class MyBinder extends Binder{
        public void Count(){
            MyDatabaseHelper dbHelper = new MyDatabaseHelper(getApplicationContext(), "user_info3.db", null, 1);
            SQLiteDatabase DB = dbHelper.getReadableDatabase();

            String sql = "select sum(cost) From user_info3 Where Alltype = '收入' And cost!=0";
            Cursor cursor = DB.rawQuery(sql, null);
            if(cursor.moveToFirst()) {
               inputTotal = String.valueOf(cursor.getDouble(0));
            }
            String sql2 = "select sum(cost) From user_info3 Where Alltype = '支出' And cost!=0";
            cursor = DB.rawQuery(sql2, null);
            if(cursor.moveToFirst()) {
              costTotal = String.valueOf(cursor.getDouble(0));
            }
            String sql3 = "select * From user_info3  WHERE cost!=0";
            cursor = DB.rawQuery(sql3, null);
            //需要修改
            for(int i=0;i<cursor.getColumnCount();i++){
                if (cursor.moveToPosition(i)) {
                    String date1 = cursor.getString(2);
                    double cost1 = cursor.getDouble(3);
                    String type1 = cursor.getString(5);
                    String Alltype1 = cursor.getString(4);
                    CostBean costBean = new CostBean(type1, Alltype1, cost1, date1);
                    costBeanList.add(costBean);
                }
            }
            Log.e("m_tag","MyService counting...");
        }
        public List<CostBean> getList(){
            return costBeanList;

        }
        public void setList(List<CostBean> costBeanList2){
            costBeanList = costBeanList2;
        }
        public String getCostTotal(){
            return costTotal;
        }
        public String getInputTotal(){
            return inputTotal;
        }
    }

    public void onCreate(){
        super.onCreate();

        Log.e("m_tag","MyService==OnCreate");
    }
    public int onStartCommand(Intent intent,int flags,int startId){
        Log.d("m_tag", "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }
    public void onDestroy(){
        super.onDestroy();
        Log.e("m_tag","onDestroy");
    }
    public boolean onUnbind(Intent intent){
        return super.onUnbind(intent);
    }
}
