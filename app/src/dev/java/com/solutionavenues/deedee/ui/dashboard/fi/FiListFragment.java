package com.solutionavenues.deedee.ui.dashboard.fi;

import android.app.Dialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;
import com.solutionavenues.deedee.database.tables.FiTable;
import com.solutionavenues.deedee.model.request.AddFiRequestModel;
import com.solutionavenues.deedee.model.response.FiListResponseModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.ui.dashboard.fi.adapter.LocalFiListAdapter;
import com.solutionavenues.deedee.ui.dashboard.fi.adapter.ServerFiListAdapter;

import java.util.ArrayList;

/**
 * Created by Azher on 18/6/18.
 */
public class FiListFragment extends AppBaseFragment
        implements ApiHitListener {

    private TextView tv_server_fi, tv_local_fi, tv_no_item;
    private RecyclerView rv_server_fi, rv_local_fi;
    private ArrayList<AddFiRequestModel> serverFiList = new ArrayList<>();
    private ArrayList<AddFiRequestModel> localFiList = new ArrayList<>();
    ServerFiListAdapter listAdapter;
    LocalFiListAdapter localFiAdapter;
    private SwipeRefreshLayout srl_server_fi, srl_local_fi;
    LinearLayout ll_server_fi, ll_local_fi;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_fi_list;
    }

    @Override
    public void initializeComponent() {
        findViews();
        setAdapter();
        getFiFromServer(true);
        initServerSwipeToRefresh();
        initLocalSwipeToRefresh();
        tv_server_fi.performClick();

    }

    private void findViews() {
        ll_server_fi = getView().findViewById(R.id.ll_server_fi);
        srl_server_fi = getView().findViewById(R.id.srl_server_fi);
        rv_server_fi = getView().findViewById(R.id.rv_server_fi);
        rv_server_fi.setLayoutManager(new LinearLayoutManager(getActivity()));

        ll_local_fi = getView().findViewById(R.id.ll_local_fi);
        srl_local_fi = getView().findViewById(R.id.srl_local_fi);
        rv_local_fi = getView().findViewById(R.id.rv_local_fi);
        rv_local_fi.setLayoutManager(new LinearLayoutManager(getActivity()));

        tv_no_item = getView().findViewById(R.id.tv_no_item);
        tv_server_fi = getView().findViewById(R.id.tv_server_fi);
        tv_local_fi = getView().findViewById(R.id.tv_local_fi);
        tv_server_fi.setOnClickListener(this);
        tv_local_fi.setOnClickListener(this);
    }

    private void setAdapter() {
        listAdapter = new ServerFiListAdapter(getActivity(), serverFiList, this);
        rv_server_fi.setAdapter(listAdapter);

    }

    private void setLocalEnquiryAdapter() {
        FiTable fiTable = new FiTable();
        localFiList = fiTable.getAllFi();
        if (localFiList.size() > 0) {
            localFiAdapter = new LocalFiListAdapter(getActivity(), localFiList, this);
            rv_local_fi.setAdapter(localFiAdapter);
            setDataEnvLocal();
        } else {
            setNoDataEnv();
        }
        if (srl_local_fi != null && srl_local_fi.isRefreshing()) {
            srl_local_fi.setRefreshing(false);
        }
    }

    private void initServerSwipeToRefresh() {

        srl_server_fi.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    getFiFromServer(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        srl_server_fi.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void initLocalSwipeToRefresh() {

        srl_local_fi.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    setLocalEnquiryAdapter();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        srl_local_fi.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_local_fi:
                setTabVisibility(tv_local_fi);
                setLocalEnquiryAdapter();
                break;

            case R.id.tv_server_fi:
                setTabVisibility(tv_server_fi);
                if (serverFiList != null && serverFiList.size() > 0) {
                    listAdapter.serverFiList = serverFiList;
                    listAdapter.notifyDataSetChanged();
                    setDataEnvServer();
                }else{
                    setNoDataEnv();
                }
                break;

            case R.id.ll_parent_server:
                int position_server = (int) v.getTag();
                AddFiRequestModel leadRequestModel = serverFiList.get(position_server);
                leadRequestModel.setLocalFi(false);
                addFiFormFragment(leadRequestModel);

                /*if (serverFiList.get(position_server).getStatus().equalsIgnoreCase(Constants.ENQUIRY_STATUS_PENDING)) {
                    addEditEnquiryFragment(Constants.SERVER_ENQUIRY, enquiryList.get(position_server), null);
                }else if(enquiryList.get(position_server).getStatus().equalsIgnoreCase(Constants.ENQUIRY_STATUS_APPROVED)){
                    addAppFormFragment(enquiryList.get(position_server));
                }*/

                break;
            case R.id.ll_parent_local:
                int position_local = (int) v.getTag();
                AddFiRequestModel leadRequestModel1 = localFiList.get(position_local);
                leadRequestModel1.setLocalFi(true);
                addFiFormFragment(leadRequestModel1);
                break;


        }

    }


    private void addFiFormFragment(final AddFiRequestModel dataBean) {
        final Dialog dialog = displayProgressBar(false, getActivity());
        tv_server_fi.postDelayed(new Runnable() {
            @Override
            public void run() {
                AddFiFragment fragment = new AddFiFragment();
                int enterAnimation = R.anim.translate_right_to_left_anim;
                int exitAnimation = 0;
                int enterAnimationBackStack = 0;
                int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
                fragment.setFiDataForEdit(dataBean);
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


    private void setTabVisibility(TextView textView) {
        textView.setTextColor(getResources().getColor(R.color.colorAccent));
        textView.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        if (textView.getId() == tv_local_fi.getId()) {
            tv_server_fi.setTextColor(getResources().getColor(R.color.colorWhite));
            tv_server_fi.setBackgroundColor(getResources().getColor(R.color.semi_black));
        } else if (textView.getId() == tv_server_fi.getId()) {
            tv_local_fi.setTextColor(getResources().getColor(R.color.colorWhite));
            tv_local_fi.setBackgroundColor(getResources().getColor(R.color.semi_black));
        }

    }


    // call to getFiFromServer service
    private void getFiFromServer(boolean progressVisibility) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            if (progressVisibility) {
                displayProgressBar(false, getActivity());
            }
            String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
            getRestClient().callback(this).getFi(user_id);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    private void setNoDataEnv() {
        if (tv_server_fi.getCurrentTextColor() == getResources().getColor(R.color.colorAccent)) {
            srl_server_fi.setVisibility(View.VISIBLE);
            srl_local_fi.setVisibility(View.GONE);
        }else {
            srl_server_fi.setVisibility(View.GONE);
            srl_local_fi.setVisibility(View.VISIBLE);
        }
        rv_local_fi.setVisibility(View.GONE);
        rv_server_fi.setVisibility(View.GONE);
        tv_no_item.setVisibility(View.VISIBLE);
    }

    private void setDataEnvServer() {
        tv_no_item.setVisibility(View.GONE);
        rv_local_fi.setVisibility(View.GONE);
        rv_server_fi.setVisibility(View.VISIBLE);
        srl_local_fi.setVisibility(View.GONE);
        srl_server_fi.setVisibility(View.VISIBLE);
    }


    private void setDataEnvLocal() {
        tv_no_item.setVisibility(View.GONE);
        rv_server_fi.setVisibility(View.GONE);
        rv_local_fi.setVisibility(View.VISIBLE);
        srl_local_fi.setVisibility(View.VISIBLE);
        srl_server_fi.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessResponse(int apiId, Object response) {
        if (srl_server_fi != null && srl_server_fi.isRefreshing()) {
            srl_server_fi.setRefreshing(false);
        } else {
            dismissProgressBar(getActivity());
        }
        if (apiId == ApiIds.ID_GET_FI) {
            FiListResponseModel responseModel = (FiListResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    if (responseModel.getData().size() == 0) {
                        setNoDataEnv();
                    } else {
                        listAdapter.serverFiList = responseModel.getData();
                        serverFiList = responseModel.getData();
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
