package com.example.zq.mynote;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private final static int REQUEST_CODE_NEW = 1;
    private final static int REQUEST_CODE_MODIFY = 2;
    private static final String TAG = "MainActivity";
    private static final int CONTEXTMENU_DELETEITEM = 0;
    private static final int CONTEXTMENU_UPDATEITEM = 1;
    private static final int OPTION_MENU_EDIT = 0;
    private static final int OPTION_MENU_DELETE =1 ;
    private static final int OPTION_MENU_SELECELTALL = 2;
    private static boolean  edit=false;

    private List<Map<String, Object>> data;
    private List<Map<String, Object>> datacheckbox;

    private ListView listView;
    private ListView listView1;

    private SimpleAdapter simpleAdapter = null; // 进行数据的转换操作
    private MyAdapter myAdapterForCheckbox = null; // 进行数据的转换操作

    private SQLiteDatabase db = null;
    private NoteDao noteDao = null;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //获取组件
        listView = (ListView) findViewById(R.id.listView);
        listView1 = (ListView) findViewById(R.id.listView1);

        data = new ArrayList<Map<String, Object>>();


        noteDao = new NoteDao(this);

        //定义单击事件
        listView.setOnItemClickListener(new OnItemClickListenerImpl()); // 单击选项

        //为listview注册上下文菜单
        registerForContextMenu(listView);

        //actionbar设置
        actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        menu.clear();
        super.onPrepareOptionsMenu(menu);


        if(edit==false) {
            MenuItem edit = menu.add(0, OPTION_MENU_EDIT, 0, "编辑");
            edit.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }else{
            MenuItem edit = menu.add(0, OPTION_MENU_DELETE, 0, "删除");
            edit.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
            MenuItem edit1 = menu.add(0, OPTION_MENU_SELECELTALL, 1, "全选");
            edit1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }

        Log.i("onPrepareOptionsMenu",edit+"");
        //getMenuInflater().inflate(R.menu.option_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);


        switch (item.getItemId()){
            case OPTION_MENU_EDIT:
                   Toast.makeText(this,"编辑",Toast.LENGTH_LONG).show();
                    listView.setVisibility(ListView.GONE);

                    listView1.setAdapter(myAdapterForCheckbox);
                    listView1.setVisibility(ListView.VISIBLE);
                    edit=true;
                    invalidateOptionsMenu();
                break;
            case OPTION_MENU_SELECELTALL:

                for(int i=0;i<data.size();i++) {
                    MyAdapter.isChecked.put(i, true);
                }
                invalidateOptionsMenu();
                break;

            case OPTION_MENU_DELETE:
                edit=false;

                ArrayList<Integer> list = new ArrayList<Integer>();
                ArrayList<String> list1 = new ArrayList<String>();
                for(int i=0;i<data.size();i++) {
                    if(MyAdapter.isChecked.get(i)){
                        list.add(i);
                    }
                }
                for(int i=0;i<list.size();i++) {
                   Map<String,Object> map = data.get(list.get(i));
                  list1.add((String)map.get("id"));
                }
                noteDao.deletePatchNote(list1);
                refreshNote();
                listView1.setVisibility(ListView.GONE);
                listView.setVisibility(ListView.VISIBLE);
                invalidateOptionsMenu();
                break;
        }
        return true;
    }
    public void newFile(View v) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, NewFileActivity.class);
        intent.putExtra("newFile", "请创建一个新note");
        startActivityForResult(intent, REQUEST_CODE_NEW);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_CODE_NEW) {
            if (resultCode == NewFileActivity.RESULT_CODE) {
                Bundle bundle = intent.getExtras();
                String noteText = bundle.getString("noteText");
                if (!noteText.trim().isEmpty()) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    long id = noteDao.getMaxId() + 1;
                    noteDao.insertNote(id + "", noteText);
                }
               // refreshNote();
                //listView.setAdapter(this.simpleAdapter);
                Toast.makeText(MainActivity.this, noteText, Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == REQUEST_CODE_MODIFY) {
            if (resultCode == ModifyActivity.RESULT_CODE) {
                Bundle bundle = intent.getExtras();
                String id = bundle.getString("id");
                String noteText = bundle.getString("noteText");
                Map<String, Object> map = new HashMap<String, Object>();
                if (!noteText.trim().isEmpty()) {
                    noteDao.updateNote(id, noteText);
                } else {
                    noteDao.deleteNote(id);
                }
                //listView.setAdapter(this.simpleAdapter);
                Toast.makeText(MainActivity.this, noteText, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onstart");
        refreshNote();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflator = new MenuInflater(this);
        inflator.inflate(R.menu.menu_main,menu);
        menu.setHeaderTitle("请选择");
        menu.setHeaderIcon(R.drawable.ic_launcher1);

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo amenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int positon = amenuInfo.position;
        Log.i("MainActivity", "" + positon);
        Map<String, Object> map = data.get(positon);
        String note_id = (String) map.get("id");
        switch (item.getItemId()) {
            case R.id.delete_note:
                Log.i("MainActivity", "delete");
                noteDao.deleteNote(note_id);
                refreshNote();
                break;
            case R.id.modify_note:
                Log.i("MainActivity", "update");
                String noteText = (String) map.get("noteText");
                String noteTime = (String) map.get("noteTime");
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ModifyActivity.class);
                intent.putExtra("id", note_id);
                intent.putExtra("noteText", noteText);
                intent.putExtra("noteTime",noteTime);
                startActivityForResult(intent, REQUEST_CODE_MODIFY);
                break;
            default:
                break;
        }
        return true;/* true means: "we handled the event". */
    }

    public void refreshNote() {

        data =noteDao.findAllNote();
        //使用适配器
        this.simpleAdapter = new SimpleAdapter(this, data,
                R.layout.data_list, new String[]{ "noteFlag","noteText"}  // Map中的key的名称
                , new int[]{R.id.noteFlag,R.id.noteText}); // 是data_list.xml中定义的组件的资源ID

        myAdapterForCheckbox = new MyAdapter(this,data);

        listView.setAdapter(this.simpleAdapter);

    }
    private class OnItemClickListenerImpl implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {

            Map<String, Object> map = (Map<String, Object>)data.get(position);
            String note_id = (String) map.get("id");
            String noteText = (String) map.get("noteText");
            String noteTime = (String) map.get("noteTime");
            Log.i("MainActivityNote",note_id);
            Log.i("MainActivityNote",noteTime);
            Log.i("MainActivityNote",noteText);
            Intent intent = new Intent();
            intent.setClass(MainActivity.this, ModifyActivity.class);
            intent.putExtra("id", note_id);
            intent.putExtra("noteText", noteText);
            intent.putExtra("noteTime",noteTime);
            startActivityForResult(intent, REQUEST_CODE_MODIFY);
        }
    }
}