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
 * {@link SetmoneyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SetmoneyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetmoneyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private EditText editText;
    private Button button;
    private int id,i_money,i_nowmoney;
    private String str_money = "";
    private String str;
    private int int_id;
    private String str_nowmoney;
    private String str_remoney;
    private String str_username;
    private String str_paswd;
    private MenusActivity menusActivity;
    private user_database us_wri;
    private SQLiteDatabase sqLiteDatabase,sqLiteDatabase_update;
    private List<userInfo> list;
    private Bundle bundle;

    public SetmoneyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetmoneyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SetmoneyFragment newInstance(String param1, String param2) {
        SetmoneyFragment fragment = new SetmoneyFragment();
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
        View view = inflater.inflate(R.layout.fragment_setmoney, container, false);

        editText = view.findViewById(R.id.et_setmoeny);
        button = view.findViewById(R.id.frag_setmoney);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_money = editText.getText().toString();
                if("".equals(str_money)){
                    Toast.makeText(button.getContext(),"您还没有输入任何金额！",Toast.LENGTH_SHORT).show();
                }else {
                    i_money = Integer.parseInt(str_money);
                    if (i_money <= 5000 && i_money % 100 == 0 && i_money>0) {
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
                        str_nowmoney = list.get(id).getUsermoney();
                        i_nowmoney = Integer.parseInt(str_nowmoney);
                        str_remoney = String.valueOf(i_nowmoney + i_money);;
                        sqLiteDatabase_update = us_wri.getWritableDatabase();
                        us_wri.update(sqLiteDatabase_update,int_id,str_username,str_paswd,str_remoney);
                        Toast.makeText(button.getContext(), "存款成功！", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(button.getContext(), "每次只能存整且不能超过5000元！", Toast.LENGTH_SHORT).show();
                    }
                }
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
