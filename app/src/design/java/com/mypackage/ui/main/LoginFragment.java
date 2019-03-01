package com.mypackage.ui.main;

import android.view.View;
import android.widget.TextView;

import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;

/**
 * Created by Azher on 18/6/18.
 */
public class LoginFragment extends AppBaseFragment{
    private TextView tv_forgot_password,tv_login;
    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_login;
    }

    @Override
    public void initializeComponent() {
        tv_forgot_password=getView().findViewById(R.id.tv_forgot_password);
        tv_login=getView().findViewById(R.id.tv_login);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_login:
                break;
            case R.id.tv_forgot_password:
                break;


        }

    }
}
