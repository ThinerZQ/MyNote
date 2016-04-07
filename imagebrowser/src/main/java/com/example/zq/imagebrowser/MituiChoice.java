package com.example.zq.imagebrowser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;


public class MituiChoice extends Activity {

    Button showBtn;
    ListView mListView;
    List<Person> persionArr = new ArrayList<Person>();
    Context mContext;
    List<Integer> listItemID = new ArrayList<Integer>();
    MyListAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);// 绑定布局
        mContext = getApplicationContext();// 获取上下文对象

        // 获取布局中的控件
        showBtn = (Button) findViewById(R.id.show);
        mListView = (ListView) findViewById(R.id.lvperson);

        initPersonData();
        mAdapter = new MyListAdapter(persionArr);
        mListView.setAdapter(mAdapter);

        showBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                listItemID.clear();// 清空listItemID

                for (int i = 0; i < mAdapter.mChecked.size(); i++) {
                    if (mAdapter.mChecked.get(i)) {
                        listItemID.add(i);
                    }
                }

                if (listItemID.size() == 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(
                            MituiChoice.this);
                    builder1.setMessage("没有选中任何记录");
                    builder1.show();
                } else {// 如果列表不为空,在对话框上显示选中项的ID
                    StringBuilder sb = new StringBuilder();

                    for (int i = 0; i < listItemID.size(); i++) {// 遍历listItemID列表取得存放的每一项
                        sb.append("ItemID=" + listItemID.get(i) + " . ");
                    }
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(
                            MituiChoice.this);
                    builder2.setMessage(sb.toString());
                    builder2.show();// 显示对话框
                }
            }
        });
    }

    /**
     * 模拟数据
     */
    private void initPersonData() {
        Person mPerson;
        for (int i = 1; i <= 12; i++) {
            mPerson = new Person();
            mPerson.setName("Item" + i);
            mPerson.setAddress("这里是第" + i + "个选项");
            persionArr.add(mPerson);
        }
    }

    /**
     * 自定义ListView适配器
     */
    class MyListAdapter extends BaseAdapter {
        /** 标记CheckBox是否被选中 **/
        List<Boolean> mChecked;
        /** 存放要显示的Item数据 **/
        List<Person> listPerson;
        /** 一个HashMap对象 **/
        @SuppressLint("UseSparseArrays")
        HashMap<Integer, View> map = new HashMap<Integer, View>();

        public MyListAdapter(List<Person> list) {
            listPerson = new ArrayList<Person>();
            listPerson = list;

            mChecked = new ArrayList<Boolean>();
            for (int i = 0; i < list.size(); i++) {// 遍历且设置CheckBox默认状态为未选中
                mChecked.add(false);
            }
        }

        /**
         * 获取总项数
         */
        @Override
        public int getCount() {
            return listPerson.size();
        }

        /**
         * 获取指定子项
         */
        @Override
        public Object getItem(int position) {
            return listPerson.get(position);
        }

        /**
         * 获取指定子项的ID
         */
        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * 返回一个视图对象
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder = null;

            if (map.get(position) == null) {// 根据position判断View是否为空
                LayoutInflater mInflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = mInflater.inflate(R.layout.listitem, null);
                // 初始化ViewHolder对象
                holder = new ViewHolder();
                holder.selected = (CheckBox) view
                        .findViewById(R.id.list_select);
                holder.name = (TextView) view.findViewById(R.id.list_name);
                holder.address = (TextView) view
                        .findViewById(R.id.list_address);
                final int p = position;
                map.put(position, view);// 存储视图信息
                holder.selected.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        mChecked.set(p, cb.isChecked());// 设置CheckBox为选中状态
                    }
                });
                view.setTag(holder);
            } else {
                view = map.get(position);
                holder = (ViewHolder) view.getTag();
            }

            holder.selected.setChecked(mChecked.get(position));
            holder.name.setText(listPerson.get(position).getName());
            holder.address.setText(listPerson.get(position).getAddress());

            return view;
        }

    }

    /**
     * 常量类
     */
    static class ViewHolder {
        CheckBox selected;
        TextView name;
        TextView address;
    }
}