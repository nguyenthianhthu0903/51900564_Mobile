package com.example.lab3b3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<Item> {
    private Context ctx;
    private List<Item> data;


    public MyArrayAdapter(@NonNull Context context, int resource, @NonNull List<Item> objects) {
        super(context, resource, objects);
        this.ctx=context;
        this.data=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Item item=data.get(position);
        LayoutInflater inflater=(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.itemlist,null);
        CheckedTextView name=convertView.findViewById(R.id.textView);
        name.setText(item.getName());
        if(name.isChecked())
        {

        }
        return convertView;
    }

}
