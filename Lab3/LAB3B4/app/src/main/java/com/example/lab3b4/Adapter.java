package com.example.lab3b4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends ArrayAdapter<ToggleList>
{
    private Context cxt;
    private List<ToggleList> data;
    public Adapter(@NonNull Context context, int resource, @NonNull List<ToggleList> objects) {
        super(context, resource, objects);
        this.cxt=context;
        this.data=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ToggleList item=data.get(position);
        LayoutInflater inflater=(LayoutInflater)cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.itemgrid,null);
        ToggleButton toggleButton=convertView.findViewById(R.id.toggleButton);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!toggleButton.isChecked())
                    toggleButton.isChecked();
            }
        });


        return convertView;
    }
}

