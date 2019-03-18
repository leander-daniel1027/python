package com.solutionavenues.deedee.ui.dashboard.cgt;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;
import com.solutionavenues.deedee.imagePicker.FileInformation;
import com.solutionavenues.deedee.imagePicker.ImagePickDialog;
import com.solutionavenues.deedee.model.request.AddCGTRequestModel;
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
 * Created by Azher on 20/6/18.
 */
public class AddCGTFormFragment extends AppBaseFragment implements ApiHitListener, MembersChildAdapter.UpdateMembersList {
    private static final String TAG = "AddCGTFormFragment";
    private TextView tv_submit;
    private RadioGroup rg_day_type;
    private ImageView iv_group_photo;
    private String filePathGroupPhoto;

    ArrayList<MemberListResponseModel.DataBean> membersList = new ArrayList<>();
    private ArrayList<AddGrtRequestModel.QuestionsBean> questionList = new ArrayList<>();
    private RecyclerView rv_test_questions;
    private QuestionListAdapter questionAdapter;

    public void setGroupData(GroupListResponseModel.DataBean groupData) {
        this.groupData = groupData;
    }

    public GroupListResponseModel.DataBean groupData;


    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_add_cgt;
    }

    @Override
    public void initializeComponent() {
        findViews();
        getGroupMembers();
    }

    private void findViews() {
        rv_test_questions = getView().findViewById(R.id.rv_test_questions);
        rv_test_questions.setLayoutManager(getVerticalLinearLayoutManager());
        rg_day_type = getView().findViewById(R.id.rg_day_type);
        iv_group_photo = getView().findViewById(R.id.iv_group_photo);
        iv_group_photo.setOnClickListener(this);
        tv_submit = getView().findViewById(R.id.tv_submit);
        tv_submit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:
                if (!TextUtils.isEmpty(filePathGroupPhoto)) {
                    addCGT();
                } else {
                    displayToast(getString(R.string.please_select_group_photo));
                }

                break;
            case R.id.iv_group_photo:
                openPicker();
                break;

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
            Log.e(TAG + "getId", String.valueOf(groupData.getId()));
            getRestClient().callback(this).getGroupMembers(String.valueOf(groupData.getId()));
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }


    // call to getBranchList service
    private void getBranchList() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
            getRestClient().callback(this).getBranchList();
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    // call to addCGT service
    private void addCGT() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
            String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
            AddCGTRequestModel addCGTRequestModel = new AddCGTRequestModel();
            updateQuestionList();
            addCGTRequestModel.setUser_id(user_id);
            addCGTRequestModel.setGroup_id(groupData.getId());
            addCGTRequestModel.setLead_id("");
            addCGTRequestModel.setQuestions(questionList);
            addCGTRequestModel.setDay1(getRadioButtonStateWithValue(rg_day_type, getString(R.string.day1)));
            addCGTRequestModel.setDay2(getRadioButtonStateWithValue(rg_day_type, getString(R.string.day2)));
            addCGTRequestModel.setFile(filePathGroupPhoto);
            Gson gson = new Gson();
            String json = gson.toJson(addCGTRequestModel);
            Log.d("api request", json);
            getRestClient().callback(this).addCGT(addCGTRequestModel);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }


    private void uploadImage(String filePath, String type) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            MultipartBody.Part part = RetrofitUtils.createFilePart(BaseArguments.ARG_FILE,
                    filePath, RetrofitUtils.MEDIA_TYPE_IMAGE_PNG);
            getRestClient().callback(this).uploadImage(part, type);
        }
    }

    private void openPicker() {
        ImagePickDialog picDialog = ImagePickDialog.getNewInstance(false);
        picDialog.setProfilePicDialogListner(new ImagePickDialog.ProfilePicDialogListner() {
            @Override
            public void onProfilePicSelected(FileInformation fileInformation) {
                Log.e(TAG, "onProfilePicSelected: " + fileInformation.getFilePath());
                filePathGroupPhoto = fileInformation.getBitmapPathForUpload(getActivity());
                iv_group_photo.setImageBitmap(fileInformation.getThumbBitmap(getActivity()));

            }

            @Override
            public void onProfilePicRemoved() {
                iv_group_photo.setImageResource(R.mipmap.default_image);
            }
        });
        picDialog.show(getChildFm(), picDialog.getClass().getSimpleName());
    }


    @Override
    public void onSuccessResponse(int apiId, Object response) {
        if (apiId == ApiIds.ID_ADD_CGT) {
            BaseWebResponseModel responseModel = (BaseWebResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    uploadImage(filePathGroupPhoto, "");
                } else {
                    dismissProgressBar(getActivity());
                }
            } else {
                dismissProgressBar(getActivity());
            }
        } else if (apiId == ApiIds.ID_UPLOAD_IMAGE) {
            dismissProgressBar(getActivity());
            UploadImageResponseModel responseModel = (UploadImageResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    try {
                        getDashBoardActivity().onBackPressed();
                    } catch (Exception e) {
                        e.printStackTrace();
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
                    displayErrorDialog(responseModel.getMessage());
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

    private void setQuestionAdapter(ArrayList<MemberListResponseModel.DataBean> membersList) {
        ArrayList<AddGrtRequestModel.QuestionsBean.AllMembers> allMembersList = new ArrayList<>();

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

    private ArrayList<AddGrtRequestModel.QuestionsBean> getQuestions(ArrayList<MemberListResponseModel.DataBean> membersList) {
        questionList = new ArrayList<>();
        String questionArr[] = getActivity().getResources().getStringArray(R.array.cgt_test_question_arr);
        for (String quetion : questionArr) {
            AddGrtRequestModel.QuestionsBean questionsBean = new AddGrtRequestModel.QuestionsBean();
            questionsBean.setQuestion(quetion);
            questionsBean.setQuestion_id(quetion.split(" ")[0]);
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

    public void setMembersVisibility(int pos) {
        if (membersList.size() == 0) {
            displayToast(getString(R.string.no_members_available));
            return;
        }
        View view = rv_test_questions.findViewHolderForAdapterPosition(pos).itemView;
        RecyclerView rv_group_members = view.findViewById(R.id.rv_group_members);
        CheckBox cb_select_all = view.findViewById(R.id.cb_select_all);
        if (rv_group_members.getVisibility() == View.VISIBLE) {
            rv_group_members.setVisibility(View.GONE);
            cb_select_all.setVisibility(View.GONE);
        } else {
            rv_group_members.setVisibility(View.VISIBLE);
            cb_select_all.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateMembers(int parentPosition, ArrayList<AddGrtRequestModel.QuestionsBean.AllMembers> list) {
        questionList.get(parentPosition).setAll_members(list);
    }

    private AddGrtRequestModel getAllGrtData() {
        AddGrtRequestModel grtRequestModel = new AddGrtRequestModel();
        updateQuestionList();
        return grtRequestModel;
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
}
