package com.example.appnote;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.appnote.database.LabelDatabase;

import java.util.List;

public class LabelAdapter extends ArrayAdapter<LabelItem> {
    private Context ctx;
    private List<LabelItem> data;

    public LabelAdapter( Context context, int resource, List<LabelItem> objects) {
        super(context, resource, objects);
        this.ctx=context;
        this.data=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LabelItem item=data.get(position);
        LayoutInflater inflater=(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.itemlabel,null);
        TextView title=convertView.findViewById(R.id.contentLabel);
        title.setText(item.getLabelName());
        RelativeLayout itemLayout=convertView.findViewById(R.id.layoutItemLabel);
        ImageView iconDelete=convertView.findViewById(R.id.deleteLabel);
        iconDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LabelDatabase.getInstance(getContext()).labelDao().deleleLabel(item);
                itemLayout.setVisibility(View.GONE);
            }

        });
        LabelDatabase.getInstance(getContext()).labelDao().getListLabel();
        return convertView;
    }

}
