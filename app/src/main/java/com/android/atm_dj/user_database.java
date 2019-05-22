package com.android.atm_dj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class user_database extends SQLiteOpenHelper {
    public user_database(Context context){
        super(context,"user_db",null,1);
    }


    //数据库第一次创建时调用该方法
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //数据库执行语句
        String sql = "create table user(id integer primary key autoincrement,username varchar(20),paswd varchar(20),usermoney varchar(20))";
        sqLiteDatabase.execSQL(sql);
    }

    //数据库版本号更新时调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //添加数据
    public void adddata(SQLiteDatabase sqLiteDatabase, String username, String paswd, String usermoney) {
        ContentValues values = new ContentValues();
        values.put("username",username);
        values.put("paswd",paswd);
        values.put("usermoney",usermoney);
        sqLiteDatabase.insert("user",null,values);
        sqLiteDatabase.close();
    }

    //删除数据
    public void delete(SQLiteDatabase sqLiteDatabase, int id) {
        //第一个参数：表名；第二个参数需要删除的属性名，?代表占位符；第三个参数：属性名的属性值
        sqLiteDatabase.delete("user","id=?",new String[]{id+""});
        sqLiteDatabase.close();
    }

    //更新数据
    public void update(SQLiteDatabase sqLiteDatabase, int id, String username, String paswd, String usermoney) {
        //创建一个ContentValues对象
        ContentValues values = new ContentValues();
        //以键值对的形式插入
        values.put("username",username);
        values.put("paswd",paswd);
        values.put("usermoney",usermoney);
        //执行修改的方法
        sqLiteDatabase.update("user",values,"id=?",new String[]{id+""});
        sqLiteDatabase.close();
    }

    //查询数据
    public List<userInfo> querydata(SQLiteDatabase sqLiteDatabase){
        Cursor cursor = sqLiteDatabase.query("user",null,null,null,null,null,"id ASC");
        List<userInfo> list = new ArrayList<userInfo>();
        while(cursor.moveToNext()){
            int id =cursor.getInt(cursor.getColumnIndex("id"));
            String username = cursor.getString(1);
            String paswd = cursor.getString(2);
            String usermoney = cursor.getString(3);
            list.add(new userInfo(id,username,paswd,usermoney));
        }
        cursor.close();
        sqLiteDatabase.close();
        return list;
    }

}