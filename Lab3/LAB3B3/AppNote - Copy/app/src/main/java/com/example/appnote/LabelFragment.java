package com.example.appnote;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appnote.database.LabelDatabase;

import java.util.ArrayList;
import java.util.List;


public class LabelFragment extends Fragment {
    private ListView listView;
    private List<LabelItem> listItemLabel;
    private LabelAdapter adapter;

    private EditText inputLabel;
    private ImageView iconDoneLabel,refresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.labelfragment,container,false);

        inputLabel=view.findViewById(R.id.inputLabel);
        listView=view.findViewById(R.id.listViewLabel);
        listView.setDivider(null);
        iconDoneLabel=view.findViewById(R.id.iconDoneLabel);


        iconDoneLabel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addLabel();
            }
        });
        listItemLabel=LabelDatabase.getInstance(getActivity()).labelDao().getListLabel();
        adapter=new LabelAdapter(getActivity(), android.R.layout.simple_list_item_1,listItemLabel);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        return view;
    }

    public void addLabel()
    {
        listItemLabel=LabelDatabase.getInstance(getActivity()).labelDao().getListLabel();
        String newlable=inputLabel.getText().toString().trim();
        if(TextUtils.isEmpty(newlable))
        {
            Toast.makeText(getActivity(),"Nhập tên nhãn",Toast.LENGTH_SHORT).show();
            return;
        }
        LabelItem newlabel=new LabelItem(newlable,false);

        LabelDatabase.getInstance(getActivity()).labelDao().insertLabel(newlabel);
        Toast.makeText(getActivity(),"Thành công",Toast.LENGTH_SHORT).show();

        inputLabel.setText("");
        listItemLabel=LabelDatabase.getInstance(getActivity()).labelDao().getListLabel();
        adapter=new LabelAdapter(getActivity(), android.R.layout.simple_list_item_1,listItemLabel);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

    }
    public void loadData()
    {
        listItemLabel=LabelDatabase.getInstance(getActivity()).labelDao().getListLabel();
        adapter=new LabelAdapter(getActivity(), android.R.layout.simple_list_item_1,listItemLabel);
        listView.setAdapter(adapter);
    }
}