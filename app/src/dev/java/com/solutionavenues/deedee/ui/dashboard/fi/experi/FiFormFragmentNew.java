package com.solutionavenues.deedee.ui.dashboard.fi.experi;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;

/**
 * Created by Azher on 13/7/18.
 */
public class FiFormFragmentNew extends AppBaseFragment {

    FiPersonalInfoFragment fiPersonalInfoFragment;
    FiAppraisalFragment fiAppraisalFragment;
    TabLayout tab_layout_fi;

    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_fi_new;
    }

    @Override
    public void initializeComponent() {
        tab_layout_fi = getView().findViewById(R.id.tab_layout_fi);
        setupTabLayout();
    }

    private void setupTabLayout() {
       /* fiPersonalInfoFragment = new FiPersonalInfoFragment();
        fiAppraisalFragment = new FiAppraisalFragment();
        fiAppraisalFragment = new FiAppraisalFragment();*/
        tab_layout_fi.addTab(tab_layout_fi.newTab().setText(getString(R.string.personal_info)), true);
        tab_layout_fi.addTab(tab_layout_fi.newTab().setText(getString(R.string.appraisal)));
        tab_layout_fi.addTab(tab_layout_fi.newTab().setText(getString(R.string.appraisal)));
        tab_layout_fi.addTab(tab_layout_fi.newTab().setText(getString(R.string.appraisal)));
        tab_layout_fi.addTab(tab_layout_fi.newTab().setText(getString(R.string.appraisal)));

        tab_layout_fi.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        addPersonalInfoFragment();
    }

    private void setCurrentTabFragment(int tabPosition) {
        switch (tabPosition) {
            case 0:
                addPersonalInfoFragment();
                break;
            case 1:
                addAppraisalFragment();
                break;
        }
    }

    private void addPersonalInfoFragment() {
        FiPersonalInfoFragment fragment = new FiPersonalInfoFragment();
        int enterAnimation = 0;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = 0;
        changeChildFragment(fragment, false, false, 0,
                enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);

    }

    private void addAppraisalFragment() {
        FiAppraisalFragment fragment = new FiAppraisalFragment();
        int enterAnimation = 0;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = 0;
        changeChildFragment(fragment, false, false, 0,
                enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);

    }

    @Override
    public int getFragmentContainerResourceId(Fragment fragment) {
        return R.id.child_container;
    }
}
