package com.android.atm_dj;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FixpaswdFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FixpaswdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FixpaswdFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private EditText et_frag_paswd,et_frag_newpaswd,et_frag_renewpaswd;
    private Button button;
    private int id,int_id;
    private String str,str_username,str_paswd,str_money,str_frag_paswd = "",str_frag_newpaswd = "",str_frag_renewpaswd = "";
    private MenusActivity menusActivity;
    private user_database us_wri;
    private SQLiteDatabase sqLiteDatabase,sqLiteDatabase_update;
    private List<userInfo> list;
    private Bundle bundle;

    public FixpaswdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FixpaswdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FixpaswdFragment newInstance(String param1, String param2) {
        FixpaswdFragment fragment = new FixpaswdFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fixpaswd, container, false);

        et_frag_paswd = view.findViewById(R.id.frag_paswd);
        et_frag_newpaswd = view.findViewById(R.id.frag_newpaswd);
        et_frag_renewpaswd = view.findViewById(R.id.frag_renewpaswd);
        button = view.findViewById(R.id.frag_fixpaswd);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_frag_paswd = et_frag_paswd.getText().toString();
                str_frag_newpaswd = et_frag_newpaswd.getText().toString();
                str_frag_renewpaswd = et_frag_renewpaswd.getText().toString();
                //判断所有的编辑框是否都填写了
                if("".equals(str_frag_paswd) || "".equals(str_frag_newpaswd) || "".equals(str_frag_renewpaswd)){
                    Toast.makeText(button.getContext(),"您还没有填写完！",Toast.LENGTH_SHORT).show();
                }else {
                    //判断两次新密码输入相同
                    if(str_frag_newpaswd.equals(str_frag_renewpaswd)) {
                        //判断新密码位数不小于6位
                        if(str_frag_newpaswd.length()>=6){
                            //判断新密码位数是否6位完全相同
                            if(Same(str_frag_newpaswd)){
                                menusActivity = (MenusActivity) getActivity();
                                us_wri = new user_database(menusActivity);
                                sqLiteDatabase = us_wri.getWritableDatabase();
                                list = us_wri.querydata(sqLiteDatabase);
                                bundle = getArguments();
                                str = bundle.getString("key_id");
                                id = Integer.parseInt(str);
                                int_id = list.get(id).getId();
                                str_username = list.get(id).getUsername();
                                str_paswd = list.get(id).getPaswd();
                                str_money = list.get(id).getUsermoney();
                                //验证旧密码是否输入正确
                                if(str_paswd.equals(str_frag_paswd)){
                                    sqLiteDatabase_update = us_wri.getWritableDatabase();
                                    us_wri.update(sqLiteDatabase_update,int_id,str_username,str_frag_newpaswd,str_money);
                                    Toast.makeText(button.getContext(), "修改成功！\n新密码为 "+str_frag_newpaswd, Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(button.getContext(), "旧密码输入错误！", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(button.getContext(), "新密码不允许完全相同！", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(button.getContext(), "新密码长度不小于6位", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(button.getContext(), "两次输入的新密码不一样！", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            //判断字符串里的字符是否全部一样
            private boolean Same(String string) {
                boolean flag = false;
                int t=0;
                if (string.length()<=1){return false;}
                for(int i=0;i<string.length();i++) {
                    if(string.charAt(0)==string.charAt(i)){
                        t++;
                    }
                }
                if (t != string.length()){
                    flag =true;
                }
                return flag;
            }

        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
