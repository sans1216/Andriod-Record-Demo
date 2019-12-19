package com.example.myapplication.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myapplication.MyDatabaseHelper;
import com.example.myapplication.R;
import com.example.myapplication.getRecord;

public class fragment2 extends Fragment {
    private View view;
    Button button;
    TextView address;
    TextView date;
    TextView bw ;
    TextView cost;
    getRecord sh;
    TextView costType;
    TextView costAmount;

    String type ="空";
    CheckBox cb1,cb2,cb3,cb4,cb5,cb6,cb7,cb8;
    private MyDatabaseHelper dbHelper;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_fragment2, container, false);
        button = view.findViewById(R.id.button2);
        address =  view.findViewById(R.id.address);
        date =  view.findViewById(R.id.date);
        bw =  view.findViewById(R.id.bw);
        cost = view.findViewById(R.id.edtCost2);

        cb1 = view.findViewById(R.id.checkClothes);
        cb2 = view.findViewById(R.id.checkMedical);
        cb3 = view.findViewById(R.id.checkTZ);
        cb4 = view.findViewById(R.id.checkSetting);
        cb5 = view.findViewById(R.id.checkFood);
        cb6 = view.findViewById(R.id.checkTrans);
        cb7 = view.findViewById(R.id.checkHome);
        cb8 = view.findViewById(R.id.checkGame);
        //设置数据库
        dbHelper = new MyDatabaseHelper(view.getContext(),"user_info3.db",null,1);

        return  view;
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) activity;
            costType = mainActivity.findViewById(R.id.costType);
            costAmount = mainActivity.findViewById(R.id.costAmount);
        }
    }
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        cb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "衣服饰品";
                Toast.makeText(view.getContext(),"类型为衣服饰品",Toast.LENGTH_SHORT).show();


            }
        });
        cb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "健康医疗";
                Toast.makeText(view.getContext(),"类型为健康医疗",Toast.LENGTH_SHORT).show();

            }
        });
        cb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "投资支出";
                Toast.makeText(view.getContext(),"类型为投资支出",Toast.LENGTH_SHORT).show();

            }
        });
        cb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "设置";
                Toast.makeText(view.getContext(),"类型为设置",Toast.LENGTH_SHORT).show();
            }
        });
        cb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "餐饮饮品";
                Toast.makeText(view.getContext(),"类型为餐饮饮品",Toast.LENGTH_SHORT).show();
            }
        });
        cb6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "行车交通";
                Toast.makeText(view.getContext(),"类型为行车交通",Toast.LENGTH_SHORT).show();
            }
        });
        cb7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "居家生活";
                Toast.makeText(view.getContext(),"类型为居家生活",Toast.LENGTH_SHORT).show();
            }
        });
        cb8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = "休闲娱乐";
                Toast.makeText(view.getContext(),"类型为休闲娱乐",Toast.LENGTH_SHORT).show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {

                String address1 = address.getText().toString();
                String date1 = date.getText().toString();
                String bw1 = bw.getText().toString();
                String cost1 = cost.getText().toString();
                // sh = new getRecord(getActivity());
                //sh.save(date1, bw1, address1);
                SQLiteDatabase DB = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                DB.execSQL( "CREATE TABLE IF NOT EXISTS user_info3"+"("
                        + "bw VARCHAR, "
                        + "address VARCHAR, "
                        + "date VARCHAR,"
                        + "cost DOUBLE,"
                        + "AllType VARCHAR,"
                        + "type VARCHAR"
                        +")");
                values.put("bw", bw1);
                values.put("address", address1);
                values.put("date", date1);
                values.put("cost",cost1);
                values.put("AllType","支出");
                values.put("type",type);
                DB.insert("user_info3", null, values);
                Toast.makeText(view.getContext(),"已保存数据",Toast.LENGTH_SHORT).show();
                costType.setText("类型：支出");
                costAmount.setText(cost.getText());

            }
        });
    }

}
