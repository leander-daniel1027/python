package com.solutionavenues.deedee.ui.dashboard.grt.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.BaseRecycleAdapter;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.model.request.AddGrtRequestModel;
import com.utilities.ItemClickSupport;

import java.util.ArrayList;


public class QuestionListAdapter extends BaseRecycleAdapter {
    private Context context;
    ArrayList<AddGrtRequestModel.QuestionsBean> questionList;
    MembersChildAdapter.UpdateMembersList listener;

    public QuestionListAdapter(Context context,
                               ArrayList<AddGrtRequestModel.QuestionsBean> questionList,
                               MembersChildAdapter.UpdateMembersList listener) {
        this.context = context;
        this.questionList = questionList;
        this.listener = listener;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_test_question));
    }

    @Override
    public int getDataCount() {
        return questionList == null ? 0 : questionList.size();
    }


    public class ViewHolder extends BaseViewHolder implements CompoundButton.OnCheckedChangeListener {
        public TextView tv_question;
        public RecyclerView rv_group_members;
        public LinearLayout ll_test_question;
        public MembersChildAdapter adapter;
        CheckBox cb_select_all;


        public ViewHolder(View itemView) {
            super(itemView);
            tv_question = itemView.findViewById(R.id.tv_question);
            rv_group_members = itemView.findViewById(R.id.rv_group_members);
            cb_select_all = itemView.findViewById(R.id.cb_select_all);
            cb_select_all.setOnCheckedChangeListener(this);
            ll_test_question = itemView.findViewById(R.id.ll_test_question);
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
            rv_group_members.setLayoutManager(layoutManager);


            ItemClickSupport.addTo(rv_group_members).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                @Override
                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                    performChildItemClick((int) recyclerView.getTag(), position, v);
                }
            });
        }

        @Override
        public void setData(int position) {
            adapter = new MembersChildAdapter(getContext(), questionList, listener, position, tv_question, questionList.get(position).getQuestion());
            rv_group_members.setAdapter(adapter);
            tv_question.setText(questionList.get(position).getQuestion());
            rv_group_members.setTag(position);
            ll_test_question.setTag(position);
            ll_test_question.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            performItemClick((Integer) v.getTag(), v);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            adapter.setSelectAll(b);
        }
    }
}
