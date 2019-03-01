package com.mypackage.ui.helpers;

import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.BaseFragment;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.ui.dashboard.DashboardActivity;


/**
 * Created by Sunil kumar yadav on 27/2/18.
 */

public class ToolBarHelper implements View.OnClickListener {

    DashboardActivity dashboardActivity;
    RelativeLayout ll_tool_bar;

    ImageView iv_menu_left;
    ImageView iv_back;
    TextView tv_title;
    ImageView iv_right_menu;
    TextView tv_report;

    public ToolBarHelper(DashboardActivity dashboardActivity) {
        this.dashboardActivity = dashboardActivity;
        initializeComponent();
    }

    private void initializeComponent() {
        ll_tool_bar = (RelativeLayout) dashboardActivity.findViewById(R.id.ll_tool_bar);
        iv_menu_left = (ImageView) dashboardActivity.findViewById(R.id.iv_menu_left);
        iv_back = (ImageView) dashboardActivity.findViewById(R.id.iv_back);

        tv_title = (TextView) dashboardActivity.findViewById(R.id.tv_title);
        iv_back.setVisibility(View.GONE);
        iv_menu_left.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_right_menu.setOnClickListener(this);
        tv_report.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu_left:
                break;
            case R.id.iv_back:
                dashboardActivity.onBackPressed();
                break;

        }
    }

    private Resources getResources() {
        return dashboardActivity.getResources();
    }

    private boolean isViewVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    private boolean isViewHide(View view) {
        return view.getVisibility() == View.GONE || view.getVisibility() == View.INVISIBLE;
    }

    public void onCreateViewFragment(BaseFragment baseFragment) {
        dashboardActivity.hideKeyboard();

    }

    public void onDestroyViewFragment(BaseFragment baseFragment) {

    }



}
