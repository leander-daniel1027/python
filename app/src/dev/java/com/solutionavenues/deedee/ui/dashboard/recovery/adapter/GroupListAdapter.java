package com.solutionavenues.deedee.ui.dashboard.recovery.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.model.response.RecoveryGroupModel;

import java.util.ArrayList;


public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.Viewholder> {

    Activity mActivity;
    public ArrayList<RecoveryGroupModel.DataBean> groupList;
    View.OnClickListener onClickListener;


    public GroupListAdapter(FragmentActivity activity, ArrayList<RecoveryGroupModel.DataBean> groupList,
                            View.OnClickListener onClickListener) {
        mActivity = activity;
        this.groupList = groupList;
        this.onClickListener = onClickListener;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recovery_groups, parent, false);
        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        RecoveryGroupModel.DataBean dataBean = groupList.get(position);

        holder.tv_group_name.setText(dataBean.getGroup_name());
        holder.tv_leader_contact.setText(dataBean.getLeader_contact());
        holder.tv_leader_name.setText(dataBean.getLeader_name());
        holder.tv_emi_collection_date.setText(dataBean.getNextRecoveryDate());

        holder.iv_call.setOnClickListener(onClickListener);
        holder.iv_call.setTag(position);
        holder.iv_location.setOnClickListener(onClickListener);
        holder.iv_location.setTag(position);
        holder.iv_view.setOnClickListener(onClickListener);
        holder.iv_view.setTag(position);

    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tv_group_name, tv_leader_contact, tv_leader_name,
                tv_emi_collection_date;
        ImageView iv_call,iv_location,iv_view;


        public Viewholder(View itemView) {
            super(itemView);
            iv_call = itemView.findViewById(R.id.iv_call);
            iv_location = itemView.findViewById(R.id.iv_location);
            iv_view = itemView.findViewById(R.id.iv_view);
            tv_group_name = itemView.findViewById(R.id.tv_group_name);
            tv_leader_name = itemView.findViewById(R.id.tv_leader_name);
            tv_leader_contact = itemView.findViewById(R.id.tv_leader_contact);
            tv_emi_collection_date = itemView.findViewById(R.id.tv_emi_collection_date);
        }
    }


}
