package org.ionecode.exercise_1;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Item> items;
    MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.recyclerView = this.findViewById(R.id.recyclerView);
        this.initItems();
        this.myAdapter = new MyAdapter(this, this.items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        this.recyclerView.setAdapter(this.myAdapter);
        this.recyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.option_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.deleteAll:
                this.items.clear();
                this.myAdapter.notifyDataSetChanged();
                break;
            case R.id.deleteSelected:
                this.items.removeIf(Item::getCheckBox);
                this.myAdapter.notifyDataSetChanged();

                for(Item i : this.items) {
                    i.setItemCheck(false);
                }

                break;
            case R.id.checkAll:
                for(Item i : this.items) {
                    if(!i.getCheckBox()) {
                        i.setItemCheck(true);
                    }
                }

                break;
            case R.id.uncheckAll:
                for(Item i : this.items) {
                    if(i.getCheckBox()) {
                        i.setItemCheck(false);
                    }
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initItems() {
        this.items = new ArrayList<>();
        this.items.add(new Item("Apple"));
        this.items.add(new Item("Samsung"));
        this.items.add(new Item("Nokia"));
        this.items.add(new Item("Oppo"));
    }

}