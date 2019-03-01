package com.solutionavenues.deedee.ui.dashboard.grt.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.BaseRecycleAdapter;
import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.model.request.AddGrtRequestModel;

import java.util.ArrayList;


/**
 * Created by Sunil kumar yadav on 6/3/18.
 */

public class MembersChildAdapter extends BaseRecycleAdapter {
    private Context context;
    ArrayList<AddGrtRequestModel.QuestionsBean> questionList;
    UpdateMembersList listener;
    private int parentPosition;
    TextView tv_question;
    String question;
    public MembersChildAdapter(Context context,
                               ArrayList<AddGrtRequestModel.QuestionsBean> questionList,
                               UpdateMembersList listener, int parentPosition, TextView tv_question, String question) {
        this.context = context;
        this.questionList = questionList;
        this.listener = listener;
        this.parentPosition = parentPosition;
        this.tv_question = tv_question;
        this.question = question;
    }

    @Override
    public BaseViewHolder getViewHolder() {
        return new ViewHolder(inflateLayout(R.layout.item_member_view));
    }

    @Override
    public int getDataCount() {
        return questionList.get(parentPosition).getAll_members() == null ? 0 : questionList.get(parentPosition).getAll_members().size();
    }

    public class ViewHolder extends BaseViewHolder {
        private TextView tv_member_name;
        public CheckBox cb_member;
        LinearLayout ll_member_parent;

        public ViewHolder(View itemView) {
            super(itemView);
            ll_member_parent = itemView.findViewById(R.id.ll_member_parent);
            tv_member_name = itemView.findViewById(R.id.tv_member_name);
            cb_member = itemView.findViewById(R.id.cb_member);
        }

        @Override
        public void setData(final int position) {
            tv_member_name.setText(questionList.get(parentPosition).getAll_members().get(position).getName());
            ll_member_parent.setTag(position);
            ll_member_parent.setOnClickListener(this);

            if(parentPosition == 2){
                if(cb_member.isChecked()){
                    tv_question.setText(question+"(Married)");
                }else {
                    tv_question.setText(question+"(Widowed)");
                }
            }
           /* if (membersList.get(position).isSelected()) {
                cb_member.setChecked(true);
            } else {
                cb_member.setChecked(false);
            }*/
            cb_member.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (parentPosition == 2)
                            tv_question.setText(question+"(Married)");
                        questionList.get(parentPosition).getAll_members().get(position).setSelected(true);
                    } else {
                        if (parentPosition == 2)
                            tv_question.setText(question+"(Widowed)");
                        questionList.get(parentPosition).getAll_members().get(position).setSelected(false);
                    }
                    listener.updateMembers(parentPosition,questionList.get(parentPosition).getAll_members());
                }
            });

        }

        @Override
        public void onClick(View v) {
            performItemClick((int) v.getTag(), v);
        }
    }


    public interface UpdateMembersList {
        void updateMembers(int parentPosition,ArrayList<AddGrtRequestModel.QuestionsBean.AllMembers> list);
    }

}
