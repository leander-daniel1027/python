package com.solutionavenues.deedee.ui.dashboard.grt;

import android.graphics.Bitmap;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;
import com.solutionavenues.deedee.imagePicker.FileInformation;
import com.solutionavenues.deedee.imagePicker.ImagePickDialog;
import com.solutionavenues.deedee.interfaces.OnOkClickListener;
import com.solutionavenues.deedee.model.request.AddGrtRequestModel;
import com.solutionavenues.deedee.model.response.BaseWebResponseModel;
import com.solutionavenues.deedee.model.response.GroupListResponseModel;
import com.solutionavenues.deedee.model.response.MemberListResponseModel;
import com.solutionavenues.deedee.model.response.UploadImageResponseModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.rest.BaseArguments;
import com.solutionavenues.deedee.rest.RetrofitUtils;
import com.solutionavenues.deedee.ui.dashboard.grt.adapter.MembersChildAdapter;
import com.solutionavenues.deedee.ui.dashboard.grt.adapter.QuestionListAdapter;
import com.utilities.ItemClickSupport;

import java.util.ArrayList;

import okhttp3.MultipartBody;

/**
 * Created by Azher on 23/7/18.
 */
public class AddGRTFragment extends AppBaseFragment implements
        ApiHitListener, OnOkClickListener, MembersChildAdapter.UpdateMembersList {
    private static final String TAG = "AddFiFragment";
    private TextView[] tabArr;
    private LinearLayout[] viewArr;
    private TextView tv_test_details, tv_result, tv_recommended;
    private LinearLayout ll_test_details, ll_result,
            ll_recommended;

    public void setGroupData(GroupListResponseModel.DataBean groupData) {
        this.groupData = groupData;
    }

    public GroupListResponseModel.DataBean groupData;

    /*Test Details views*/
    private LinearLayout[] questionArr;
    private HorizontalScrollView hsv_form_header;
    private ArrayList<String> allGrtImages = new ArrayList<>();

    /*Recommended views*/
    private LinearLayout ll_members_containor;
    private TextView tv_save;

    /*Result Views*/
    private ImageView iv_b_m_signature, iv_c_l_signature;
    private RadioGroup rg_group_passed;
    private EditText et_cite_reason, et_bm_remarks, et_bm_name, et_center_leader;
    private TextInputLayout til_cite_reason;
    int user_id;
    private QuestionListAdapter questionAdapter;
    private RecyclerView rv_test_questions;
    private ArrayList<AddGrtRequestModel.QuestionsBean> questionList;
    ArrayList<MemberListResponseModel.DataBean> membersList = new ArrayList<>();


    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_add_grt;
    }

    @Override
    public void initializeComponent() {
        user_id = getMyApplication().getUserPrefs().getLoggedInUser().getId();
        hsv_form_header = getView().findViewById(R.id.hsv_form_header);
        /*Header Tab Views*/
        tv_test_details = getView().findViewById(R.id.tv_test_details);
        tv_result = getView().findViewById(R.id.tv_result);
        tv_recommended = getView().findViewById(R.id.tv_recommended);

        /*Header Views*/
        ll_test_details = getView().findViewById(R.id.ll_test_details);
        ll_result = getView().findViewById(R.id.ll_result);
        ll_recommended = getView().findViewById(R.id.ll_recommended);

        /*click listeners to views*/
        tv_test_details.setOnClickListener(this);
        tv_result.setOnClickListener(this);
        tv_recommended.setOnClickListener(this);

        initTestDetailsViews();
        initRecommendedViews();
        initResultViews();

        tabArr = new TextView[]{tv_test_details, tv_result,
                tv_recommended};
        viewArr = new LinearLayout[]{ll_test_details, ll_result,
                ll_recommended};
        setTabVisibility(tv_test_details, ll_test_details);

        getGroupMembers();

       /* if (fiDataForEdit != null) {
            if (fiDataForEdit.isLocalLead() && !TextUtils.isEmpty(fiDataForEdit.getLocal_id()) &&
                    !fiDataForEdit.getLocal_id().equalsIgnoreCase("null")) {
                local_id = Integer.parseInt(fiDataForEdit.getLocal_id());
                Log.e(TAG, "local_lead: " + local_id);
            } else {
                server_fi_id = fiDataForEdit.getId();
            }
            setDataHandler.post(setDataRunnable);
        }*/
    }


    private void initRecommendedViews() {
        tv_save = getView().findViewById(R.id.tv_save);
        tv_save.setOnClickListener(this);
        ll_members_containor = getView().findViewById(R.id.ll_members_containor);
        addRecommendedMemberView();
    }

    private void initResultViews() {
        til_cite_reason = getView().findViewById(R.id.til_cite_reason);
        rg_group_passed = getView().findViewById(R.id.rg_group_passed);
        et_cite_reason = getView().findViewById(R.id.et_cite_reason);
        et_bm_remarks = getView().findViewById(R.id.et_bm_remarks);
        et_bm_name = getView().findViewById(R.id.et_bm_name);
        et_center_leader = getView().findViewById(R.id.et_center_leader);
        iv_b_m_signature = getView().findViewById(R.id.iv_b_m_signature);
        iv_c_l_signature = getView().findViewById(R.id.iv_c_l_signature);
        iv_c_l_signature.setOnClickListener(imageClickListener);
        iv_b_m_signature.setOnClickListener(imageClickListener);
        rg_group_passed.setOnCheckedChangeListener(onGroupPassedCheckListener);
    }

    RadioGroup.OnCheckedChangeListener onGroupPassedCheckListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            String groupPassValue = getRadioButtonValue(group);
            if (groupPassValue.equalsIgnoreCase(getString(R.string.recommended_for_gt)) ||
                    groupPassValue.equalsIgnoreCase(getString(R.string.no))) {
                til_cite_reason.setVisibility(View.VISIBLE);
            } else {
                til_cite_reason.setVisibility(View.GONE);
            }
        }
    };

    private ArrayList<AddGrtRequestModel.QuestionsBean> getQuestions(ArrayList<MemberListResponseModel.DataBean> membersList) {
        questionList = new ArrayList<>();
        String questionArr[] = getActivity().getResources().getStringArray(R.array.grt_test_question_arr);
        for (int i = 0; i < questionArr.length; i++) {
            AddGrtRequestModel.QuestionsBean questionsBean = new AddGrtRequestModel.QuestionsBean();
            questionsBean.setQuestion(questionArr[i]);
            questionsBean.setQuestion_id(questionArr[i].split(" ")[0]);
            questionsBean.setSelected_members(new ArrayList<String>());
            ArrayList<AddGrtRequestModel.QuestionsBean.AllMembers> mList = new ArrayList<>();
            for (int j = 0; j < membersList.size(); j++) {
                AddGrtRequestModel.QuestionsBean.AllMembers allMembers = new AddGrtRequestModel.QuestionsBean.AllMembers();
                allMembers.setId(membersList.get(j).getId());
                allMembers.setName(membersList.get(j).getName());
                allMembers.setSelected(false);
                mList.add(allMembers);
            }
            questionsBean.setAll_members(mList);
            questionList.add(questionsBean);
        }
        return questionList;
    }

    private void initTestDetailsViews() {
        rv_test_questions = getView().findViewById(R.id.rv_test_questions);
        rv_test_questions.setLayoutManager(getVerticalLinearLayoutManager());
    }

    private void setQuestionAdapter(ArrayList<MemberListResponseModel.DataBean> membersList) {
        ArrayList<AddGrtRequestModel.QuestionsBean.AllMembers> allMembersList = new ArrayList<>();
        /*for (int i = 0; i < membersList.size(); i++) {
            AddGrtRequestModel.QuestionsBean.AllMembers allMembers = new AddGrtRequestModel.QuestionsBean.AllMembers();
            allMembers.setId(membersList.get(i).getId());
            allMembers.setName(membersList.get(i).getName());
            allMembersList.add(allMembers);
        }*/
        ArrayList<AddGrtRequestModel.QuestionsBean> questionsBean = getQuestions(membersList);
        questionAdapter = new QuestionListAdapter(getActivity(), questionsBean, this);
        rv_test_questions.setAdapter(questionAdapter);

        new ItemClickSupport(rv_test_questions) {
            @Override
            public void onChildItemClicked(RecyclerView recyclerView, int parentPosition, int childPosition, View v) {
                switch (v.getId()) {
                 /*   case R.id.ll_member_parent:
                        setMembersAsSelected(recyclerView, parentPosition, childPosition);
                        break;
                    case R.id.cb_member:
                        setMembersAsSelected(recyclerView, parentPosition, childPosition);
                        break;*/
                }
            }
        }.setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                switch (v.getId()) {
                    case R.id.ll_test_question:
                        setMembersVisibility(position);
                        break;
                }
            }
        });
    }

    Handler setDataHandler = new Handler();

    Runnable setDataRunnable = new Runnable() {
        @Override
        public void run() {
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_test_details:
                manageSaveAndSubmitButton(false, tv_test_details, ll_test_details);
                break;
            case R.id.tv_result:
                manageSaveAndSubmitButton(false, tv_result, ll_result);
                break;
            case R.id.tv_recommended:
                manageSaveAndSubmitButton(false, tv_recommended, ll_recommended);
                break;
            case R.id.tv_save:
                AddGrtRequestModel grtRequestModel = getAllGrtData();
                addGRT(grtRequestModel);
              /*  Gson gson = new Gson();
                String json = gson.toJson(grtRequestModel);
                Log.e(TAG, "onClick: " + json);*/
                break;
        }
    }

    private AddGrtRequestModel getAllGrtData() {
        allGrtImages.clear();
        AddGrtRequestModel grtRequestModel = new AddGrtRequestModel();
        updateQuestionList();
        grtRequestModel.setUser_id(String.valueOf(user_id));
        grtRequestModel.setGroup_passed(getRadioButtonValue(rg_group_passed));
        grtRequestModel.setCite_reason(et_cite_reason.getText().toString());
        grtRequestModel.setBm_name(et_bm_name.getText().toString());
        grtRequestModel.setBm_remark(et_bm_remarks.getText().toString());
        grtRequestModel.setBm_signature(String.valueOf(iv_b_m_signature.getTag()));
        grtRequestModel.setCl_signature(String.valueOf(iv_c_l_signature.getTag()));
        grtRequestModel.setQuestions(questionList);
        grtRequestModel.setMembers(getMembersData());
        if (!TextUtils.isEmpty(String.valueOf(iv_b_m_signature.getTag()))) {
            if (String.valueOf(iv_b_m_signature.getTag()).contains("/")) {
                allGrtImages.add(String.valueOf(iv_b_m_signature.getTag()));
            }
        }
        if (!TextUtils.isEmpty(String.valueOf(iv_c_l_signature.getTag()))) {
            if (String.valueOf(iv_c_l_signature.getTag()).contains("/")) {
                allGrtImages.add(String.valueOf(iv_c_l_signature.getTag()));
            }
        }

        return grtRequestModel;
    }

    View.OnClickListener imageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openPicker(v);
        }
    };

    private void manageSaveAndSubmitButton(boolean navigateNext,
                                           TextView visibleTv, LinearLayout visibleLl) {
        int currentVisibleLayoutPosition = getVisibleLayout();
        if (navigateNext) {
            if (currentVisibleLayoutPosition != tabArr.length - 1) {
                hsv_form_header.scrollBy(120, 0);
                setTabVisibility(tabArr[currentVisibleLayoutPosition + 1], viewArr[currentVisibleLayoutPosition + 1]);
            }
        } else {
            setTabVisibility(visibleTv, visibleLl);
        }
    }

    private int getVisibleLayout() {
        int pos = 0;
        for (int i = 0; i < viewArr.length; i++) {
            if (viewArr[i].getVisibility() == View.VISIBLE) {
                pos = i;
                return pos;
            }
        }
        return pos;
    }

    public void setMembersVisibility(int pos) {
        if (membersList.size() == 0) {
            displayToast(getString(R.string.no_members_available));
            return;
        }
        View view = rv_test_questions.findViewHolderForAdapterPosition(pos).itemView;
        RecyclerView rv_group_members = view.findViewById(R.id.rv_group_members);
        if (rv_group_members.getVisibility() == View.VISIBLE) {
            rv_group_members.setVisibility(View.GONE);
        } else {
            rv_group_members.setVisibility(View.VISIBLE);
        }
    }

    public void setMembersAsSelected(RecyclerView recyclerView, int parentPosition, final int childPos) {
        View viewParent = recyclerView.findViewHolderForAdapterPosition(parentPosition).itemView;
        RecyclerView rv_members = viewParent.findViewById(R.id.rv_group_members);
        View view = rv_members.findViewHolderForAdapterPosition(childPos).itemView;
        final CheckBox cb_member = view.findViewById(R.id.cb_member);
        if (cb_member.isChecked()) {
            cb_member.setChecked(false);
        } else cb_member.setChecked(true);
        // updateMembers(parentPosition, questionList.get(parentPosition).getAll_members());

    }

    private void updateQuestionList() {
        for (int i = 0; i < questionList.size(); i++) {
            ArrayList<String> members = new ArrayList<>();
            for (int j = 0; j < questionList.get(i).getAll_members().size(); j++) {
                if (questionList.get(i).getAll_members().get(j).isSelected()) {
                    members.add(String.valueOf(questionList.get(i).getAll_members().get(j).getId()));
                }
            }
            questionList.get(i).setSelected_members(members);
        }
    }


    public void setTabVisibility(TextView textView, LinearLayout view) {
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
        textView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        view.setVisibility(View.VISIBLE);
        for (int i = 0; i < tabArr.length; i++) {
            if (textView.getId() != tabArr[i].getId()) {
                tabArr[i].setTextColor(getResources().getColor(R.color.colorWhite));
                tabArr[i].setBackgroundColor(getResources().getColor(R.color.semi_black));
            } else {
                try {
                    if (i == tabArr.length - 1) {
                        getDashBoardActivity().getToolBarHelper().tv_save_continue.setText(getString(R.string.submit));
                    } else {
                        getDashBoardActivity().getToolBarHelper().tv_save_continue.setText(getString(R.string.save_and_continue));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (view.getId() != viewArr[i].getId()) {
                viewArr[i].setVisibility(View.GONE);
            }
        }
    }

    private void openPicker(final View view) {
        ImagePickDialog picDialog = ImagePickDialog.getNewInstance(false);
        picDialog.setProfilePicDialogListner(new ImagePickDialog.ProfilePicDialogListner() {
            @Override
            public void onProfilePicSelected(FileInformation fileInformation) {
                Log.e(TAG, "onPicSelected: " + fileInformation.getFilePath());
                String imagePath = fileInformation.getBitmapPathForUpload(getActivity());
                Bitmap bitmap = fileInformation.getThumbBitmap(getActivity());
                ((ImageView) view).setImageBitmap(bitmap);
                view.setTag(imagePath);
            }

            @Override
            public void onProfilePicRemoved() {

            }
        });
        picDialog.show(getChildFm(), picDialog.getClass().getSimpleName());
    }


    private void addRecommendedMemberView() {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.layout_grt_members_view, null);
        ImageView iv_add_view = view.findViewById(R.id.iv_add_view);
        ImageView iv_remove_view = view.findViewById(R.id.iv_remove_view);
        ImageView iv_c_l_signature = view.findViewById(R.id.iv_c_l_signature);
        iv_remove_view.setTag(ll_members_containor.getChildCount());
        iv_c_l_signature.setTag(ll_members_containor.getChildCount());
        ll_members_containor.addView(view);
        iv_remove_view.setOnClickListener(onRecommendedDeleteListener);
        iv_add_view.setOnClickListener(onRecommendedAddListener);
        iv_c_l_signature.setOnClickListener(imageClickListener);
    }

    View.OnClickListener onRecommendedAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addRecommendedMemberView();
            onRefreshRecommended();
        }
    };

    View.OnClickListener onRecommendedDeleteListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ll_members_containor.removeViewAt(Integer.parseInt(v.getTag().toString()));
            onRefreshRecommended();
        }
    };

    private void onRefreshRecommended() {
        for (int i = 0; i < ll_members_containor.getChildCount(); i++) {
            View view = ll_members_containor.getChildAt(i);
            ImageView iv_remove_view = view.findViewById(R.id.iv_remove_view);
            ImageView iv_add_view = view.findViewById(R.id.iv_add_view);
            iv_remove_view.setTag(i);
            iv_remove_view.setVisibility(View.VISIBLE);
            if (i == 0) {
                iv_remove_view.setVisibility(View.GONE);
            }
            if (i == ll_members_containor.getChildCount() - 1) {
                iv_add_view.setVisibility(View.VISIBLE);
            } else {
                iv_add_view.setVisibility(View.GONE);
            }
        }
    }


    private ArrayList<AddGrtRequestModel.MembersBean> getMembersData() {
        ArrayList<AddGrtRequestModel.MembersBean> membersDetailsList = new ArrayList<>();
        if (ll_members_containor.getChildCount() > 0) {
            for (int i = 0; i < ll_members_containor.getChildCount(); i++) {
                AddGrtRequestModel.MembersBean membersBean = new AddGrtRequestModel.MembersBean();
                View view = ll_members_containor.getChildAt(i);
                EditText et_member_name = view.findViewById(R.id.et_member_name);
                EditText et_father_name = view.findViewById(R.id.et_father_name);
                EditText et_mother_name = view.findViewById(R.id.et_mother_name);
                EditText et_husbands_father_name = view.findViewById(R.id.et_husbands_father_name);
                ImageView iv_c_l_signature = view.findViewById(R.id.iv_c_l_signature);

                String memberName = et_member_name.getText().toString();
                String father = et_father_name.getText().toString();
                String mother = et_mother_name.getText().toString();
                String hus_father = et_husbands_father_name.getText().toString();

                membersBean.setMember_name(memberName);
                membersBean.setFather_name(father);
                membersBean.setMother_name(mother);
                membersBean.setHusband_father_name(hus_father);
                membersBean.setMember_signature(String.valueOf(iv_c_l_signature.getTag()));
                if (!TextUtils.isEmpty(String.valueOf(iv_c_l_signature.getTag()))) {
                    if (String.valueOf(iv_c_l_signature.getTag()).contains("/")) {
                        allGrtImages.add(String.valueOf(iv_c_l_signature.getTag()));
                    }
                }
                membersDetailsList.add(membersBean);
            }
        }
        return membersDetailsList;
    }

    // call to addGRT service
    private void addGRT(AddGrtRequestModel grtRequestModel) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
              getRestClient().callback(this).addGRT(grtRequestModel);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    private void uploadImage(String filePath, boolean showProgress) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            if (showProgress) {
                displayProgressBar(false, getActivity());
            }
            MultipartBody.Part part = RetrofitUtils.createFilePart(BaseArguments.ARG_FILE,
                    filePath, RetrofitUtils.MEDIA_TYPE_IMAGE_PNG);
            getRestClient().callback(this).uploadImage(part);
        }
    }

    // call to getGroupMembers service
    private void getGroupMembers() {
        if (groupData == null) {
            Log.e(TAG, "getGroupMembers: data null");
            return;
        }
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
            getRestClient().callback(this).getGroupMembers(String.valueOf(groupData.getId()));
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }


    @Override
    public void onSuccessResponse(int apiId, Object response) {
        if (apiId == ApiIds.ID_ADD_GRT) {
            BaseWebResponseModel responseModel = (BaseWebResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    if (allGrtImages.size() > 0) {
                        uploadImage(allGrtImages.get(0), false);
                    } else {
                        dismissProgressBar(getActivity());
                    }
                } else {
                    displayErrorDialog(responseModel.getMessage());
                }
            } else {
                dismissProgressBar(getActivity());
            }
        }else if (apiId == ApiIds.ID_UPLOAD_IMAGE) {
            UploadImageResponseModel responseModel = (UploadImageResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    allGrtImages.remove(0);
                    if (allGrtImages.size() > 0) {
                        uploadImage(allGrtImages.get(0), false);
                    } else {
                        dismissProgressBar(getActivity());
                        displayAlertDialog(getString(R.string.message), getString(R.string.grt_successfully_submitted), this);
                    }
                } else {
                    dismissProgressBar(getActivity());
                }
            } else {
                dismissProgressBar(getActivity());
            }
        } else if (apiId == ApiIds.ID_GET_MEMBERS) {
            dismissProgressBar(getActivity());
            MemberListResponseModel responseModel = (MemberListResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    membersList = responseModel.getData();
                    setQuestionAdapter(membersList);
                } else {
                    //  displayErrorDialog(responseModel.getMessage());
                }
            } else {
                dismissProgressBar(getActivity());
            }
        }
    }

    @Override
    public void onFailResponse(int apiId, String error) {
        dismissProgressBar(getActivity());
        displayErrorDialog(error);

    }

    @Override
    public void onDialogOkClick() {
        try {
            getDashBoardActivity().onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateMembers(int parentPosition, ArrayList<AddGrtRequestModel.QuestionsBean.AllMembers> list) {
        questionList.get(parentPosition).setAll_members(list);
    }
}
