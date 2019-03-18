package com.solutionavenues.deedee.ui.dashboard.home;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;
import com.solutionavenues.deedee.model.DashboardItem;
import com.solutionavenues.deedee.ui.dashboard.appform.ApplicationFormFragment;
import com.solutionavenues.deedee.ui.dashboard.appform.myleads.LeadListFragment;
import com.solutionavenues.deedee.ui.dashboard.centerform.AddCenterFragment;
import com.solutionavenues.deedee.ui.dashboard.enquiry.AddEnquiryFragment;
import com.solutionavenues.deedee.ui.dashboard.fi.FiListFragment;
import com.solutionavenues.deedee.ui.dashboard.groupform.AddGroupFragment;
import com.solutionavenues.deedee.ui.dashboard.groupform.MyGroupListFragment;
import com.solutionavenues.deedee.ui.dashboard.locality.LocalityFragment;
import com.solutionavenues.deedee.ui.dashboard.recovery.activity.RecoveryGroupsActivity;
import com.solutionavenues.deedee.ui.dashboard.setting.SettingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Azher on 18/6/18.
 */
public class HomeFragment extends AppBaseFragment implements View.OnClickListener {

    private RelativeLayout rl_setting, rl_enquiry,
            rl_locality, rl_application_form,
            rl_my_leads, rl_group, rl_center_form, rl_fi,
            rl_added_fi, rl_my_group, rl_recovery;

    private RecyclerView recyclerView;
    private HomeListAdapter homeListAdapter;
    private List<DashboardItem> dashboardItemList;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initializeComponent() {
        rl_center_form = getView().findViewById(R.id.rl_center_form);
        rl_group = getView().findViewById(R.id.rl_group);
        rl_setting = getView().findViewById(R.id.rl_setting);
        rl_enquiry = getView().findViewById(R.id.rl_enquiry);
        rl_locality = getView().findViewById(R.id.rl_locality);
        rl_application_form = getView().findViewById(R.id.rl_application_form);
        rl_my_leads = getView().findViewById(R.id.rl_my_leads);
        rl_recovery = getView().findViewById(R.id.rl_recovery);
        rl_fi = getView().findViewById(R.id.rl_fi);
        rl_added_fi = getView().findViewById(R.id.rl_added_fi);
        rl_my_group = getView().findViewById(R.id.rl_my_group);
        rl_center_form.setOnClickListener(this);
        rl_group.setOnClickListener(this);
        rl_locality.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        rl_enquiry.setOnClickListener(this);
        rl_application_form.setOnClickListener(this);
        rl_my_leads.setOnClickListener(this);
        rl_recovery.setOnClickListener(this);
        rl_fi.setOnClickListener(this);
        rl_added_fi.setOnClickListener(this);
        rl_my_group.setOnClickListener(this);

        recyclerView = getView().findViewById(R.id.home_rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        String homeList[] = getResources().getStringArray(R.array.homescreen_item_arr);
        int roleType = getMyApplication().getUserPrefs().getLoggedInUser().getRole_id();
        dashboardItemList = new ArrayList<>();
        for (int i = 0; i < homeList.length; i++) {
            DashboardItem newDashBoard = new DashboardItem(i, homeList[i]);
            if (roleType == 18 && (i == 8 || i == 10)) {
                continue;
            }
            if (roleType == 31 && i == 10) {
                continue;
            }
            dashboardItemList.add(newDashBoard);
        }
        homeListAdapter = new HomeListAdapter(getActivity(), dashboardItemList, this);
        recyclerView.setAdapter(homeListAdapter);
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.hs_item_parent) {
            int pos_cgt = (int) v.getTag();
            DashboardItem dashboardItem = dashboardItemList.get(pos_cgt);
            switch (dashboardItem.getId()) {
                case 9:
                    addSettingFragment();
                    break;
                case 0:
                    addEnquiryFragment();
                    break;
                case 1:
                    addLocalityFragment();
                    break;
                case 6:
                    addAppFormFragment();
                    break;
                case 7:
                    addLeadListFragment();
                    break;
                case 2:
                    addGroupFormFragment();
                    break;
                case 4:
                    addCenterFormFragment();
                    break;
                case 5:
                    //addFiFormFragment();
                    break;
                case 8:
                    addFiListFragment();
                    break;
                case 3:
                    addMyGroupFragment();
                    break;
                case 10:
                    startActivity(new Intent(getActivity(), RecoveryGroupsActivity.class));
                    break;
            }
        }


    }

    private void addEnquiryFragment() {
        AddEnquiryFragment fragment = new AddEnquiryFragment();
        int enterAnimation = R.anim.translate_right_to_left_anim;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
        try {
            getDashBoardActivity().changeFragment(fragment, true, false, 0,
                    enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addSettingFragment() {
        SettingFragment fragment = new SettingFragment();
        int enterAnimation = R.anim.translate_right_to_left_anim;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
        try {
            getDashBoardActivity().changeFragment(fragment, true, false, 0,
                    enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addLocalityFragment() {
        LocalityFragment fragment = new LocalityFragment();
        int enterAnimation = R.anim.translate_right_to_left_anim;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
        try {
            getDashBoardActivity().changeFragment(fragment, true, false, 0,
                    enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addAppFormFragment() {
        ApplicationFormFragment fragment = new ApplicationFormFragment();
        int enterAnimation = R.anim.translate_right_to_left_anim;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
        try {
            getDashBoardActivity().changeFragment(fragment, true, false, 0,
                    enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addLeadListFragment() {
        LeadListFragment fragment = new LeadListFragment();
        int enterAnimation = R.anim.translate_right_to_left_anim;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
        try {
            getDashBoardActivity().changeFragment(fragment, true, false, 0,
                    enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addCenterFormFragment() {
        AddCenterFragment fragment = new AddCenterFragment();
        int enterAnimation = R.anim.translate_right_to_left_anim;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
        try {
            getDashBoardActivity().changeFragment(fragment, true, false, 0,
                    enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addGroupFormFragment() {
        AddGroupFragment fragment = new AddGroupFragment();
        int enterAnimation = R.anim.translate_right_to_left_anim;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
        try {
            getDashBoardActivity().changeFragment(fragment, true, false, 0,
                    enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void addFiListFragment() {
        FiListFragment fragment = new FiListFragment();
        int enterAnimation = R.anim.translate_right_to_left_anim;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
        try {
            getDashBoardActivity().changeFragment(fragment, true, false, 0,
                    enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void addMyGroupFragment() {
        MyGroupListFragment fragment = new MyGroupListFragment();
        int enterAnimation = R.anim.translate_right_to_left_anim;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
        try {
            getDashBoardActivity().changeFragment(fragment, true, false, 0,
                    enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
