package com.solutionavenues.deedee.ui.dashboard.setting;

import android.content.DialogInterface;
import android.view.View;
import android.widget.RelativeLayout;

import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;
import com.solutionavenues.deedee.util.Utils;

/**
 * Created by Azher on 18/6/18.
 */
public class SettingFragment extends AppBaseFragment {
    private RelativeLayout rl_change_password, rl_notification_setting,
            rl_user_profile, rl_set_server;
    String[] server_arr;
    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initializeComponent() {
       server_arr=getResources().getStringArray(R.array.server_arr);
        rl_change_password = getView().findViewById(R.id.rl_change_password);
        rl_notification_setting = getView().findViewById(R.id.rl_notification_setting);
        rl_user_profile = getView().findViewById(R.id.rl_user_profile);
        rl_set_server = getView().findViewById(R.id.rl_set_server);
        rl_change_password.setOnClickListener(this);
        rl_notification_setting.setOnClickListener(this);
        rl_user_profile.setOnClickListener(this);
        rl_set_server.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_change_password:
                addChangePasswordFragment();
                break;
            case R.id.rl_notification_setting:

                break;
            case R.id.rl_user_profile:

                break;
            case R.id.rl_set_server:
                openServerDialog();
                break;
        }
    }

    private void addChangePasswordFragment() {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
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


    private void openServerDialog() {

        Utils.hideKeyboard(getActivity());
        Utils.showAlertDialog(getActivity(),
                getString(R.string.select_server),
                server_arr,
                onClickedServerListner);
    }


    DialogInterface.OnClickListener onClickedServerListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            if (server_arr[item].equalsIgnoreCase("dee")) {
                getMyApplication().getAppPrefs().saveCurrentServer("https://dee.hravenues.com/webServices/");
            }else if (server_arr[item].equalsIgnoreCase("ddv")) {
                getMyApplication().getAppPrefs().saveCurrentServer("https://ddv.hravenues.com/webServices/");
            }
        }
    };

}
