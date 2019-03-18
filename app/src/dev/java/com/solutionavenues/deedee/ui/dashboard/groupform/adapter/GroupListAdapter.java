package com.solutionavenues.deedee.ui.dashboard.groupform.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.model.response.GroupListResponseModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.Viewholder> {

    Activity mActivity;
    public ArrayList<GroupListResponseModel.DataBean> groupList;
    View.OnClickListener onClickListener;
    int roleType;


    public GroupListAdapter(FragmentActivity activity, ArrayList<GroupListResponseModel.DataBean> groupList,
                            View.OnClickListener onClickListener, int roleType) {
        mActivity = activity;
        this.groupList = groupList;
        this.onClickListener = onClickListener;
        this.roleType = roleType;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_list, parent, false);
        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        GroupListResponseModel.DataBean dataBean = groupList.get(position);

        holder.tv_group_name.setText(dataBean.getName());
        holder.tv_leader_contact.setText(dataBean.getLeader_contact());
        holder.tv_leader_name.setText(dataBean.getLeader_name());
        holder.tv_number_of_member.setText(dataBean.getNumber_of_member() + "");
        holder.tv_latitude.setText(dataBean.getLat());
        holder.tv_longitude.setText(dataBean.getLng());
        try {
            DateFormat input = new SimpleDateFormat("dd MMM, yyyy, hh:mm a");
            DateFormat output = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date d = output.parse(dataBean.getCreated());
            holder.tv_date.setText(input.format(d));
        } catch (Exception e) {
            holder.tv_date.setText(dataBean.getCreated());
        }
        holder.tv_cgt.setTag(position);
        holder.tv_cgt.setOnClickListener(onClickListener);
        holder.tv_grt.setTag(position);
        holder.tv_grt.setOnClickListener(onClickListener);

        switch (roleType){
            case 18:
                holder.tv_cgt.setVisibility(View.GONE);
                holder.tv_grt.setVisibility(View.GONE);
                break;
            case 19:
                holder.tv_cgt.setVisibility(View.GONE);
                break;
            case 31:
                holder.tv_grt.setVisibility(View.GONE);
                break;

        }

    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tv_group_name, tv_leader_contact, tv_leader_name,
                tv_number_of_member, tv_cgt, tv_grt, tv_date, tv_latitude, tv_longitude;


        public Viewholder(View itemView) {
            super(itemView);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_latitude = itemView.findViewById(R.id.tv_latitude);
            tv_longitude = itemView.findViewById(R.id.tv_longitude);

            tv_group_name = itemView.findViewById(R.id.tv_group_name);
            tv_leader_name = itemView.findViewById(R.id.tv_leader_name);
            tv_leader_contact = itemView.findViewById(R.id.tv_leader_contact);
            tv_number_of_member = itemView.findViewById(R.id.tv_number_of_member);
            tv_cgt = itemView.findViewById(R.id.tv_cgt);
            tv_grt = itemView.findViewById(R.id.tv_grt);


        }
    }


}
