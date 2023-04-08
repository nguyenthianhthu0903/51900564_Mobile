package org.ionecode.exercise_2;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    public static class MyHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        LinearLayout linearLayout;
        TextView title, place, date, time;
        SwitchCompat _switch;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            this.linearLayout = itemView.findViewById(R.id.linearLayout);
            this.title = itemView.findViewById(R.id.title);
            this.place = itemView.findViewById(R.id.place);
            this.date = itemView.findViewById(R.id.date);
            this.time = itemView.findViewById(R.id.time);
            this._switch = itemView.findViewById(R.id._switch);

            this.linearLayout.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(this.getAdapterPosition(), 101, 0, "Edit");
            contextMenu.add(this.getAdapterPosition(), 102, 1, "Delete");
        }
    }

    Context context;
    List<Event> eventsList;

    MyAdapter(Context context, List<Event> data) {
        this.context = context;
        this.eventsList = data;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View root = layoutInflater.inflate(R.layout.recycler_view_for_events_list, parent, false);
        MyHolder myHolder = new MyHolder(root);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Event event = this.eventsList.get(position);

        holder.title.setText(event.getTitle());
        holder.place.setHint(event.getPlace());
        holder.date.setHint(event.getDate());
        holder.time.setHint(event.getTime());

        event.setLinearLayout(holder.linearLayout);
        event.set_switch(holder._switch);
    }

    @Override
    public int getItemCount() {
        return this.eventsList.size();
    }

    public void edit(int i) {

    }

    public void remove(int i) {
        this.notifyItemRemoved(i);
        this.notifyItemRangeChanged(i, this.eventsList.size() - i);
        this.eventsList.remove(i);
    }

}