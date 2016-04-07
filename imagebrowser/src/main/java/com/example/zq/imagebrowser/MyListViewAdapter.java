package com.example.zq.imagebrowser;

import java.util.ArrayList;
import java.util.HashMap;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class MyListViewAdapter extends BaseAdapter {
    // 填充数据的list
    private ArrayList<Food> foodlist;
    // 用来控制CheckBox的选中状况
    private static HashMap<Integer, Boolean> isSelected;
    // 上下文
    private Context context;
    // 用来导入布局
    private LayoutInflater inflater = null;

    // 构造器
    public MyListViewAdapter(ArrayList<Food> list, Context context) {
        this.context = context;
        this.foodlist = list;
        inflater = LayoutInflater.from(context);
        isSelected = new HashMap<Integer, Boolean>();
        // 初始化数据
        initDate();
    }

    // 初始化isSelected的数据
    private void initDate() {
        for (int i = 0; i < foodlist.size(); i++) {
            getIsSelected().put(i, false);
        }
    }

    @Override
    public int getCount() {
        return foodlist.size();
    }

    @Override
    public Object getItem(int position) {
        return foodlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            // 获得ViewHolder对象
            holder = new ViewHolder();
            // 导入布局并赋值给convertview
            convertView = inflater.inflate(R.layout.item, null);
            holder.imageView = (ImageView) convertView
                    .findViewById(R.id.food_imager);
            holder.txt1 = (TextView) convertView.findViewById(R.id.food_name);
            holder.txt2 = (TextView) convertView.findViewById(R.id.price);
            holder.cb = (CheckBox) convertView.findViewById(R.id.check_box);
            // 为view设置标签
            convertView.setTag(holder);
        } else {
            // 取出holder
            holder = (ViewHolder) convertView.getTag();
        }
        // 获取数据
        Food food = foodlist.get(position);

        // 将数据填充到当前convertView的对应控件中

        holder.imageView.setImageResource(food.food_img);
        holder.txt1.setText(food.food_name);
        holder.txt2.setText(food.food_price);
        // 设置list中TextView的显示
        // 根据isSelected来设置checkbox的选中状况
        holder.cb.setChecked(getIsSelected().get(position));
        return convertView;
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        MyListViewAdapter.isSelected = isSelected;
    }

    public static class ViewHolder {
        public TextView txt1;
        public TextView txt2;
        public ImageView imageView;
        public CheckBox cb;
    }
}