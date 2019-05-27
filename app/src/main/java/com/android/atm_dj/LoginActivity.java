package com.android.atm_dj;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_login,btn_show;
    private EditText edt_number,edt_paswd;
    private boolean Bigflag = false;
    private int Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initshow();
        init();

    }

    //创建了默认账号和密码
    private void initshow() {
        user_database user1 = new user_database(LoginActivity.this);
        SQLiteDatabase database1 = user1.getReadableDatabase();
        List<userInfo> list = user1.querydata(database1);
        if(list.isEmpty()){
            user_database user2 = new user_database(LoginActivity.this);
            SQLiteDatabase database2 = user2.getWritableDatabase();
            user2.adddata(database2,"123456","123456","10000");
        }
    }

    //初始化方法
    private void init() {
        btn_login = findViewById(R.id.btn_login);
        btn_show = findViewById(R.id.btn_show);
        edt_number = findViewById(R.id.edt_number);
        edt_paswd = findViewById(R.id.edt_paswd);
        btn_login.setOnClickListener(this);
        btn_show.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                if(Isempty()){
                    if(IsuserNot()){
                        Bigflag = false;
                        if(IsUserPaswd()){
                            Intent intent = new Intent(LoginActivity.this,MenusActivity.class);
                            //ps 这里传过去的id并不是sql里面的主键的id，而是这一条数据在整个sql里面的第几条
                            intent.putExtra("sql_id",String.valueOf(Id));
                            startActivity(intent);
                        }
                    }
                }
                break;
            case R.id.btn_show:
//                Toast.makeText(this,"我是一个待命的按钮！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this,ShowUserinfoActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    //判断用户输入框是否为空
    private boolean Isempty() {
        boolean flag = false;
        String str_munber = edt_number.getText().toString();
        String str_paswd = edt_paswd.getText().toString();
        if("".equals(str_munber)&&"".equals(str_paswd)){
            Toast.makeText(this,"卡号和密码不能为空！",Toast.LENGTH_SHORT).show();
        }else if("".equals(str_munber)){
            Toast.makeText(this,"卡号不能为空！",Toast.LENGTH_SHORT).show();
        }else if ("".equals(str_paswd)){
            Toast.makeText(this,"密码不能为空！",Toast.LENGTH_SHORT).show();
        }else flag = true;
        return flag;
    }

    //判断数据库中是否存在该账户
    private boolean IsuserNot() {
        String str_munber = edt_number.getText().toString();

        user_database us_read = new user_database(LoginActivity.this);
        SQLiteDatabase sqLiteDatabase =us_read.getReadableDatabase();
        List<userInfo> list = us_read.querydata(sqLiteDatabase);
        //遍历获取到的信息与用户填入的卡号匹配
        for(int i = 0; i < list.size(); i++){
            String user_mes = "";
            user_mes = list.get(i).getUsername();
            if(user_mes.equals(str_munber)){
                Bigflag = true;
                break;
            }
        }
        //数据库中没有用户填写的卡号的情况
        if(!Bigflag){
            new AlertDialog.Builder(LoginActivity.this).setTitle("系统提示")
                    .setMessage("您输入的账户不存在于系统中\n是否创建账户"+str_munber)
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            CreateUsername();
                            Bigflag = true;
                        }
                    })
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(LoginActivity.this,"可以输入123456作为测试卡号和密码",Toast.LENGTH_LONG).show();
                        }
                    }).show();
        }
        return Bigflag;
    }

    //创建该账户
    private void CreateUsername() {
        String str_munber = edt_number.getText().toString();
        String str_paswd = edt_paswd.getText().toString();
        String str_money = String.valueOf(10000);
        user_database us_wri = new user_database(LoginActivity.this);
        SQLiteDatabase sqLiteDatabase = us_wri.getWritableDatabase();
        us_wri.adddata(sqLiteDatabase,str_munber,str_paswd,str_money);
        Toast.makeText(LoginActivity.this,"创建成功！\n"+"账户为"+str_munber+" 密码为"+str_paswd,Toast.LENGTH_LONG).show();
    }

    //判断账户名和密码是否匹配
    private boolean IsUserPaswd() {
        boolean flag = false;
        String str_munber = edt_number.getText().toString();
        String str_paswd = edt_paswd.getText().toString();
        user_database us_read = new user_database(LoginActivity.this);
        SQLiteDatabase sqLiteDatabase =us_read.getReadableDatabase();
        List<userInfo> list = us_read.querydata(sqLiteDatabase);
        for(int i = 0; i < list.size(); i++){
            String username = list.get(i).getUsername();
            if(str_munber.equals(username)){
                Id = i;
                String paswd = list.get(Id).getPaswd();
                if  (str_paswd.equals(paswd)){
                    flag = true;
                    break;
                } else Toast.makeText(LoginActivity.this,"输入的密码不正确！",Toast.LENGTH_SHORT).show();
            }
        }
        return flag;
    }
}