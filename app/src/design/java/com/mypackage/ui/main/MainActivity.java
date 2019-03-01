package com.mypackage.ui.main;

import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseActivity;

/**
 * Created by Azher on 18/6/18.
 */
public class MainActivity extends AppBaseActivity{
    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_main;

    }

    @Override
    public void initializeComponent() {
        addLoginFragment();
    }


    private void addLoginFragment() {
        LoginFragment fragment = new LoginFragment();
        int enterAnimation = R.anim.translate_right_to_left_anim;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
        changeFragment(fragment, true, false, 0,
                enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
    }

    @Override
    public int getFragmentContainerResourceId() {
        return R.id.main_container;
    }
}
