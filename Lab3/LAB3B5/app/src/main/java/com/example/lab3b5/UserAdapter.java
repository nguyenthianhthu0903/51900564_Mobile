package com.example.lab3b5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User>
{
    private Context ctx;
    private List<User> data;
    public UserAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);
        this.ctx=context;
        this.data=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User user=data.get(position);
        LayoutInflater inflater=(LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.itemlist,null);
        TextView name=convertView.findViewById(R.id.userName);
        TextView mail=convertView.findViewById(R.id.mailUser);
        name.setText(user.getName());
        mail.setText(user.getMail());
        return convertView;
    }
}
