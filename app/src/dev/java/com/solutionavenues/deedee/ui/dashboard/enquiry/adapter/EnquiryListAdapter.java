package com.solutionavenues.deedee.ui.dashboard.enquiry.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.model.response.EnquiryListResponseModel;
import com.solutionavenues.deedee.util.Constants;

import java.util.ArrayList;


/**
 * Created by brsoft on 1/7/17.
 */

public class EnquiryListAdapter extends RecyclerView.Adapter<EnquiryListAdapter.Viewholder> {

    Activity mActivity;
    public ArrayList<EnquiryListResponseModel.DataBean> enquiryList;
    View.OnClickListener onClickListener;


    public EnquiryListAdapter(FragmentActivity activity, ArrayList<EnquiryListResponseModel.DataBean> enquiryList,
                              View.OnClickListener onClickListener) {
        mActivity = activity;
        this.enquiryList =enquiryList;
        this.onClickListener=onClickListener;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_enquiry_list, parent, false);
        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        EnquiryListResponseModel.DataBean dataBean = enquiryList.get(position);
        holder.tv_name.setText(dataBean.getApplicant_name());
        holder.tv_father_name.setText(dataBean.getApplicant_father_name());
        holder.tv_mother_name.setText(dataBean.getApplicant_mother_name());
        holder.tv_dob.setText(dataBean.getApplicant_dob().split("T")[0]);
        holder.tv_aadhar_no.setText(dataBean.getApplicant_aadhar_no());
        holder.tv_mobile1.setText(dataBean.getApplicant_mobile1());
        holder.tv_mobile2.setText(dataBean.getApplicant_mobile2());
        holder.tv_aadhar_no.setText(dataBean.getApplicant_aadhar_no());
        if (dataBean.getStatus().equalsIgnoreCase(Constants.ENQUIRY_STATUS_PENDING)) {
            holder.tv_status.setText(mActivity.getString(R.string.pending));
        }else if (dataBean.getStatus().equalsIgnoreCase(Constants.ENQUIRY_STATUS_APPROVED)) {
            holder.tv_status.setText(mActivity.getString(R.string.approved));
        }else if (dataBean.getStatus().equalsIgnoreCase(Constants.ENQUIRY_STATUS_REJECTED)) {
            holder.tv_status.setText(mActivity.getString(R.string.rejected));
        }else if (dataBean.getStatus().equalsIgnoreCase(Constants.ENQUIRY_STATUS_ONHOLD)) {
            holder.tv_status.setText(mActivity.getString(R.string.on_hold));
        }

        if (dataBean.getCebil_status().equalsIgnoreCase(Constants.CIBIL_STATUS_GOOD)) {
            holder.tv_cibil_status.setText(mActivity.getString(R.string.good));
        }else if (dataBean.getCebil_status().equalsIgnoreCase(Constants.CIBIL_STATUS_BAD)) {
            holder.tv_cibil_status.setText(mActivity.getString(R.string.bad));
        }else if (dataBean.getCebil_status().equalsIgnoreCase(Constants.CIBIL_STATUS_PENDING)) {
            holder.tv_cibil_status.setText(mActivity.getString(R.string.pending));
        }

        holder.ll_parent.setOnClickListener(onClickListener);
        holder.ll_parent.setTag(position);

    }

    @Override
    public int getItemCount() {
        return enquiryList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_father_name,tv_mother_name,tv_aadhar_no,
                tv_dob,tv_mobile2,tv_mobile1,tv_cibil_status,tv_status;
        LinearLayout ll_parent;



        public Viewholder(View itemView) {
            super(itemView);
            ll_parent = itemView.findViewById(R.id.ll_parent);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_father_name = itemView.findViewById(R.id.tv_father_name);
            tv_mother_name = itemView.findViewById(R.id.tv_mother_name);
            tv_aadhar_no = itemView.findViewById(R.id.tv_aadhar_no);
            tv_dob = itemView.findViewById(R.id.tv_dob);
            tv_mobile2 = itemView.findViewById(R.id.tv_mobile2);
            tv_mobile1 = itemView.findViewById(R.id.tv_mobile1);
            tv_cibil_status = itemView.findViewById(R.id.tv_cibil_status);
            tv_status = itemView.findViewById(R.id.tv_status);

        }
    }


}
