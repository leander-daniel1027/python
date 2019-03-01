package com.solutionavenues.deedee.ui.dashboard.appform.myleads.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.model.request.AddLeadRequestModel;

import java.util.ArrayList;


/**
 * Created by brsoft on 1/7/17.
 */

public class LocalLeadListAdapter extends RecyclerView.Adapter<LocalLeadListAdapter.Viewholder> {

    Activity mActivity;
    public ArrayList<AddLeadRequestModel> localLeadList;
    View.OnClickListener onClickListener;


    public LocalLeadListAdapter(FragmentActivity activity, ArrayList<AddLeadRequestModel> localLeadList,
                                View.OnClickListener onClickListener) {
        mActivity = activity;
        this.localLeadList =localLeadList;
        this.onClickListener=onClickListener;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_lead_list, parent, false);
        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        AddLeadRequestModel dataBean = localLeadList.get(position);
        holder.tv_name.setText(dataBean.getLoanPersonelInfo().getName());
        holder.tv_father_name.setText(dataBean.getLoanPersonelInfo().getFather_name());
        holder.tv_loan_amount.setText(dataBean.getLoanPersonelInfo().getLoan_amount());
        holder.tv_dob.setText(dataBean.getLoanPersonelInfo().getDob());
        holder.tv_current_address.setText(dataBean.getAddressInfo().getCurrent_address());
        holder.tv_mobile1.setText(dataBean.getAddressInfo().getMobile_contact());
        holder.tv_mobile2.setText(dataBean.getAddressInfo().getPermanent_phone());
        holder.tv_latitude.setText(dataBean.getLoanPersonelInfo().getLatitude());
        holder.tv_longitude.setText(dataBean.getLoanPersonelInfo().getLongitude());

        holder.ll_parent_local.setOnClickListener(onClickListener);
        holder.ll_parent_local.setTag(position);

    }

    @Override
    public int getItemCount() {
        return localLeadList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_father_name,tv_loan_amount,tv_current_address,
                tv_dob,tv_mobile2,tv_mobile1,tv_latitude,tv_longitude;
        LinearLayout ll_parent_local;



        public Viewholder(View itemView) {
            super(itemView);
            ll_parent_local = itemView.findViewById(R.id.ll_parent_local);
            tv_latitude = itemView.findViewById(R.id.tv_latitude);
            tv_longitude = itemView.findViewById(R.id.tv_longitude);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_father_name = itemView.findViewById(R.id.tv_father_name);
            tv_loan_amount = itemView.findViewById(R.id.tv_loan_amount);
            tv_current_address = itemView.findViewById(R.id.tv_current_address);
            tv_dob = itemView.findViewById(R.id.tv_dob);
            tv_mobile2 = itemView.findViewById(R.id.tv_mobile2);
            tv_mobile1 = itemView.findViewById(R.id.tv_mobile1);

        }
    }


}
