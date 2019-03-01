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
import com.solutionavenues.deedee.model.request.AddEditEnquiryRequestModel;

import java.util.ArrayList;


/**
 * Created by brsoft on 1/7/17.
 */

public class EnquiryLocalListAdapter extends RecyclerView.Adapter<EnquiryLocalListAdapter.Viewholder> {

    Activity mActivity;
    public ArrayList<AddEditEnquiryRequestModel> enquiryList;
    View.OnClickListener onClickListener;


    public EnquiryLocalListAdapter(FragmentActivity activity, ArrayList<AddEditEnquiryRequestModel> enquiryList,
                                   View.OnClickListener onClickListener) {
        mActivity = activity;
        this.enquiryList =enquiryList;
        this.onClickListener=onClickListener;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_enquiry_list, parent, false);
        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        AddEditEnquiryRequestModel dataBean = enquiryList.get(position);
        holder.tv_name.setText(dataBean.getApplicant_name());
        holder.tv_father_name.setText(dataBean.getApplicant_father_name());
        holder.tv_mother_name.setText(dataBean.getApplicant_mother_name());
        holder.tv_dob.setText(dataBean.getApplicant_dob());
        holder.tv_aadhar_no.setText(dataBean.getApplicant_aadhar_no());
        holder.tv_mobile1.setText(dataBean.getApplicant_mobile1());
        holder.tv_mobile2.setText(dataBean.getApplicant_mobile2());
        holder.tv_aadhar_no.setText(dataBean.getApplicant_aadhar_no());
        holder.ll_parent_local.setOnClickListener(onClickListener);
        holder.ll_parent_local.setTag(position);

    }

    @Override
    public int getItemCount() {
        return enquiryList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tv_name,tv_father_name,tv_mother_name,tv_aadhar_no,
                tv_dob,tv_mobile2,tv_mobile1;
        LinearLayout ll_parent_local;



        public Viewholder(View itemView) {
            super(itemView);
            ll_parent_local = itemView.findViewById(R.id.ll_parent_local);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_father_name = itemView.findViewById(R.id.tv_father_name);
            tv_mother_name = itemView.findViewById(R.id.tv_mother_name);
            tv_aadhar_no = itemView.findViewById(R.id.tv_aadhar_no);
            tv_dob = itemView.findViewById(R.id.tv_dob);
            tv_mobile2 = itemView.findViewById(R.id.tv_mobile2);
            tv_mobile1 = itemView.findViewById(R.id.tv_mobile1);

        }
    }


}
