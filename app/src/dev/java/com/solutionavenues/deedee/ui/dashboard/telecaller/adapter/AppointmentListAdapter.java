package com.solutionavenues.deedee.ui.dashboard.telecaller.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.model.response.AppointmentListResponseModel;

import java.util.ArrayList;




public class AppointmentListAdapter extends RecyclerView.Adapter<AppointmentListAdapter.Viewholder> {

    Activity mActivity;
    public ArrayList<AppointmentListResponseModel.DataBean> appointmentList;
    View.OnClickListener onClickListener;


    public AppointmentListAdapter(FragmentActivity activity, ArrayList<AppointmentListResponseModel.DataBean> appointmentList,
                                  View.OnClickListener onClickListener) {
        mActivity = activity;
        this.appointmentList =appointmentList;
        this.onClickListener=onClickListener;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment_list, parent, false);
        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        AppointmentListResponseModel.DataBean dataBean = appointmentList.get(position);
        holder.et_date.setText(dataBean.getTele_date());
        holder.et_place.setText(dataBean.getPlace());
        holder.et_remark.setText(dataBean.getRemark());

        holder.tv_delete.setOnClickListener(onClickListener);
        holder.tv_delete.setTag(position);



    }

    @Override
    public int getItemCount() {
        return appointmentList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        EditText et_date,et_place,et_remark;
        TextView tv_delete;




        public Viewholder(View itemView) {
            super(itemView);

            et_date = itemView.findViewById(R.id.et_date);
            et_place = itemView.findViewById(R.id.et_place);
            et_remark = itemView.findViewById(R.id.et_remark);
            tv_delete = itemView.findViewById(R.id.tv_delete);


        }
    }


}
