package com.solutionavenues.deedee.ui.dashboard.fi.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.model.request.AddFiRequestModel;

import java.util.ArrayList;



public class ServerFiListAdapter extends RecyclerView.Adapter<ServerFiListAdapter.Viewholder> {

    Activity mActivity;
    public ArrayList<AddFiRequestModel> serverFiList;
    View.OnClickListener onClickListener;


    public ServerFiListAdapter(FragmentActivity activity, ArrayList<AddFiRequestModel> enquiryList,
                               View.OnClickListener onClickListener) {
        mActivity = activity;
        this.serverFiList =enquiryList;
        this.onClickListener=onClickListener;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_server_fi_list, parent, false);
        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        AddFiRequestModel dataBean = serverFiList.get(position);
        holder.tv_applicant_name.setText(dataBean.getName_of_applicant());
        holder.tv_father_name.setText(dataBean.getName_of_father());
        holder.tv_loan_amount.setText(dataBean.getLoan_amount());
        holder.tv_native_address.setText(dataBean.getNative_address());
        holder.tv_mobile1.setText(dataBean.getMobile());
        holder.tv_mobile2.setText(dataBean.getLandlord_mobile());
        holder.ll_parent_server.setOnClickListener(onClickListener);
        holder.ll_parent_server.setTag(position);

      /*  if (dataBean.getData().get.equalsIgnoreCase(Constants.ENQUIRY_STATUS_PENDING)) {
            holder.tv_status.setText(mActivity.getString(R.string.pending));
        }else if (dataBean.getStatus().equalsIgnoreCase(Constants.ENQUIRY_STATUS_APPROVED)) {
            holder.tv_status.setText(mActivity.getString(R.string.approved));
        }else if (dataBean.getStatus().equalsIgnoreCase(Constants.ENQUIRY_STATUS_REJECTED)) {
            holder.tv_status.setText(mActivity.getString(R.string.rejected));
        }else if (dataBean.getStatus().equalsIgnoreCase(Constants.ENQUIRY_STATUS_ONHOLD)) {
            holder.tv_status.setText(mActivity.getString(R.string.on_hold));
        }*/


    }

    @Override
    public int getItemCount() {
        return serverFiList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tv_applicant_name,tv_father_name,tv_loan_amount,tv_native_address
                ,tv_mobile2,tv_mobile1;
        LinearLayout ll_parent_server;



        public Viewholder(View itemView) {
            super(itemView);
            ll_parent_server = itemView.findViewById(R.id.ll_parent_server);
            tv_applicant_name = itemView.findViewById(R.id.tv_applicant_name);
            tv_father_name = itemView.findViewById(R.id.tv_father_name);
            tv_loan_amount = itemView.findViewById(R.id.tv_loan_amount);
            tv_native_address = itemView.findViewById(R.id.tv_native_address);
            tv_mobile2 = itemView.findViewById(R.id.tv_mobile2);
            tv_mobile1 = itemView.findViewById(R.id.tv_mobile1);
        }
    }


}
