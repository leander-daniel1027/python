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



public class LocalFiListAdapter extends RecyclerView.Adapter<LocalFiListAdapter.Viewholder> {

    Activity mActivity;
    public ArrayList<AddFiRequestModel> localFiList;
    View.OnClickListener onClickListener;


    public LocalFiListAdapter(FragmentActivity activity, ArrayList<AddFiRequestModel> localFiList,
                              View.OnClickListener onClickListener) {
        mActivity = activity;
        this.localFiList =localFiList;
        this.onClickListener=onClickListener;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_local_fi_list, parent, false);
        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        AddFiRequestModel dataBean = localFiList.get(position);
        holder.tv_applicant_name.setText(dataBean.getName_of_applicant());
        holder.tv_father_name.setText(dataBean.getName_of_father());
        holder.tv_loan_amount.setText(dataBean.getLoan_amount());
        holder.tv_native_address.setText(dataBean.getNative_address());
        holder.tv_mobile1.setText(dataBean.getMobile());
        holder.tv_mobile2.setText(dataBean.getLandlord_mobile());
        holder.ll_parent_local.setOnClickListener(onClickListener);
        holder.ll_parent_local.setTag(position);

    }

    @Override
    public int getItemCount() {
        return localFiList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tv_applicant_name,tv_father_name,tv_loan_amount,tv_native_address
                ,tv_mobile2,tv_mobile1;
        LinearLayout ll_parent_local;



        public Viewholder(View itemView) {
            super(itemView);
            ll_parent_local = itemView.findViewById(R.id.ll_parent_local);
            tv_applicant_name = itemView.findViewById(R.id.tv_applicant_name);
            tv_father_name = itemView.findViewById(R.id.tv_father_name);
            tv_loan_amount = itemView.findViewById(R.id.tv_loan_amount);
            tv_native_address = itemView.findViewById(R.id.tv_native_address);
            tv_mobile2 = itemView.findViewById(R.id.tv_mobile2);
            tv_mobile1 = itemView.findViewById(R.id.tv_mobile1);

        }
    }


}
