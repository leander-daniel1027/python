package com.solutionavenues.deedee.ui.dashboard.recovery.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.solutionavenues.deedee.MyApplication;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.model.request.SubmitRecoveryDetailsRequestModel;
import com.solutionavenues.deedee.model.response.GroupMemberListModel;
import com.solutionavenues.deedee.util.Constants;
import com.solutionavenues.deedee.util.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.Viewholder> {

    Activity mActivity;
    public ArrayList<GroupMemberListModel.DataBean.MembersBean> membersList;
    View.OnClickListener onClickListener;
    TextView textView;
    int positionClicked;


    public MemberListAdapter(Activity activity, ArrayList<GroupMemberListModel.DataBean.MembersBean> membersList,
                             View.OnClickListener onClickListener) {
        mActivity = activity;
        this.membersList = membersList;
        this.onClickListener = onClickListener;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recovery_group_members, parent, false);
        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, final int position) {
        GroupMemberListModel.DataBean.MembersBean dataBean = membersList.get(position);
        if (dataBean.getIs_closed().equalsIgnoreCase("1")) {
            holder.ll_closed.setVisibility(View.VISIBLE);
            holder.tv_dead.setVisibility(View.GONE);
            holder.tv_submit.setVisibility(View.GONE);
        }else {
            holder.ll_closed.setVisibility(View.GONE);
            holder.tv_dead.setVisibility(View.VISIBLE);
            holder.tv_submit.setVisibility(View.VISIBLE);
        }
        holder.tv_member_name.setText(dataBean.getName());
        holder.tv_lead_id.setText(dataBean.getApplication_area_id() + "");
        holder.tv_contact.setText(dataBean.getMobile_contact());
        holder.tv_installment_no.setText(dataBean.getEmiData().getInst_no() + "");
        holder.tv_emi_date.setText(dataBean.getEmiData().getEmi_date());
        holder.tv_emi_amount.setText(Utils.formatDecimal(dataBean.getEmiData().getEmi_amount()));
        holder.tv_pending_amount.setText(Utils.formatDecimal(dataBean.getEmiData().getPending_amount()));
        holder.tv_delay_charges.setText(Utils.formatDecimal(dataBean.getEmiData().getDelay_charges()));
        holder.tv_delay_days.setText(dataBean.getEmiData().getDelay_days() + "");
        if (TextUtils.isEmpty(dataBean.getEmiData().getCollect_from())) {
            holder.tv_collected_from.setText("");
        }else{
            holder.tv_collected_from.setText(dataBean.getEmiData().getCollect_from() + "");
        }

        holder.tv_dead.setOnClickListener(onClickListener);
        holder.tv_dead.setTag(position);
        holder.tv_view_details.setOnClickListener(onClickListener);
        holder.tv_view_details.setTag(position);
        holder.tv_submit.setOnClickListener(onClickListener);
        holder.tv_submit.setTag(position);
        holder.tv_collected_from.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView= (TextView) v;
                positionClicked=position;
                Utils.hideKeyboard(mActivity);
                Utils.showAlertDialog(mActivity,
                        mActivity.getString(R.string.collected_from),
                        mActivity.getResources().getStringArray(R.array.collected_from_arr),
                        onClickedCollectedFromListner);
            }
        });
    }
    DialogInterface.OnClickListener onClickedCollectedFromListner = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int item) {
            membersList.get(positionClicked).getEmiData().setCollect_from(mActivity.getResources().getStringArray(R.array.collected_from_arr)[item]);
            textView.setText("" + mActivity.getResources().getStringArray(R.array.collected_from_arr)[item]);
        }
    };


    private void disableClosedView(){

    }

    @Override
    public int getItemCount() {
        return membersList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tv_member_name, tv_lead_id, tv_contact,
                tv_view_details, tv_dead, tv_submit, tv_installment_no,
                tv_emi_date, tv_emi_amount, tv_pending_amount, tv_delay_charges,
                tv_delay_days, tv_collected_from;
        EditText et_collected_amount, et_collected_delay_charge;
        CheckBox cb_signature, cb_collected_self, cb_present;
        LinearLayout ll_closed;


        public Viewholder(View itemView) {
            super(itemView);
            ll_closed = itemView.findViewById(R.id.ll_closed);
            tv_view_details = itemView.findViewById(R.id.tv_view_details);
            tv_dead = itemView.findViewById(R.id.tv_dead);
            tv_submit = itemView.findViewById(R.id.tv_submit);
            tv_member_name = itemView.findViewById(R.id.tv_member_name);
            tv_lead_id = itemView.findViewById(R.id.tv_lead_id);
            tv_contact = itemView.findViewById(R.id.tv_contact);
            tv_installment_no = itemView.findViewById(R.id.tv_installment_no);
            tv_emi_date = itemView.findViewById(R.id.tv_emi_date);
            tv_emi_amount = itemView.findViewById(R.id.tv_emi_amount);
            tv_pending_amount = itemView.findViewById(R.id.tv_pending_amount);
            tv_delay_charges = itemView.findViewById(R.id.tv_delay_charges);
            tv_delay_days = itemView.findViewById(R.id.tv_delay_days);
            et_collected_amount = itemView.findViewById(R.id.et_collected_amount);
            et_collected_delay_charge = itemView.findViewById(R.id.et_collected_delay_charge);
            cb_signature = itemView.findViewById(R.id.cb_signature);
            cb_collected_self = itemView.findViewById(R.id.cb_collected_self);
            cb_present = itemView.findViewById(R.id.cb_present);
            tv_collected_from = itemView.findViewById(R.id.tv_collected_from);

        }
    }


    public SubmitRecoveryDetailsRequestModel getRecoveryData(Viewholder holder, int position) {
        SubmitRecoveryDetailsRequestModel requestModel = new SubmitRecoveryDetailsRequestModel();
        requestModel.setUser_id(MyApplication.getInstance().getUserPrefs().getLoggedInUser().getId());
        requestModel.setRecovery_id(membersList.get(position).getEmiData().getRecovery_id());
        requestModel.setCollected_amount(holder.et_collected_amount.getText().toString());
        requestModel.setSingnature(getCheckStatus(holder.cb_signature));
        requestModel.setCollect_from(holder.tv_collected_from.getText().toString());
        requestModel.setCollect_from_self(getCheckStatus(holder.cb_collected_self));
        requestModel.setCollected_date(getCurrentDate());
        requestModel.setPresent_on_center(getCheckStatus(holder.cb_present));
        requestModel.setDelay_charge(holder.et_collected_delay_charge.getText().toString());
        requestModel.setApplication_area_id(membersList.get(position).getApplication_area_id());
        return requestModel;
    }

    private String getCheckStatus(CheckBox checkBox) {
        if (checkBox.isChecked()) {
            return mActivity.getString(R.string.yes);
        } else
            return mActivity.getString(R.string.no);
    }

    public String getCurrentDate() {
        String date = new SimpleDateFormat(Constants.SERVER_DATE_FORMAT, Locale.getDefault()).format(new Date());
        return date;
    }

}
