package com.example.appnote;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.List;

public class LabelAdapterChoose extends ArrayAdapter<LabelItem> {
    private Context ctx;
    private List<LabelItem> data;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean switch_status,choose_status=false;

    public LabelAdapterChoose( Context context, int resource, List<LabelItem> objects) {
        super(context, resource, objects);
        this.ctx=context;
        this.data=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        LabelItem item=data.get(position);
        LayoutInflater inflater=(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.labelchoose,null);


        TextView title=convertView.findViewById(R.id.title);
        title.setText(item.getLabelName());
        CheckBox checkLabel=convertView.findViewById(R.id.checkLabel);
        if(item.getCheckLabel()==true)
            checkLabel.setChecked(true);


        checkLabel.setOnCheckedChangeListener(null);
        checkLabel.setSelected(item.getCheckLabel());
        checkLabel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                String result="";
                if (isChecked)
                {
                    item.setCheckLabel(true);
//                    Toast.makeText(getContext(),String.valueOf(item.getCheckLabel()),Toast.LENGTH_SHORT).show();
                } else {
                    item.setCheckLabel(false);
//                    Toast.makeText(getContext(),String.valueOf(item.getCheckLabel()),Toast.LENGTH_SHORT).show();
                }
            }
        });


        return convertView;
    }
}
