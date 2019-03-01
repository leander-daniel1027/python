package com.solutionavenues.deedee.ui.dashboard.enquiry;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;
import com.solutionavenues.deedee.database.tables.EnquiryTable;
import com.solutionavenues.deedee.model.request.AddEditEnquiryRequestModel;
import com.solutionavenues.deedee.model.response.EnquiryListResponseModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.ui.dashboard.appform.ApplicationFormFragment;
import com.solutionavenues.deedee.ui.dashboard.enquiry.adapter.EnquiryListAdapter;
import com.solutionavenues.deedee.ui.dashboard.enquiry.adapter.EnquiryLocalListAdapter;
import com.solutionavenues.deedee.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Azher on 18/6/18.
 */
public class EnquiryListFragment extends AppBaseFragment
        implements ApiHitListener {

    private TextView tv_server_enquiries, tv_local_enquiries, tv_no_item;
    private RecyclerView rv_server_enquiries,rv_local_enquiries;
    private ArrayList<EnquiryListResponseModel.DataBean> enquiryList = new ArrayList<>();
    private ArrayList<AddEditEnquiryRequestModel> localEnquiryList = new ArrayList<>();
    EnquiryListAdapter listAdapter;
    EnquiryLocalListAdapter localEnquiryAdapter;
    List<com.solutionavenues.deedee.model.response.UserAreaList> UserAreaList ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null ){
            if( getArguments().containsKey("array")) {
                UserAreaList = ( List<com.solutionavenues.deedee.model.response.UserAreaList>)getArguments().getSerializable("array");
            }
        }
    }
    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_enquiry_list;
    }

    @Override
    public void initializeComponent() {
        findViews();
        setAdapter();
        getEnquiriesFromServer();
        tv_server_enquiries.performClick();

    }

    private void findViews() {
        rv_server_enquiries = getView().findViewById(R.id.rv_server_enquiries);
        rv_server_enquiries.setLayoutManager(new LinearLayoutManager(getActivity()));

        rv_local_enquiries = getView().findViewById(R.id.rv_local_enquiries);
        rv_local_enquiries.setLayoutManager(new LinearLayoutManager(getActivity()));


        tv_no_item = getView().findViewById(R.id.tv_no_item);
        tv_server_enquiries = getView().findViewById(R.id.tv_server_enquiries);
        tv_local_enquiries = getView().findViewById(R.id.tv_local_enquiries);
        tv_server_enquiries.setOnClickListener(this);
        tv_local_enquiries.setOnClickListener(this);
    }

    private void setAdapter() {
        listAdapter = new EnquiryListAdapter(getActivity(), enquiryList, this);
        rv_server_enquiries.setAdapter(listAdapter);

    }

    private void setLocalEnquiryAdapter() {
        EnquiryTable enquiryTable = new EnquiryTable();
        localEnquiryList = enquiryTable.getAllEnquiries();
        if (localEnquiryList.size() > 0) {
            localEnquiryAdapter = new EnquiryLocalListAdapter(getActivity(), localEnquiryList, this);
            rv_local_enquiries.setAdapter(localEnquiryAdapter);
            setDataEnvLocal();
        } else {
            setNoDataEnv();
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_local_enquiries:
                setTabVisibility(tv_local_enquiries);
                setLocalEnquiryAdapter();
                break;

            case R.id.tv_server_enquiries:
                setTabVisibility(tv_server_enquiries);
                if (enquiryList != null && enquiryList.size() > 0) {
                    listAdapter.enquiryList = enquiryList;
                    listAdapter.notifyDataSetChanged();
                    setDataEnvServer();
                }else{
                    setNoDataEnv();
                }
                break;

            case R.id.ll_parent:
                int position_server = (int) v.getTag();
                if (enquiryList.get(position_server).getStatus().equalsIgnoreCase(Constants.ENQUIRY_STATUS_PENDING)) {
                    addEditEnquiryFragment(Constants.SERVER_ENQUIRY, enquiryList.get(position_server), null);
                }else if(enquiryList.get(position_server).getStatus().equalsIgnoreCase(Constants.ENQUIRY_STATUS_APPROVED)){
                    addAppFormFragment(enquiryList.get(position_server));
                }

                break;
            case R.id.ll_parent_local:
                int position_local = (int) v.getTag();
                addEditEnquiryFragment(Constants.LOCAL_ENQUIRY, null, localEnquiryList.get(position_local));
                break;


        }

    }

    private void addEditEnquiryFragment(String enquiryType,
                                        EnquiryListResponseModel.DataBean dataBean,
                                        AddEditEnquiryRequestModel enquiryRequestModel) {
        AddEnquiryFragment fragment = new AddEnquiryFragment();
        int enterAnimation = R.anim.translate_right_to_left_anim;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
        try {
            if (enquiryType.equalsIgnoreCase(Constants.SERVER_ENQUIRY)) {
                fragment.setEnquiryType(Constants.SERVER_ENQUIRY);
                fragment.setEditEnquiryModel(dataBean);
                fragment.setRequestModel(enquiryRequestModel);
            } else if (enquiryType.equalsIgnoreCase(Constants.LOCAL_ENQUIRY)) {
                fragment.setEnquiryType(Constants.LOCAL_ENQUIRY);
                fragment.setRequestModel(enquiryRequestModel);
                fragment.setEditEnquiryModel(dataBean);
            }
            getDashBoardActivity().changeFragment(fragment, true, false, 0,
                    enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void addAppFormFragment(EnquiryListResponseModel.DataBean dataBean) {
        ApplicationFormFragment fragment = new ApplicationFormFragment();
        int enterAnimation = R.anim.translate_right_to_left_anim;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
        fragment.setEnquiryData(dataBean);
        try {
            getDashBoardActivity().changeFragment(fragment, true, false, 0,
                    enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setTabVisibility(TextView textView) {
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
        textView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        if (textView.getId() == tv_local_enquiries.getId()) {
            tv_server_enquiries.setTextColor(getResources().getColor(R.color.colorWhite));
            tv_server_enquiries.setBackgroundColor(getResources().getColor(R.color.semi_black));
        } else if (textView.getId() == tv_server_enquiries.getId()) {
            tv_local_enquiries.setTextColor(getResources().getColor(R.color.colorWhite));
            tv_local_enquiries.setBackgroundColor(getResources().getColor(R.color.semi_black));
        }

    }


    // call to getEnquiriesFromServer service
    private void getEnquiriesFromServer() {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            displayProgressBar(false, getActivity());
            String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
            getRestClient().callback(this).getEnquiryList(user_id);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    private void setNoDataEnv() {
        tv_no_item.setVisibility(View.VISIBLE);
        rv_server_enquiries.setVisibility(View.GONE);
        rv_local_enquiries.setVisibility(View.GONE);
    }

    private void setDataEnvServer() {
        tv_no_item.setVisibility(View.GONE);
        rv_server_enquiries.setVisibility(View.VISIBLE);
        rv_local_enquiries.setVisibility(View.GONE);
    }


    private void setDataEnvLocal() {
        tv_no_item.setVisibility(View.GONE);
        rv_server_enquiries.setVisibility(View.GONE);
        rv_local_enquiries.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessResponse(int apiId, Object response) {
        dismissProgressBar(getActivity());
        if (apiId == ApiIds.ID_ENQUIRY_LIST) {
            EnquiryListResponseModel responseModel = (EnquiryListResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    // displayToast(responseModel.getMessage());
                    if (responseModel.getData().size() == 0) {
                        setNoDataEnv();
                    } else {
                        listAdapter.enquiryList = responseModel.getData();
                        enquiryList = responseModel.getData();
                        listAdapter.notifyDataSetChanged();
                        setDataEnvServer();
                    }
                } else {
                    setNoDataEnv();
                    displayErrorDialog(responseModel.getMessage());
                }
            }
        }
    }


    @Override
    public void onFailResponse(int apiId, String error) {
        setNoDataEnv();
        dismissProgressBar(getActivity());
        displayErrorDialog(error);

    }


}
