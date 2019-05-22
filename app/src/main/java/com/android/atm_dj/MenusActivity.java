package com.android.atm_dj;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MenusActivity extends AppCompatActivity implements
        WelcomeFragment.OnFragmentInteractionListener,QueryFragment.OnFragmentInteractionListener,
        GetmoneyFragment.OnFragmentInteractionListener,SetmoneyFragment.OnFragmentInteractionListener,
        FixpaswdFragment.OnFragmentInteractionListener{

    String str_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menus);

        Intent intent = this.getIntent();
        str_id = intent.getStringExtra("sql_id");

        WelcomeFragment welcomeFragment = WelcomeFragment.newInstance("","");
        FragmentManager manager = MenusActivity.this.getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("key_id",str_id);
        welcomeFragment.setArguments(bundle);
        FragmentTransaction transaction =manager.beginTransaction();
        transaction.add(R.id.fragment_layout,welcomeFragment);
        transaction.commit();

        Button b_query = findViewById(R.id.btn_query);
        b_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QueryFragment queryFragment = new QueryFragment();
                FragmentManager manager = MenusActivity.this.getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("key_id",str_id);
                queryFragment.setArguments(bundle);
                FragmentTransaction transaction =manager.beginTransaction();
                transaction.replace(R.id.fragment_layout,queryFragment);
                transaction.commit();
                setMessage("查询余额");
            }
        });

        Button b_getmoney = findViewById(R.id.btn_getmoney);
        b_getmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetmoneyFragment getmoneyFragment = new GetmoneyFragment();
                FragmentManager manager = MenusActivity.this.getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("key_id",str_id);
                getmoneyFragment.setArguments(bundle);
                FragmentTransaction transaction =manager.beginTransaction();
                transaction.replace(R.id.fragment_layout,getmoneyFragment);
                transaction.commit();
                setMessage("取款");
            }
        });

        Button b_setmoney = findViewById(R.id.btn_setmoney);
        b_setmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetmoneyFragment setmoneyFragment = new SetmoneyFragment();
                FragmentManager manager = MenusActivity.this.getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("key_id",str_id);
                setmoneyFragment.setArguments(bundle);
                FragmentTransaction transaction =manager.beginTransaction();
                transaction.replace(R.id.fragment_layout,setmoneyFragment);
                transaction.commit();
                setMessage("存款");
            }
        });

        Button b_fixpaswd = findViewById(R.id.btn_fixpaswd);
        b_fixpaswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FixpaswdFragment fixpaswdFragment = new FixpaswdFragment();
                FragmentManager manager = MenusActivity.this.getSupportFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("key_id",str_id);
                fixpaswdFragment.setArguments(bundle);
                FragmentTransaction transaction =manager.beginTransaction();
                transaction.replace(R.id.fragment_layout,fixpaswdFragment);
                transaction.commit();
                setMessage("修改密码");
            }
        });


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void setMessage(String msg){
        TextView show_message = this.findViewById(R.id.show_message);
        show_message.setText(msg);
    }

}
