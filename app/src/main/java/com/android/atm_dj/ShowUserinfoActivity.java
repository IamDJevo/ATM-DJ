package com.android.atm_dj;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ShowUserinfoActivity extends AppCompatActivity {

    public ListView user_list;
    private List<userInfo> list;
    private SQLiteDatabase sqLiteDatabase;
    private String[] uesr_mes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_userinfo);

        user_database user_info = new user_database(ShowUserinfoActivity.this);
        sqLiteDatabase = user_info.getReadableDatabase();
        list = user_info.querydata(sqLiteDatabase);
        uesr_mes = new String[list.size()];
        for(int i=0;i<list.size();i++){
            uesr_mes[i]=list.get(i).toString();
        }

        //把获取到信息显示到ListView中
        user_list =findViewById(R.id.mes);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,uesr_mes);
        user_list.setAdapter(adapter);
        //为istView每个元素添加单击事件
        user_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int id =list.get(i).getId();
                new AlertDialog.Builder(ShowUserinfoActivity.this).setTitle("系统提示")
                        .setMessage("确定删除该条数据吗！")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                user_database user_del = new user_database(ShowUserinfoActivity.this);
                                SQLiteDatabase database = user_del.getWritableDatabase();
                                user_del.delete(database,id);
                                refresh();
                                Toast.makeText(ShowUserinfoActivity.this,"删除成功！",Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
            }
        });
    }

    //刷新页面方法
    private void refresh() {
        finish();
        Intent intent = new Intent(ShowUserinfoActivity.this,ShowUserinfoActivity.class);
        startActivity(intent);
    }
}
