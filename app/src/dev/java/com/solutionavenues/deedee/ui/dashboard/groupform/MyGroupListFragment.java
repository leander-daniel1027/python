package com.solutionavenues.deedee.ui.dashboard.groupform;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.helper.ConnectionDetector;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.appBase.AppBaseFragment;
import com.solutionavenues.deedee.model.response.GroupListResponseModel;
import com.solutionavenues.deedee.rest.ApiHitListener;
import com.solutionavenues.deedee.rest.ApiIds;
import com.solutionavenues.deedee.ui.dashboard.cgt.AddCGTFormFragment;
import com.solutionavenues.deedee.ui.dashboard.groupform.adapter.GroupListAdapter;
import com.solutionavenues.deedee.ui.dashboard.grt.AddGRTFragment;

import java.util.ArrayList;

/**
 * Created by Azher on 23/7/18.
 */
public class MyGroupListFragment extends AppBaseFragment
        implements ApiHitListener {

    private TextView tv_no_item;
    private SwipeRefreshLayout srl_groups;
    private RecyclerView rv_groups;
    private ArrayList<GroupListResponseModel.DataBean> groupList = new ArrayList<>();
    GroupListAdapter listAdapter;

    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_group_list;
    }

    @Override
    public void initializeComponent() {
        findViews();
        setAdapter();
        getMyGroups(true);

    }

    private void findViews() {
        srl_groups = getView().findViewById(R.id.srl_groups);
        rv_groups = getView().findViewById(R.id.rv_groups);
        rv_groups.setLayoutManager(new LinearLayoutManager(getActivity()));
        tv_no_item = getView().findViewById(R.id.tv_no_item);
        initSwipeToRefresh();
    }

    private void initSwipeToRefresh() {

        srl_groups.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    getMyGroups(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        srl_groups.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    private void setAdapter() {
        listAdapter = new GroupListAdapter(getActivity(), groupList, this);
        rv_groups.setAdapter(listAdapter);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cgt:
                int pos_cgt = (int) v.getTag();
                addCgtFragment(groupList.get(pos_cgt));
                break;
            case R.id.tv_grt:
                int pos_grt = (int) v.getTag();
                addGrtFragment(groupList.get(pos_grt));
                break;


        }

    }


    private void addGrtFragment(GroupListResponseModel.DataBean dataBean) {
        AddGRTFragment fragment = new AddGRTFragment();
        int enterAnimation = R.anim.translate_right_to_left_anim;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
        fragment.setGroupData(dataBean);
        try {
            getDashBoardActivity().changeFragment(fragment, true, false, 0,
                    enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addCgtFragment(GroupListResponseModel.DataBean dataBean) {
        AddCGTFormFragment fragment = new AddCGTFormFragment();
        int enterAnimation = R.anim.translate_right_to_left_anim;
        int exitAnimation = 0;
        int enterAnimationBackStack = 0;
        int exitAnimationBackStack = R.anim.translate_left_to_right_medium;
        fragment.setGroupData(dataBean);
        try {
            getDashBoardActivity().changeFragment(fragment, true, false, 0,
                    enterAnimation, exitAnimation, enterAnimationBackStack, exitAnimationBackStack, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // call to getMyGroups service
    private void getMyGroups(boolean progressVisibility) {
        if (ConnectionDetector.isNetAvail(getActivity())) {
            if (progressVisibility) {
                displayProgressBar(false, getActivity());
            }
            String user_id = String.valueOf(getMyApplication().getUserPrefs().getLoggedInUser().getId());
            getRestClient().callback(this).getMyGroups(user_id);
        } else {
            displayErrorDialog(getString(R.string.error_internet_connection));
        }
    }

    private void setNoDataEnv() {
        tv_no_item.setVisibility(View.VISIBLE);
        rv_groups.setVisibility(View.GONE);
    }

    private void setDataEnvServer() {
        tv_no_item.setVisibility(View.GONE);
        rv_groups.setVisibility(View.VISIBLE);
    }


    @Override
    public void onSuccessResponse(int apiId, Object response) {
        if (srl_groups != null && srl_groups.isRefreshing()) {
            srl_groups.setRefreshing(false);
        } else {
            dismissProgressBar(getActivity());
        }
        if (apiId == ApiIds.ID_GET_GROUPS) {
            GroupListResponseModel responseModel = (GroupListResponseModel) response;
            if (responseModel != null) {
                if (!responseModel.isError()) {
                    if (responseModel.getData().size() == 0) {
                        setNoDataEnv();
                    } else {
                        listAdapter.groupList = responseModel.getData();
                        groupList = responseModel.getData();
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
