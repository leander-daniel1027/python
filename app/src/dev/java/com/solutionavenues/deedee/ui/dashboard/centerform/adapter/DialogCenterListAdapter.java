package com.solutionavenues.deedee.ui.dashboard.centerform.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.model.response.AddCenterResponseModel;

import java.util.ArrayList;


/**
 * Created by brsoft on 1/7/17.
 */

public class DialogCenterListAdapter extends RecyclerView.Adapter<DialogCenterListAdapter.Viewholder> {

    Activity mActivity;
    public ArrayList<AddCenterResponseModel.DataBean> centerList;


    public DialogCenterListAdapter(FragmentActivity activity, ArrayList<AddCenterResponseModel.DataBean> centerList) {
        mActivity = activity;
        this.centerList =centerList;

    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_center_list_dialog, parent, false);
        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        AddCenterResponseModel.DataBean dataBean = centerList.get(position);
        holder.tv_center_name.setText(dataBean.getName());
        holder.tv_center_location.setText(dataBean.getCenter_location());
       /* holder.ll_parent.setOnClickListener(onClickListener);
        holder.ll_parent.setTag(position);*/

    }

    @Override
    public int getItemCount() {
        return centerList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tv_center_name,tv_center_location;
        LinearLayout ll_parent;



        public Viewholder(View itemView) {
            super(itemView);
            ll_parent = itemView.findViewById(R.id.ll_parent);
            tv_center_name = itemView.findViewById(R.id.tv_center_name);
            tv_center_location = itemView.findViewById(R.id.tv_center_location);


        }
    }


}
