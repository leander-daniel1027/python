package com.solutionavenues.deedee.imagePicker;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.base.BaseDialogFragment;
import com.solutionavenues.deedee.BuildConfig;
import com.solutionavenues.deedee.R;


/**
 * Created by bitu on 9/9/17.
 */

public class ImagePickDialog extends BaseDialogFragment {

    public static final String TAG = "ImagePickDialog";

    LinearLayout ll_layout;
    LinearLayout ll_camera_lay;
    LinearLayout ll_gallery_lay;
    LinearLayout ll_remove_lay;
    private boolean isCamera;
    public static ImagePickDialog imagePickDialog;

    public static ImagePickDialog getInstance() {
        return imagePickDialog;
    }

    CameraGallerySelectorHelper cameraGallerySelectorHelper = new CameraGallerySelectorHelper();

    ProfilePicDialogListner profilePicDialogListner;

    public ProfilePicDialogListner getProfilePicDialogListner() {
        return profilePicDialogListner;
    }

    public void setProfilePicDialogListner(ProfilePicDialogListner profilePicDialogListner) {
        this.profilePicDialogListner = profilePicDialogListner;
    }

    public static ImagePickDialog getNewInstance(boolean isCamera) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isCamera", isCamera);
        ImagePickDialog confirmationDialog = new ImagePickDialog();
        confirmationDialog.setArguments(bundle);
        return confirmationDialog;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.dialog_profile_pic;
    }

    @Override
    public void initializeComponent() {
        imagePickDialog =this;
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            this.isCamera = bundle.getBoolean("isCamera", false);
        }
        ll_layout = getView().findViewById(R.id.ll_layout);
        ll_camera_lay = getView().findViewById(R.id.ll_camera_lay);
        ll_camera_lay.setOnClickListener(this);
        ll_gallery_lay = getView().findViewById(R.id.ll_gallery_lay);
        ll_gallery_lay.setOnClickListener(this);
        ll_remove_lay = getView().findViewById(R.id.ll_remove_lay);
        ll_remove_lay.setOnClickListener(this);
        if (isCamera){
            ll_camera_lay.performClick();
        }else {
            ll_layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getFragmentContainerResourceId() {
        return -1;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_camera_lay:
                cameraGallerySelectorHelper.callCamera(this);
                break;
            case R.id.ll_gallery_lay:
                cameraGallerySelectorHelper.callGallery(this);
                break;
            case R.id.ll_remove_lay:
                if (getProfilePicDialogListner() != null) {
                    getProfilePicDialogListner().onProfilePicRemoved();
                }
                dismiss();
                break;
        }
    }

    private void printLog(String msg) {
        if (BuildConfig.DEBUG && msg != null) {
            Log.e(TAG, msg);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        FileInformation fileInformation = cameraGallerySelectorHelper.handleOnActivityResult(getActivity(),
                requestCode, resultCode, data);
        if (fileInformation != null) {
            printLog("selected file path= " + fileInformation.getFilePath());
            if (getProfilePicDialogListner() != null) {
                getProfilePicDialogListner().onProfilePicSelected(fileInformation);
            }
        }
        if (getView() != null) {
            getView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismiss();
                }
            }, 200);
        }

    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawableResource(R.color.colorPrimary_00);
        LayoutInflater inflate = LayoutInflater.from(getActivity());
        View layout = inflate.inflate(getLayoutResourceId(), null);

        //set dialog view
        dialog.setContentView(layout);
        //setup dialog window param
        WindowManager.LayoutParams wlmp = dialog.getWindow().getAttributes();
        wlmp.windowAnimations = R.style.BadeDialogStyle;
        wlmp.gravity = Gravity.BOTTOM;
        wlmp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wlmp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.setTitle(null);
        setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
    }


    public void callbackToApp() {
        dismiss();
    }

    public interface ProfilePicDialogListner {
        void onProfilePicSelected(FileInformation fileInformation);

        void onProfilePicRemoved();
    }
}