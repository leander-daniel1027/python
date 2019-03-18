package com.solutionavenues.deedee.ui.dashboard.appform.myleads;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.customviews.CustomEditText;
import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.MyApplication;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;
import com.solutionavenues.deedee.database.tables.LeadTable;
import com.solutionavenues.deedee.model.request.AddLeadRequestModel;
import com.solutionavenues.deedee.model.response.LeadListResponseModel;
import com.solutionavenues.deedee.model.response.UserArea;
import com.solutionavenues.deedee.model.response.UserAreaList;
import com.solutionavenues.deedee.model.response.UserByRole;
import com.solutionavenues.deedee.model.response.UserListByRole;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.ui.dashboard.appform.ApplicationFormFragment;
import com.solutionavenues.deedee.ui.dashboard.appform.myleads.adapter.LocalLeadListAdapter;
import com.solutionavenues.deedee.ui.dashboard.appform.myleads.adapter.ServerLeadListAdapter;
import com.solutionavenues.deedee.ui.dashboard.fi.AddFiFragment;
import com.solutionavenues.deedee.ui.dashboard.telecaller.AddAppointmentFragment;
import com.solutionavenues.deedee.ui.dialog.AlertDialogWithSearch;
import com.solutionavenues.deedee.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Azher on 18/6/18.
 */
public class LeadListFragment extends AppBaseFragment
        implements ApiHitListener {

    private TextView tv_server_leads, tv_local_leads, tv_no_item;
    private RecyclerView rv_server_leads, rv_local_leads;
    private ArrayList<AddLeadRequestModel> serverLeadList = new ArrayList<>();
    private ArrayList<AddLeadRequestModel> serverOriginalLeadList = new ArrayList<>();
    private ArrayList<AddLeadRequestModel> localLeadList = new ArrayList<>();
    private ArrayList<AddLeadRequestModel> localOriginalLeadList = new ArrayList<>();
    ServerLeadListAdapter listAdapter;
    LocalLeadListAdapter localLeadAdapter;
    private SwipeRefreshLayout srl_server_leads, srl_local_leads;
    LinearLayout ll_server_leads, ll_local_leads;
    private CustomEditText et_lead_role_filter, et_lead__area_filter, et_group;

    List<UserAreaList> UserAreaList = new ArrayList<>();
    private ArrayList<String> userArealist = new ArrayList<>();

    List<UserListByRole> UserByRoleList = new ArrayList<>();
    private ArrayList<String> userByRoleList = new ArrayList<>();

    List<UserListByRole> UserGroupList = new ArrayList<>();
    private ArrayList<String> userGroupList = new ArrayList<>();

    private String filteredRoleID;
    private String filteredArea;
    private String filteredGroupID;
    private View ll_filter;
    private TextView btnResetFilter;

    DialogInterface.OnClickListener onClickedUserByRolelistListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            String name = userByRoleList.toArray(new String[userByRoleList.size()])[item];
            et_lead_role_filter.setText(name);
            filteredRoleID = String.valueOf(UserByRoleList.get(item - 1).getId());

            getMyApplication().getAppPrefs().saveFilterLo(name);
            getMyApplication().getAppPrefs().saveFilterLoID(filteredRoleID);
            serverLeadList.clear();
            serverLeadList.addAll(getFilteredArr(filteredArea, filteredRoleID, filteredGroupID));
            if (serverLeadList != null && serverLeadList.size() > 0) {
                listAdapter.serverLeadList = serverLeadList;
                listAdapter.notifyDataSetChanged();
                setDataEnvServer();
            } else {
                setNoDataEnv();
            }
        }
    };

    Utils.ListItemWithDataClickListener onClickedUserArealistListner = new Utils.ListItemWithDataClickListener() {
        @Override
        public void onItemClick(int item, String name) {
            et_lead__area_filter.setText(name);
            filteredArea = name;
            getMyApplication().getAppPrefs().saveFilterArea(filteredArea);
            serverLeadList.clear();
            serverLeadList.addAll(getFilteredArr(filteredArea, filteredRoleID, filteredGroupID));
            if (serverLeadList != null && serverLeadList.size() > 0) {
                listAdapter.serverLeadList = serverLeadList;
                listAdapter.notifyDataSetChanged();
                setDataEnvServer();
            } else {
                setNoDataEnv();
            }

        }
    };

    Utils.ListItemWithDataClickListener onClickedUsergrouplistListner = new Utils.ListItemWithDataClickListener() {
        @Override
        public void onItemClick(int item, String name) {
            et_group.setText(name);
            for (int i = 0; i < UserGroupList.size(); i++) {
                if (name.equals(UserGroupList.get(i).getName())) {
                    filteredGroupID = String.valueOf(UserGroupList.get(i).getId());
                }
            }

            getMyApplication().getAppPrefs().saveFilterGroup(name);
            getMyApplication().getAppPrefs().saveFilterGroupID(filteredGroupID);
            serverLeadList.clear();
            serverLeadList.addAll(getFilteredArr(filteredArea, filteredRoleID, filteredGroupID));
            if (serverLeadList != null && serverLeadList.size() > 0) {
                listAdapter.serverLeadList = serverLeadList;
                listAdapter.notifyDataSetChanged();
                setDataEnvServer();
            } else {
                setNoDataEnv();
            }

        }
    };

    private ArrayList<AddLeadRequestModel> getFilteredArr(String area, String id, String groupID) {
        ArrayList<AddLeadRequestModel> newServerLeadList = new ArrayList<>();

        if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(area) && !TextUtils.isEmpty(groupID)) {
            for (int i = 0; i < serverOriginalLeadList.size(); i++) {
                if (id.equals(serverOriginalLeadList.get(i).getLoanPersonelInfo().getUser_id()) &&
                        area.equals(serverOriginalLeadList.get(i).getAddressInfo().getArea()) &&
                        groupID.equals(serverOriginalLeadList.get(i).getLoanPersonelInfo().getGroup_id())) {
                    newServerLeadList.add(serverOriginalLeadList.get(i));
                }
            }
        } else if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(area)) {
            for (int i = 0; i < serverOriginalLeadList.size(); i++) {
                if (id.equals(serverOriginalLeadList.get(i).getLoanPersonelInfo().getUser_id()) &&
                        area.equals(serverOriginalLeadList.get(i).getAddressInfo().getArea())) {
                    newServerLeadList.add(serverOriginalLeadList.get(i));
                }
            }
        } else if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(groupID)) {
            for (int i = 0; i < serverOriginalLeadList.size(); i++) {
                if (id.equals(serverOriginalLeadList.get(i).getLoanPersonelInfo().getUser_id()) &&
                        groupID.equals(serverOriginalLeadList.get(i).getLoanPersonelInfo().getGroup_id())) {
                    newServerLeadList.add(serverOriginalLeadList.get(i));
                }
            }
        } else if (!TextUtils.isEmpty(groupID) && !TextUtils.isEmpty(area)) {
            for (int i = 0; i < serverOriginalLeadList.size(); i++) {
                if (groupID.equals(serverOriginalLeadList.get(i).getLoanPersonelInfo().getGroup_id()) &&
                        area.equals(serverOriginalLeadList.get(i).getAddressInfo().getArea())) {
                    newServerLeadList.add(serverOriginalLeadList.get(i));
                }
            }
        } else if (!TextUtils.isEmpty(id)) {
            for (int i = 0; i < serverOriginalLeadList.size(); i++) {
                if (id.equals(serverOriginalLeadList.get(i).getLoanPersonelInfo().getUser_id())) {
                    newServerLeadList.add(serverOriginalLeadList.get(i));
                }
            }
        } else if (!TextUtils.isEmpty(groupID)) {
            for (int i = 0; i < serverOriginalLeadList.size(); i++) {
                if (groupID.equals(serverOriginalLeadList.get(i).getLoanPersonelInfo().getGroup_id())) {
                    newServerLeadList.add(serverOriginalLeadList.get(i));
                }
            }
        } else if (!TextUtils.isEmpty(area)) {
            for (int i = 0; i < serverOriginalLeadList.size(); i++) {
                if (area.equals(serverOriginalLeadList.get(i).getAddressInfo().getArea())) {
                    newServerLeadList.add(serverOriginalLeadList.get(i));
                }
            }
        } else {
            newServerLeadList.addAll(serverOriginalLeadList);
        }

        return newServerLeadList;
    }

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_lead_list;
    }

    @Override
    public void initializeComponent() {
        findViews();
        setAdapter();
        getLeadsFromServer(true);
        getUserAreaList(false);
        getUserListByRole(false);
        getUserGroupList(false);
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
        ll_filter = getView().findViewById(R.id.ll_filter);

        tv_no_item = getView().findViewById(R.id.tv_no_item);
        tv_server_leads = getView().findViewById(R.id.tv_server_leads);
        tv_local_leads = getView().findViewById(R.id.tv_local_leads);
        et_lead_role_filter = getView().findViewById(R.id.et_lead_role);
        et_lead__area_filter = getView().findViewById(R.id.et_lead_area);
        et_group = getView().findViewById(R.id.et_group);
        btnResetFilter = getView().findViewById(R.id.btn_reset);
        tv_server_leads.setOnClickListener(this);
        et_lead_role_filter.setOnClickListener(this);
        et_lead__area_filter.setOnClickListener(this);
        et_group.setOnClickListener(this);
        btnResetFilter.setOnClickListener(this);
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
        localOriginalLeadList.addAll(leadTable.getAllLeads());
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
            case R.id.et_lead_area:

                if (UserAreaList != null && UserAreaList.size() > 0) {
                    openUserAreaListDialog();
                } else {
                    displayToast(getString(R.string.no_option_available));
                }
                break;
            case R.id.et_lead_role:
                if (UserByRoleList != null && UserByRoleList.size() > 0) {
                    openUserListByRoleDialog();
                } else {
                    displayToast(getString(R.string.no_option_available));
                }
                break;
            case R.id.et_group:
                if (UserGroupList != null && UserGroupList.size() > 0) {
                    openUserGroupListDialog();
                } else {
                    displayToast(getString(R.string.no_option_available));
                }
                break;
            case R.id.btn_reset:
                et_lead__area_filter.setText(null);
                et_lead_role_filter.setText(null);
                et_group.setText(null);
                filteredArea = null;
                filteredRoleID = null;
                filteredGroupID = null;

                getMyApplication().getAppPrefs().saveFilterGroup(null);
                getMyApplication().getAppPrefs().saveFilterGroupID(filteredGroupID);
                getMyApplication().getAppPrefs().saveFilterArea(null);
                getMyApplication().getAppPrefs().saveFilterLo(null);
                getMyApplication().getAppPrefs().saveFilterLoID(filteredRoleID);
                serverLeadList.clear();
                serverLeadList.addAll(getFilteredArr(filteredArea, filteredRoleID, filteredGroupID));
                if (serverLeadList != null && serverLeadList.size() > 0) {
                    listAdapter.serverLeadList = serverLeadList;
                    listAdapter.notifyDataSetChanged();
                    setDataEnvServer();
                } else {
                    setNoDataEnv();
                }
                break;
            case R.id.tv_local_leads:
                ll_filter.setVisibility(View.GONE);
                setTabVisibility(tv_local_leads);
                setLocalEnquiryAdapter();
                break;

            case R.id.tv_server_leads:
                ll_filter.setVisibility(View.VISIBLE);
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

    private void getUserAreaList(boolean progressVisibility) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            if (progressVisibility) {
                displayProgressBar(false, getActivity());
            }
            String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
            int roleId = getMyApplication().getUserPrefs().getLoggedInUser().getRole_id();
            if (roleId == 31) {
                getRestClient().callback(this).getUserAreaListFI(user_id);
            } else {
                getRestClient().callback(this).getUserAreaList(user_id);
            }

        } else {
//            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    private void getUserListByRole(boolean progressVisibility) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            if (progressVisibility) {
                displayProgressBar(false, getActivity());
            }
            String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
            getRestClient().callback(this).getUserListByRole(user_id);
        } else {
//            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    private void getUserGroupList(boolean progressVisibility) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            if (progressVisibility) {
                displayProgressBar(false, getActivity());
            }
            String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
            getRestClient().callback(this).getUserGroupList(user_id);
        } else {
//            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    private void openUserAreaListDialog() {
        Utils.hideKeyboard(getActivity());
        AlertDialogWithSearch alertDialogWithSearch = new AlertDialogWithSearch(getActivity(),
                userArealist.toArray(new String[userArealist.size()]), onClickedUserArealistListner);
        alertDialogWithSearch.show();
        /*Utils.showAlertDialog(getActivity(),
                "",
                userArealist.toArray(new String[userArealist.size()]),
                onClickedUserArealistListner);*/

    }

    private void openUserGroupListDialog() {
        Utils.hideKeyboard(getActivity());
        AlertDialogWithSearch alertDialogWithSearch = new AlertDialogWithSearch(getActivity(),
                userGroupList.toArray(new String[userGroupList.size()]), onClickedUsergrouplistListner);
        alertDialogWithSearch.show();
        /*Utils.showAlertDialog(getActivity(),
                "",
                userArealist.toArray(new String[userArealist.size()]),
                onClickedUserArealistListner);*/

    }


    private void openUserListByRoleDialog() {
        Utils.hideKeyboard(getActivity());
        Utils.showAlertDialog(getActivity(),
                "",
                userByRoleList.toArray(new String[userByRoleList.size()]),
                onClickedUserByRolelistListner);
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
        switch (apiId) {
            case ApiIds.ID_GET_LEADS:
                LeadListResponseModel responseModel = (LeadListResponseModel) response;
                if (responseModel != null) {
                    if (!responseModel.isError()) {
                        // displayToast(responseModel.getMessage());
                        if (responseModel.getData().size() == 0) {
                            setNoDataEnv();
                        } else {
                            serverOriginalLeadList.addAll(responseModel.getData());
                            if (!TextUtils.isEmpty(getMyApplication().getAppPrefs().getFilterArea())) {
                                filteredArea = getMyApplication().getAppPrefs().getFilterArea();
                                et_lead__area_filter.setText(filteredArea);
                            }
                            if (!TextUtils.isEmpty(getMyApplication().getAppPrefs().getFilterLo())) {
                                et_lead_role_filter.setText(getMyApplication().getAppPrefs().getFilterLo());
                                filteredRoleID = getMyApplication().getAppPrefs().getFilterLoID();
                            }
                            if (!TextUtils.isEmpty(getMyApplication().getAppPrefs().getFilterGroup())) {
                                et_group.setText(getMyApplication().getAppPrefs().getFilterGroup());
                                filteredGroupID = getMyApplication().getAppPrefs().getFilterLoID();
                            }
                            serverLeadList.clear();
                            serverLeadList.addAll(getFilteredArr(filteredArea, filteredRoleID, filteredGroupID));
                            if (serverLeadList != null && serverLeadList.size() > 0) {
                                listAdapter.serverLeadList = serverLeadList;
                                listAdapter.notifyDataSetChanged();
                                setDataEnvServer();
                            } else {
                                setNoDataEnv();
                            }
                        }
                    } else {
                        setNoDataEnv();
                        displayErrorDialog(responseModel.getMessage());
                    }
                }
                break;
            case ApiIds.ID_USER_AREA_LIST:
                dismissProgressBar(getActivity());
                UserArea responseModelUserArea = (UserArea) response;
                MyApplication.getInstance().getAppPrefs().setUserAreaList(responseModelUserArea);

                UserAreaList = responseModelUserArea.getData();
                userArealist.clear();
                if (responseModelUserArea != null && responseModelUserArea.getData() != null) {
                    for (int i = 0; i < responseModelUserArea.getData().size(); i++) {
                        userArealist.add(responseModelUserArea.getData().get(i).getArea());
                    }
                }
                break;
            case ApiIds.ID_USER_GROUP_LIST:
                dismissProgressBar(getActivity());
                UserByRole userGroup = (UserByRole) response;
                MyApplication.getInstance().getAppPrefs().setUserByRoleList(userGroup);

                UserGroupList = userGroup.getData();
                userGroupList.clear();
                if (userGroup != null && userGroup.getData() != null) {
                    for (int i = 0; i < userGroup.getData().size(); i++) {
                        userGroupList.add(userGroup.getData().get(i).getName());
                    }
                }
                break;
            case ApiIds.ID_USERLIST_BYROLE:
                dismissProgressBar(getActivity());
                UserByRole responseModelUserRole = (UserByRole) response;
                MyApplication.getInstance().getAppPrefs().setUserByRoleList(responseModelUserRole);

                UserByRoleList = responseModelUserRole.getData();
                userByRoleList.clear();
                if (responseModelUserRole != null && responseModelUserRole.getData() != null) {
                    for (int i = 0; i < responseModelUserRole.getData().size(); i++) {
                        userByRoleList.add(responseModelUserRole.getData().get(i).getName());
                    }
                }
                break;
        }
    }


    @Override
    public void onFailResponse(int apiId, String error) {
        setNoDataEnv();
        dismissProgressBar(getActivity());
        displayErrorDialog(error);

    }


}
