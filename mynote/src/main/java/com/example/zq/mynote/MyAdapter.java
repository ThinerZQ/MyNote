package com.example.zq.mynote;

import android.content.Context;
import android.text.BoringLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Michael on 2015/2/2.
 */
public  class MyAdapter extends BaseAdapter {

    public static HashMap<Integer,Boolean> isChecked;
    private Context context = null;
    private LayoutInflater inflater = null;
    private List<Map<String, Object>> list = null;
    private  HashMap<Integer, View> map = new HashMap<Integer, View>();

    public MyAdapter(Context context,List<Map<String, Object>> list) {

        this.list = list;
        inflater = LayoutInflater.from(context);
        isChecked = new HashMap<Integer,Boolean>();
        Log.i("hashmapkkk",list.size()+" ");
        for(int i=0;i<list.size();i++) {
            isChecked.put(i,false);
        }
    }




    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {

        View view;
        ViewHolder holder = null;
        if(map.get(position)==null){

            view = inflater.inflate(R.layout.data_list1, null);
            holder = new ViewHolder();
            holder.tv = (TextView) view.findViewById(R.id.noteText1);
            holder.cb = (CheckBox) view.findViewById(R.id.checkbox1);

            holder.tv_flag= (TextView) view.findViewById(R.id.noteFlag1);
            final int p=position;
            map.put(position,view);
            holder.cb.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    CheckBox cb = (CheckBox)v;
                    isChecked.put(p, cb.isChecked());
                }
            });
            view.setTag(holder);
        }else {
            view= map.get(position);
            holder = (ViewHolder) view.getTag();

        }
        Map<String, Object> map3 = (Map<String,Object>)list.get(position);
        Log.i("listsize",list.size()+"");
        //Log.i("listsize",(String)list.get(2).get("noteFlag"));
        holder.tv_flag.setText((String)map3.get("noteFlag"));
        holder.tv.setText((String) map3.get("noteText"));
        holder.cb.setChecked(isChecked.get(position));
        return view;
    }


}
