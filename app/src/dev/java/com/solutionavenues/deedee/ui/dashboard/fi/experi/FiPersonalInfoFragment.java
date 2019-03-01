package com.solutionavenues.deedee.ui.dashboard.fi.experi;

import android.support.v4.app.Fragment;

import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;

/**
 * Created by Azher on 13/7/18.
 */
public class FiPersonalInfoFragment extends AppBaseFragment{
    @Override
    public int getLayoutResourceId() {
        return R.layout.layout_fi_personal_info;
    }

    @Override
    public void initializeComponent() {

    }

    @Override
    public int getFragmentContainerResourceId(Fragment fragment) {
        return R.id.child_container;
    }
}
