package com.solutionavenues.deedee.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.solutionavenues.deedee.R;
import com.solutionavenues.deedee.util.Utils;

public class AlertDialogWithSearch extends Dialog implements View.OnClickListener {

    private ListView list;
    private EditText filterText;
    ArrayAdapter<String> adapter;
    private Utils.ListItemWithDataClickListener onClickListener;

    public AlertDialogWithSearch(Context context, String[] cityList, Utils.ListItemWithDataClickListener itemClickListener) {
        super(context);
        this.onClickListener = itemClickListener;
        /** Design the dialog in main.xml file */
        setContentView(R.layout.citylistview);
        filterText = findViewById(R.id.EditBox);
        filterText.addTextChangedListener(filterTextWatcher);
        list = findViewById(R.id.List);
        adapter = new ArrayAdapter<>(context, R.layout.simple_list_item, cityList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                onClickListener.onItemClick(position, adapter.getItem(position));
                dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private TextWatcher filterTextWatcher = new TextWatcher() {

        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            adapter.getFilter().filter(s);

        }
    };

    @Override
    public void onStop() {
        filterText.removeTextChangedListener(filterTextWatcher);
    }
}