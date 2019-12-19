package com.example.myapplication.Count;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class ListViewAdapter extends BaseAdapter {


    private MyViewHolder holder;
    private LayoutInflater mLayoutInflater;
    private List<CostBean> costList;
    public ListViewAdapter(Context context, List<CostBean> costBeanList) {

        this.mLayoutInflater = LayoutInflater.from(context);
        this.costList = costBeanList;
    }

    @Override
    public int getCount() {
        return costList.size();
    }

    @Override
    public Object getItem(int position) {
        return costList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, @Nullable View convertView, @Nullable ViewGroup parent) {
        holder = null;
        CostBean costBean = costList.get(position);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.listview_item, null);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
            if (costBean != null){
//                holder.costIcon.setImageResource(costBean.getIcon());
                holder.tv_costType.setText(costBean.getCostType());
                holder.tv_AllType.setText(costBean.getAllType());
                holder.tv_costAmount.setText(String.valueOf(costBean.getCostAmount()));
                holder.tv_date.setText(costBean.getDate());

            }else{
            holder.linearLayout.setVisibility(View.GONE);
        }

        } else {
            holder = (MyViewHolder) convertView.getTag();
        }



        return convertView;
    }
class MyViewHolder extends RecyclerView.ViewHolder
{

    LinearLayout linearLayout;
    ImageView costIcon;
    TextView tv_AllType;
    TextView tv_costType;
    TextView tv_costAmount;
    TextView tv_date;
    public MyViewHolder(View view)
    {
        super(view);
        linearLayout = view.findViewById(R.id.lv_lnlayout);
        costIcon = view.findViewById(R.id.costType_icon);
        tv_AllType = view.findViewById(R.id.tv_AllType);
        tv_costType = view.findViewById(R.id.tv_costType);
        tv_costAmount = view.findViewById(R.id.tv_cost);
        tv_date = view.findViewById(R.id.tv_date);
    }
}
}
