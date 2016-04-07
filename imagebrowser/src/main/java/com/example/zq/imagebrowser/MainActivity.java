package com.example.zq.imagebrowser;
import java.util.ArrayList;
import java.util.HashMap;



import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity implements OnClickListener,
        OnItemClickListener {

    private ListView listView;
    private Button ok;
    private ArrayList<Food> foods = new ArrayList<Food>();
    private MyListViewAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();// 初始化控件

        initData();// 初始化虚拟数据

        adapter = new MyListViewAdapter(foods, getApplicationContext());
        listView.setAdapter(adapter);
    }

    /**
     * 初始化控件
     * */
    public void initView() {
        listView = (ListView) findViewById(R.id.drink_list);// listview列表控件
        ok = (Button) findViewById(R.id.order_btn);// 确定按钮

        ok.setOnClickListener(this);
        listView.setOnItemClickListener(this);
    }

    /**
     * 初始化虚拟数据
     * */
    public void initData() {




        Class cls = R.drawable.class;
        try {
            foods.add(new Food(cls.getDeclaredField("d1").getInt(null), "猕猴桃汁",
                    "10"));
            foods.add(new Food(cls.getDeclaredField("d2").getInt(null), "橙汁",
                    "12"));
            foods.add(new Food(cls.getDeclaredField("d3").getInt(null), "啤酒",
                    "15"));
            foods.add(new Food(cls.getDeclaredField("d4").getInt(null), "葡萄汁",
                    "10"));
            foods.add(new Food(cls.getDeclaredField("d5").getInt(null), "纯麦奶茶",
                    "8"));
            foods.add(new Food(cls.getDeclaredField("d6").getInt(null), "薄荷汁",
                    "10"));
            foods.add(new Food(cls.getDeclaredField("d7").getInt(null), "柠檬薄荷",
                    "12"));
            foods.add(new Food(cls.getDeclaredField("d8").getInt(null), "椰子汁",
                    "10"));
            foods.add(new Food(cls.getDeclaredField("d9").getInt(null), "珍珠奶茶",
                    "9"));
            foods.add(new Food(cls.getDeclaredField("d10").getInt(null), "石榴汁",
                    "10"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 按钮的点击事件处理
     * */
    @Override
    public void onClick(View v) {
        int mID = v.getId();
        switch (mID) {
            case R.id.order_btn:
                myPrice();// 计算总价并输出
                break;
        }
    }

    /**
     * 计算总价格的方法
     * */
    public void myPrice() {
        HashMap<Integer, Boolean> map = MyListViewAdapter.getIsSelected();
        String str = "";
        int money = 0;
        for (int i = 0; i < map.size(); i++) {
            if (map.get(i)) {
                str += (i + " ");
                money += Integer.parseInt(foods.get(i).food_price);
            }
        }
        MyListViewAdapter.getIsSelected().get("");
        Toast.makeText(getApplicationContext(),
                "已选中了" + str + "项,总价钱为:" + money, Toast.LENGTH_SHORT).show();
    }

    /**
     * listview的item的选择的方法
     * */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
        MyListViewAdapter.ViewHolder holder = (MyListViewAdapter.ViewHolder) view.getTag();
        // 改变CheckBox的状态
        holder.cb.toggle();
        // 将CheckBox的选中状况记录下来
        MyListViewAdapter.getIsSelected().put(position, holder.cb.isChecked());

    }
}
