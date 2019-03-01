package com.solutionavenues.deedee.ui.dashboard.home;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;
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

/**
 * Created by Azher on 18/6/18.
 */
public class HomeFragment extends AppBaseFragment {

    private RelativeLayout rl_setting, rl_enquiry,
            rl_locality, rl_application_form,
            rl_my_leads, rl_group, rl_center_form, rl_fi,
            rl_added_fi, rl_my_group,rl_recovery;

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_setting:
                addSettingFragment();
                break;
            case R.id.rl_enquiry:
                addEnquiryFragment();
                break;
            case R.id.rl_locality:
                addLocalityFragment();
                break;
            case R.id.rl_application_form:
                addAppFormFragment();
                break;
            case R.id.rl_my_leads:
                addLeadListFragment();
                break;
            case R.id.rl_group:
                addGroupFormFragment();
                break;
            case R.id.rl_center_form:
                addCenterFormFragment();
                break;
            case R.id.rl_fi:
                //addFiFormFragment();
                break;
            case R.id.rl_added_fi:
                addFiListFragment();
                break;
            case R.id.rl_my_group:
                addMyGroupFragment();
                break;
            case R.id.rl_recovery:
                 startActivity(new Intent(getActivity(), RecoveryGroupsActivity.class));
                break;


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
