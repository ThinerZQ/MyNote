package com.example.zq.mynote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Michael on 2015/1/29.
 */
public class NoteDao {
    private DatabaseHelper database = null;
    private SQLiteDatabase db = null;
    private DateFormat format1;

    public NoteDao(Context context) {
        database = new DatabaseHelper(context);//这段代码放到Activity类中才用this
        format1 = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    }

    public List<Map<String, Object>> findAllNote() {
        db = database.getReadableDatabase();
        Cursor c = db.query("note", null, null, null, null, null, "datetime desc");//查询并获得游标
        List<Map<String, Object>> tempData = new ArrayList<Map<String, Object>>();
        if (c.moveToFirst()) {//判断游标是否空
            for (int i = 0; i < c.getCount(); i++) {
                c.moveToPosition(i); //移动到指定记录

                String id = c.getString(c.getColumnIndex("id"));

                Long date = c.getLong(c.getColumnIndex("datetime"));


                Log.i("notehaomiao",date+"");

                String noteText = c.getString(c.getColumnIndex("noteText"));
                String noteFlag = noteText.substring(0,1);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", id);
                String strDate = format1.format(new Date(date));

                Log.i("notehaomiao",strDate);
                map.put("noteTime", strDate);
                map.put("noteText", noteText);
                map.put("noteFlag",noteFlag);
                tempData.add(map);
            }
        }
        db.close();
        return tempData;
    }

    public void insertNote(String id, String noteText) {

        db = database.getWritableDatabase();
        ContentValues cv = new ContentValues();//实例化一个ContentValues用来装载待插入的数据
        cv.put("id", id); //添加id
        cv.put("datetime", new Date().getTime()); //添加时间
        cv.put("noteText", noteText); //添加note

        db.insert("note", null, cv);

        db.close();


    }

    public void updateNote(String id ,String noteText) {
        db = database.getWritableDatabase();

        ContentValues cv = new ContentValues();//实例化一个ContentValues用来装载待插入的数据cv.put("username","Jack Johnson");//添加用户名
        cv.put("noteText", noteText);//添加要更改的字段及内容
        cv.put("datetime", new Date().getTime());//添加要更改的字段及内容
        String whereClause = "id=?";//修改条件
        String[] whereArgs = {id};//修改条件的参数
        db.update("note", cv, whereClause, whereArgs);//执行修改
        //Note note = new Note(id,date,noteText);
        db.close();
    }

    public void deleteNote(String id) {

        db = database.getWritableDatabase();

        String whereClause = "id=?";//删除条件
        String[] whereArgs = {id};//删除的值
        db.delete("note", whereClause, whereArgs);//执行删除
        db.close();

    }
    public void deletePatchNote(String[] ids) {

        db = database.getWritableDatabase();

        for(int i=0;i<ids.length;i++){
            String id = ids[i];
            String whereClause = "id=?";//删除条件
            String[] whereArgs = {id};//删除的值
            db.delete("note", whereClause, whereArgs);//执行删除
        }



        db.close();

    }
    public void deletePatchNote(ArrayList<String> ids) {

        db = database.getWritableDatabase();

        for(String id :ids){

            String whereClause = "id=?";//删除条件
            String[] whereArgs = {id};//删除的值
            db.delete("note", whereClause, whereArgs);//执行删除
        }



        db.close();

    }

    public long getMaxId() {
        db = database.getWritableDatabase();
        Cursor c = db.query("note", null, null, null, null, null, null);//查询并获得游标
        long i = -1;
        if (c.moveToLast()) {
            i = Long.parseLong(c.getString(c.getColumnIndex("id")));
        }
        c.close();
        db.close();
        return i;
    }
}
