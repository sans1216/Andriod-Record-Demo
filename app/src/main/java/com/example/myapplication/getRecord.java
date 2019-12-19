package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class getRecord {
    private Context mContext;
    public getRecord(){ }
    public getRecord(Context context) {
        this.mContext = context;
    }

    public void save(String date,String bw,String address){
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("date",date);
        editor.putString("Bw",bw);
        editor.putString("address",address);
        editor.commit();
        Toast.makeText(mContext, "信息已写入SharedPreference中", Toast.LENGTH_SHORT).show();
    }
    public Map<String,String> read(){
        Map<String,String> data = new HashMap<String, String>();
SharedPreferences sharedPreferences = mContext.getSharedPreferences("data",Context.MODE_PRIVATE);
data.put("date",sharedPreferences.getString("date","2019.11.16"));
data.put("Bw",sharedPreferences.getString("Bw","哈哈"));
data.put("address",sharedPreferences.getString("address","ZUCC"));
return data;
}
}
