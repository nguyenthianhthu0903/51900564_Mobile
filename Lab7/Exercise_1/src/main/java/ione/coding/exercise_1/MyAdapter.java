package ione.coding.exercise_1;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        LinearLayout linearLayout;
        TextView name, phoneNumber;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            this.linearLayout = itemView.findViewById(R.id.linearLayout);
            this.name = itemView.findViewById(R.id.name);
            this.phoneNumber = itemView.findViewById(R.id.phoneNumber);

            this.linearLayout.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(this.getAdapterPosition(), 101, 0, "Call");
        }

    }

    Context context;
    List<UserPhone> data;

    public MyAdapter(Context context, List<UserPhone> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View root = layoutInflater.inflate(R.layout.user_phone, parent, false);

        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UserPhone userPhone = this.data.get(position);

        holder.name.setText(userPhone.name);
        holder.phoneNumber.setText(userPhone.phoneNumber);
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

}