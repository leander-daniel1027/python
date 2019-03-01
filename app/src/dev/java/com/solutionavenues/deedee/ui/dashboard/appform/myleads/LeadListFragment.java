package com.solutionavenues.deedee.ui.dashboard.appform.myleads;

import android.app.Dialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;
import com.solutionavenues.deedee.database.tables.LeadTable;
import com.solutionavenues.deedee.model.request.AddLeadRequestModel;
import com.solutionavenues.deedee.model.response.LeadListResponseModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.ui.dashboard.appform.ApplicationFormFragment;
import com.solutionavenues.deedee.ui.dashboard.appform.myleads.adapter.LocalLeadListAdapter;
import com.solutionavenues.deedee.ui.dashboard.appform.myleads.adapter.ServerLeadListAdapter;
import com.solutionavenues.deedee.ui.dashboard.fi.AddFiFragment;
import com.solutionavenues.deedee.ui.dashboard.telecaller.AddAppointmentFragment;

import java.util.ArrayList;

/**
 * Created by Azher on 18/6/18.
 */
public class LeadListFragment extends AppBaseFragment
        implements ApiHitListener {

    private TextView tv_server_leads, tv_local_leads, tv_no_item;
    private RecyclerView rv_server_leads, rv_local_leads;
    private ArrayList<AddLeadRequestModel> serverLeadList = new ArrayList<>();
    private ArrayList<AddLeadRequestModel> localLeadList = new ArrayList<>();
    ServerLeadListAdapter listAdapter;
    LocalLeadListAdapter localLeadAdapter;
    private SwipeRefreshLayout srl_server_leads, srl_local_leads;
    LinearLayout ll_server_leads, ll_local_leads;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_lead_list;
    }

    @Override
    public void initializeComponent() {
        findViews();
        setAdapter();
        getLeadsFromServer(true);
        tv_server_leads.performClick();
        initServerSwipeToRefresh();
        initLocalSwipeToRefresh();

    }

    private void findViews() {
        ll_server_leads = getView().findViewById(R.id.ll_server_leads);
        srl_server_leads = getView().findViewById(R.id.srl_server_leads);
        rv_server_leads = getView().findViewById(R.id.rv_server_leads);
        rv_server_leads.setLayoutManager(new LinearLayoutManager(getActivity()));

        ll_local_leads = getView().findViewById(R.id.ll_local_leads);
        srl_local_leads = getView().findViewById(R.id.srl_local_leads);
        rv_local_leads = getView().findViewById(R.id.rv_local_leads);
        rv_local_leads.setLayoutManager(new LinearLayoutManager(getActivity()));


        tv_no_item = getView().findViewById(R.id.tv_no_item);
        tv_server_leads = getView().findViewById(R.id.tv_server_leads);
        tv_local_leads = getView().findViewById(R.id.tv_local_leads);
        tv_server_leads.setOnClickListener(this);
        tv_local_leads.setOnClickListener(this);
    }

    private void setAdapter() {

        Log.e("Role Type", getMyApplication().getUserPrefs().getLoggedInUser().getRole_type());
        listAdapter = new ServerLeadListAdapter(getActivity(), serverLeadList, this, getMyApplication().getUserPrefs().getLoggedInUser().getRole_type(),
                getMyApplication().getAppPrefs().getCurrentLatitude(), getMyApplication().getAppPrefs().getCurrentLongitude(), getMyApplication().getUserPrefs().getLoggedInUser().getRole_id());
        rv_server_leads.setAdapter(listAdapter);

    }

    private void setLocalEnquiryAdapter() {
        LeadTable leadTable = new LeadTable();
        localLeadList = leadTable.getAllLeads();
        if (localLeadList.size() > 0) {
            localLeadAdapter = new LocalLeadListAdapter(getActivity(), localLeadList, this);
            rv_local_leads.setAdapter(localLeadAdapter);
            setDataEnvLocal();
        } else {
            setNoDataEnv();
        }
        if (srl_local_leads != null && srl_local_leads.isRefreshing()) {
            srl_local_leads.setRefreshing(false);
        }
    }

    private void initServerSwipeToRefresh() {

        srl_server_leads.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    getLeadsFromServer(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        srl_server_leads.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void initLocalSwipeToRefresh() {

        srl_local_leads.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    setLocalEnquiryAdapter();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        srl_local_leads.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    @Override
    public void onClick(View v) {
        String roletype = getMyApplication().getUserPrefs().getLoggedInUser().getRole_type();
        switch (v.getId()) {
            case R.id.tv_local_leads:
                setTabVisibility(tv_local_leads);
                setLocalEnquiryAdapter();
                break;

            case R.id.tv_server_leads:
                setTabVisibility(tv_server_leads);
                if (serverLeadList != null && serverLeadList.size() > 0) {
                    listAdapter.serverLeadList = serverLeadList;
                    listAdapter.notifyDataSetChanged();
                    setDataEnvServer();
                } else {
                    setNoDataEnv();
                }
                break;

            case R.id.ll_parent_server:
                if (!roletype.equalsIgnoreCase("fi")) {
                    int position_server = (int) v.getTag();
                    AddLeadRequestModel leadRequestModel = serverLeadList.get(position_server);
                    leadRequestModel.setLocalLead(false);
                    addAppFormFragment(leadRequestModel);
                }


                /*if (serverLeadList.get(position_server).getStatus().equalsIgnoreCase(Constants.ENQUIRY_STATUS_PENDING)) {
                    addEditEnquiryFragment(Constants.SERVER_ENQUIRY, enquiryList.get(position_server), null);
                }else if(enquiryList.get(position_server).getStatus().equalsIgnoreCase(Constants.ENQUIRY_STATUS_APPROVED)){
                    addAppFormFragment(enquiryList.get(position_server));
                }*/

                break;
            case R.id.ll_parent_local:
                int position_local = (int) v.getTag();
                AddLeadRequestModel leadRequestModel1 = localLeadList.get(position_local);
                leadRequestModel1.setLocalLead(true);
                addAppFormFragment(leadRequestModel1);
                break;
            case R.id.tv_telecalling:
                int position_tc = (int) v.getTag();
                AddLeadRequestModel leadRequestModel2 = serverLeadList.get(position_tc);
                addTelecallingFragment(leadRequestModel2);
                break;
            case R.id.tv_fi:
                int position_fi = (int) v.getTag();
                AddLeadRequestModel leadRequestModel3 = serverLeadList.get(position_fi);
                addFiFormFragment(leadRequestModel3);
                break;


        }

    }


    private void addAppFormFragment(final AddLeadRequestModel dataBean) {
        final Dialog dialog = displayProgressBar(false, getActivity());
        tv_server_leads.postDelayed(new Runnable() {
            @Override
            public void run() {
                ApplicationFormFragment fragment = new ApplicationFormFragment();
                int enterAnimation = R.anim.translate_right_to_left_anim;
                int exitAnimation = 0;
                int enterAnimationBackStack = 0;
                int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
                fragment.setLeadDataForEdit(dataBean);
                fragment.setLoadViewDialog(dialog);
                try {
                    getDashBoardActivity().changeFragment(fragment, true, false, 0,
                            enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 500);

    }

    private void addTelecallingFragment(AddLeadRequestModel dataBean) {
        AddAppointmentFragment fragment = new AddAppointmentFragment();
        int enterAnimation = R.anim.translate_right_to_left_anim;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
        fragment.setLead_id(dataBean.getLead_id());
        try {
            getDashBoardActivity().changeFragment(fragment, true, false, 0,
                    enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addFiFormFragment(final AddLeadRequestModel leadRequestModel3) {
        final Dialog dialog = displayProgressBar(false, getActivity());
        tv_server_leads.postDelayed(new Runnable() {
            @Override
            public void run() {
                AddFiFragment fragment = new AddFiFragment();
                int enterAnimation = R.anim.translate_right_to_left_anim;
                int exitAnimation = 0;
                int enterAnimationBackStack = 0;
                int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
                fragment.setLoadViewDialog(dialog);
                fragment.setAddLeadRequestModel(leadRequestModel3);
                try {
                    getDashBoardActivity().changeFragment(fragment, true, false, 0,
                            enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, 500);
    }


    private void setTabVisibility(TextView textView) {
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
        textView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        if (textView.getId() == tv_local_leads.getId()) {
            tv_server_leads.setTextColor(getResources().getColor(R.color.colorWhite));
            tv_server_leads.setBackgroundColor(getResources().getColor(R.color.semi_black));
        } else if (textView.getId() == tv_server_leads.getId()) {
            tv_local_leads.setTextColor(getResources().getColor(R.color.colorWhite));
            tv_local_leads.setBackgroundColor(getResources().getColor(R.color.semi_black));
        }

    }


    // call to getLeadsFromServer service
    private void getLeadsFromServer(boolean progressVisibility) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            if (progressVisibility) {
                displayProgressBar(false, getActivity());
            }
            String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
            getRestClient().callback(this).getLeads(user_id);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    private void setNoDataEnv() {
        if (tv_server_leads.getCurrentTextColor() == getResources().getColor(R.color.colorAccent)) {
            srl_server_leads.setVisibility(View.VISIBLE);
            srl_local_leads.setVisibility(View.GONE);
        } else {
            srl_server_leads.setVisibility(View.GONE);
            srl_local_leads.setVisibility(View.VISIBLE);
        }
        rv_server_leads.setVisibility(View.GONE);
        rv_local_leads.setVisibility(View.GONE);
        tv_no_item.setVisibility(View.VISIBLE);
    }

    private void setDataEnvServer() {
        tv_no_item.setVisibility(View.GONE);
        rv_local_leads.setVisibility(View.GONE);
        rv_server_leads.setVisibility(View.VISIBLE);
        srl_local_leads.setVisibility(View.GONE);
        srl_server_leads.setVisibility(View.VISIBLE);
    }


    private void setDataEnvLocal() {
        tv_no_item.setVisibility(View.GONE);
        rv_server_leads.setVisibility(View.GONE);
        rv_local_leads.setVisibility(View.VISIBLE);
        srl_local_leads.setVisibility(View.VISIBLE);
        srl_server_leads.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessResponse(int apiId, Object response) {
        if (srl_server_leads != null && srl_server_leads.isRefreshing()) {
            srl_server_leads.setRefreshing(false);
        } else {
            dismissProgressBar(getActivity());
        }
        if (apiId == ApiIds.ID_GET_LEADS) {
            LeadListResponseModel responseModel = (LeadListResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    // displayToast(responseModel.getMessage());
                    if (responseModel.getData().size() == 0) {
                        setNoDataEnv();
                    } else {
                        listAdapter.serverLeadList = responseModel.getData();
                        serverLeadList = responseModel.getData();
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
