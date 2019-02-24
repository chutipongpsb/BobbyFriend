package com.p.chutipong.bobbyfriend;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Register Controller
        registerController();

//        Login Controller
        loginController();


    }   // Main Method

    private void loginController() {
        Button button = getView().findViewById(R.id.btnLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userEditText = getView().findViewById(R.id.edtUser);
                EditText passwordEditor = getView().findViewById(R.id.edtPassword);

                String user = userEditText.getText().toString().trim();
                String password = passwordEditor.getText().toString().trim();

                MyAlert myAlert = new MyAlert(getActivity());
                if (user.isEmpty() || password.isEmpty()) {
                    myAlert.normalDialog("Have Space", "Please Fill All BlanK");

                } else {

                    try {

                        GetUserWhereUserThread getUserWhereUserThread = new GetUserWhereUserThread(getActivity());
                        getUserWhereUserThread.execute(user);
                        String json = getUserWhereUserThread.get();
                        Log.d("24FebV1", "json ==>" + json);

                        if (json.equals("null")) {
                        } else {
                            JSONArray jsonArray = new JSONArray(json);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);

                            if (password.equals(jsonObject.getString("password"))) {
                                Toast.makeText(getActivity(), "Welcome" + jsonObject.getString("Name"), Toast.LENGTH_SHORT).show();
                            } else {
                                myAlert.normalDialog("Password", "Password False");

                            }

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } //if
            }
        });

    }

    private void registerController() {
        TextView textView = getView().findViewById(R.id.txtRegister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Replace Fragment
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentMainFragment, new RegisterFragment()).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

}   //Main Class
