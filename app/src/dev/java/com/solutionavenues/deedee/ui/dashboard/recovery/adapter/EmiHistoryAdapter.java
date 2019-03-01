package com.solutionavenues.deedee.ui.dashboard.recovery.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.model.response.GetEmiHistoryModel;

import java.util.ArrayList;


public class EmiHistoryAdapter extends RecyclerView.Adapter<EmiHistoryAdapter.Viewholder> {

    Activity mActivity;
    public ArrayList<GetEmiHistoryModel.DataBean.EmiDataBean> emiList;
    View.OnClickListener onClickListener;



    public EmiHistoryAdapter(Activity activity, ArrayList<GetEmiHistoryModel.DataBean.EmiDataBean> emiList,
                             View.OnClickListener onClickListener) {
        mActivity = activity;
        this.emiList = emiList;
        this.onClickListener = onClickListener;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recovery_emi_history, parent, false);
        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, final int position) {
        GetEmiHistoryModel.DataBean.EmiDataBean dataBean = emiList.get(position);

        holder.tv_installment_no.setText(dataBean.getInst_no() + "");
        holder.tv_emi_date.setText(dataBean.getEmi_date());
        holder.tv_emi_amount.setText("");
        holder.tv_part_payment.setText(dataBean.getPart_payment());
        holder.tv_pending_emi_amount.setText(dataBean.getAmount_need_to_collect()+"");
        holder.tv_total_charges.setText("");
        holder.tv_notes.setText(dataBean.getNotes());
        holder.tv_collected_amount.setText(dataBean.getCollected_amount());
        holder.tv_delay_charge_collected.setText(dataBean.getDelay_charge_collected());
        holder.tv_delay_charges.setText(dataBean.getDelay_charge() + "");
        holder.tv_delay_days.setText(dataBean.getDelay_day() + "");
        holder.tv_signature.setText(dataBean.getSingnature());
        holder.tv_collected_from.setText(dataBean.getCollect_from());
        holder.tv_collected_self.setText(dataBean.getCollect_from_self());
        holder.tv_present.setText(dataBean.getPresent_on_center());








    }


    @Override
    public int getItemCount() {
        return emiList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tv_part_payment, tv_pending_emi_amount, tv_total_charges,
                tv_notes, tv_collected_amount, tv_delay_charge_collected, tv_installment_no,
                tv_emi_date, tv_emi_amount, tv_delay_charges,tv_signature,
                tv_collected_from,tv_collected_self,tv_present,
                tv_delay_days;



        public Viewholder(View itemView) {
            super(itemView);
            tv_notes = itemView.findViewById(R.id.tv_notes);
            tv_collected_amount = itemView.findViewById(R.id.tv_collected_amount);
            tv_delay_charge_collected = itemView.findViewById(R.id.tv_delay_charge_collected);
            tv_part_payment = itemView.findViewById(R.id.tv_part_payment);
            tv_pending_emi_amount = itemView.findViewById(R.id.tv_pending_emi_amount);
            tv_total_charges = itemView.findViewById(R.id.tv_total_charges);
            tv_installment_no = itemView.findViewById(R.id.tv_installment_no);
            tv_emi_date = itemView.findViewById(R.id.tv_emi_date);
            tv_emi_amount = itemView.findViewById(R.id.tv_emi_amount);
            tv_delay_charges = itemView.findViewById(R.id.tv_delay_charges);
            tv_delay_days = itemView.findViewById(R.id.tv_delay_days);
            tv_collected_amount = itemView.findViewById(R.id.tv_collected_amount);
            tv_signature = itemView.findViewById(R.id.tv_signature);
            tv_collected_self = itemView.findViewById(R.id.tv_collected_self);
            tv_present = itemView.findViewById(R.id.tv_present);
            tv_collected_from = itemView.findViewById(R.id.tv_collected_from);

        }
    }



}
