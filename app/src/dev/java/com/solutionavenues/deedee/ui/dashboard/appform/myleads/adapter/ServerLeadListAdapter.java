package com.solutionavenues.deedee.ui.dashboard.appform.myleads.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.model.request.AddLeadRequestModel;

import java.util.ArrayList;


/**
 * Created by brsoft on 1/7/17.
 */

public class ServerLeadListAdapter extends RecyclerView.Adapter<ServerLeadListAdapter.Viewholder> {

    Activity mActivity;
    public ArrayList<AddLeadRequestModel> serverLeadList;
    View.OnClickListener onClickListener;
    String roleType, currentLatitude, currentLongitude;
    int rollID;

    public ServerLeadListAdapter(FragmentActivity activity, ArrayList<AddLeadRequestModel> enquiryList,
                                 View.OnClickListener onClickListener, String roleType, String currentLatitude, String currentLongitude, int rollID) {
        mActivity = activity;
        this.serverLeadList = enquiryList;
        this.onClickListener = onClickListener;
        this.roleType = roleType;
        this.currentLatitude = currentLatitude;
        this.currentLongitude = currentLongitude;
        this.rollID = rollID;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_server_lead_list, parent, false);
        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, final int position) {
        AddLeadRequestModel dataBean = serverLeadList.get(position);
        holder.tv_name.setText(dataBean.getLoanPersonelInfo().getName());
        holder.tv_father_name.setText(dataBean.getLoanPersonelInfo().getFather_name());
        holder.tv_loan_amount.setText(dataBean.getLoanPersonelInfo().getLoan_amount());
        holder.tv_dob.setText(dataBean.getLoanPersonelInfo().getDob());
        holder.tv_current_address.setText(dataBean.getAddressInfo().getAddress());
        holder.tv_mobile1.setText(dataBean.getAddressInfo().getMobile_contact());
        holder.tv_mobile2.setText(dataBean.getAddressInfo().getPermanent_phone());
        holder.tv_area.setText(dataBean.getAddressInfo().getArea());
        holder.ll_parent_server.setOnClickListener(onClickListener);
        holder.ll_parent_server.setTag(position);
        holder.tv_telecalling.setOnClickListener(onClickListener);
        holder.tv_telecalling.setTag(position);
        holder.tv_fi.setOnClickListener(onClickListener);
        holder.tv_fi.setTag(position);

        holder.tv_latitude.setText(dataBean.getLoanPersonelInfo().getLatitude());
        holder.tv_longitude.setText(dataBean.getLoanPersonelInfo().getLongitude());
//        try {
//            DateFormat formatter = new SimpleDateFormat("dd MMM, yyyy hh:mm a");
//            String d = formatter.format(dataBean..getCreated());
//            holder.tv_date.setText(d);
//        } catch (Exception e) {
//            holder.tv_date.setText(dataBean.getCreated());
//        }

        if (roleType.equalsIgnoreCase("fi")) {
            holder.tv_fi.setVisibility(View.VISIBLE);
            holder.tv_telecalling.setVisibility(View.GONE);

        } else if (roleType.equalsIgnoreCase("telecalling")) {
            holder.tv_fi.setVisibility(View.GONE);
            holder.tv_telecalling.setVisibility(View.VISIBLE);

        } else if (roleType.equalsIgnoreCase("admin")) {
            holder.tv_fi.setVisibility(View.VISIBLE);
            holder.tv_telecalling.setVisibility(View.VISIBLE);
        } else {
            holder.tv_fi.setVisibility(View.GONE);
            holder.tv_telecalling.setVisibility(View.GONE);
        }

        if (!dataBean.getFi_status().equals("2")) {
            holder.tv_fi.setVisibility(View.VISIBLE);
        } else {
            holder.tv_fi.setVisibility(View.GONE);
        }
        if (rollID == 18) {
            holder.tv_fi.setVisibility(View.GONE);
        }
        if (rollID == 31) {
            holder.ll_dob.setVisibility(View.GONE);
            holder.ll_lat.setVisibility(View.GONE);
            holder.ll_lng.setVisibility(View.GONE);
            holder.ll_fathers_name.setVisibility(View.GONE);
            holder.ll_loan_amount.setVisibility(View.GONE);
        }
        holder.navi_icon.setVisibility(View.VISIBLE);
        holder.navi_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!serverLeadList.get(position).getLoanPersonelInfo().getLatitude().equalsIgnoreCase("")
                        && !serverLeadList.get(position).getLoanPersonelInfo().getLongitude().equalsIgnoreCase("")) {
                    try {
                        String url = "http://maps.google.com/maps?saddr=" + serverLeadList.get(position).getLoanPersonelInfo().getLatitude() + "," + serverLeadList.get(position).getLoanPersonelInfo().getLongitude() +
                                "&daddr=" + currentLatitude + "," + currentLongitude;
                        Intent i1 = new Intent(Intent.ACTION_VIEW);
                        i1.setData(Uri.parse(url));
                        mActivity.startActivity(i1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mActivity, "LatLong is empty", Toast.LENGTH_SHORT).show();
                }


            }
        });
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
        return serverLeadList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tv_name, tv_father_name,
                tv_loan_amount, tv_current_address,
                tv_dob, tv_mobile1, tv_area, tv_telecalling, tv_mobile2, tv_fi, tv_latitude, tv_longitude;
        LinearLayout ll_parent_server, ll_mobile2, ll_mobile1, ll_dob, ll_lng, ll_lat, ll_currnt_address, ll_fathers_name, ll_loan_amount;
        ImageView navi_icon;

        public Viewholder(View itemView) {
            super(itemView);
            ll_parent_server = itemView.findViewById(R.id.ll_parent_server);
            ll_mobile2 = itemView.findViewById(R.id.ll_mobile2);
            ll_mobile1 = itemView.findViewById(R.id.ll_mobile1);
            ll_dob = itemView.findViewById(R.id.ll_dob);
            ll_lng = itemView.findViewById(R.id.ll_lng);
            ll_lat = itemView.findViewById(R.id.ll_lat);
            ll_currnt_address = itemView.findViewById(R.id.ll_currnt_address);
            ll_loan_amount = itemView.findViewById(R.id.ll_loan_amount);
            ll_fathers_name = itemView.findViewById(R.id.ll_fathers_name);
            tv_latitude = itemView.findViewById(R.id.tv_latitude);
            tv_longitude = itemView.findViewById(R.id.tv_longitude);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_father_name = itemView.findViewById(R.id.tv_father_name);
            tv_dob = itemView.findViewById(R.id.tv_dob);
            tv_loan_amount = itemView.findViewById(R.id.tv_loan_amount);
            tv_mobile1 = itemView.findViewById(R.id.tv_mobile1);
            tv_mobile2 = itemView.findViewById(R.id.tv_mobile2);
            tv_current_address = itemView.findViewById(R.id.tv_current_address);
            tv_telecalling = itemView.findViewById(R.id.tv_telecalling);
            tv_fi = itemView.findViewById(R.id.tv_fi);
            navi_icon = itemView.findViewById(R.id.navi_icon);
            tv_area = itemView.findViewById(R.id.tv_area);

        }
    }


}
