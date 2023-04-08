package org.ionecode.exercise_1;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    public static class MyHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        CheckBox checkBox;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            this.imageView = itemView.findViewById(R.id.imageView);
            this.textView = itemView.findViewById(R.id.textView);
            this.checkBox = itemView.findViewById(R.id.checkBox);
        }
    }

    Context context;
    List<Item> data;

    MyAdapter(Context context, List<Item> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View root = layoutInflater.inflate(R.layout.recycler_view_custome, parent, false);

        MyHolder myHolder = new MyHolder(root);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Item item = this.data.get(position);

        holder.imageView.setImageResource(R.drawable.ic_phone);

        holder.textView.setText(item.getName());

        item.setCheckBox(holder.checkBox);
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

}