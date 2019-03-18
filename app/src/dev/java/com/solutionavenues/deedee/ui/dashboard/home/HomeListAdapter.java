package com.solutionavenues.deedee.ui.dashboard.home;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.model.DashboardItem;

import java.util.List;


/**
 * Created by vishwas on 13z/3/19.
 */

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.Viewholder> {

    Activity mActivity;
    public List<DashboardItem> nameList;
    View.OnClickListener onClickListener;


    public HomeListAdapter(FragmentActivity activity, List<DashboardItem> nameList,
                           View.OnClickListener onClickListener) {
        mActivity = activity;
        this.nameList = nameList;
        this.onClickListener = onClickListener;
    }

    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.homescreen_item, parent, false);
        return new Viewholder(itemview);
    }

    @Override
    public void onBindViewHolder(Viewholder holder, int position) {
        holder.tv_name.setText(nameList.get(position).getName());
        holder.hs_item_parent.setTag(position);
        holder.hs_item_parent.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView tv_name;
        LinearLayout hs_item_parent;


        public Viewholder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.name);
            hs_item_parent = itemView.findViewById(R.id.hs_item_parent);

        }
    }


}
