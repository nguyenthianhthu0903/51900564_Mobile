package org.ionecode.exercise_2;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    SwitchCompat _switch;
    RecyclerView recyclerView;
    ArrayList<Event> eventsList;
    MyAdapter myAdapter;
    Event editingEvent;
    int editingEventIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.recyclerView = this.findViewById(R.id.recyclerView);
        this.eventsList = new ArrayList<>();
        this.myAdapter = new MyAdapter(this, this.eventsList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        this.loadData();

        this.recyclerView.setLayoutManager(linearLayoutManager);
        this.recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        this.recyclerView.setAdapter(this.myAdapter);
        this.recyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.option_menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.itemSwitch);
        menuItem.setActionView(R.layout.use_switch);

        this._switch = menuItem.getActionView().findViewById(R.id._switch);
        this._switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    for(Event e : MainActivity.this.eventsList) {
                        if(!e.isCheck()) {
                            e.setCheck(true);
                        }
                    }
                }else{
                    for(Event e : MainActivity.this.eventsList) {
                        if(e.isCheck()) {
                            e.setCheck(false);
                        }
                    }
                }
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add:
                this.startActivityForResult(new Intent(this, AddEventActivity.class), 1);
                break;
            case R.id.removeAll:
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setMessage("Are you sure to remove all events?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainActivity.this.myAdapter.notifyItemRangeRemoved(0, MainActivity.this.eventsList.size());
                                MainActivity.this.myAdapter.notifyItemRangeChanged(0, MainActivity.this.eventsList.size());
                                MainActivity.this.eventsList.clear();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                alertDialog.show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 1) {
            assert data != null;
            this.eventsList.add(new Event(data.getStringExtra("title"), data.getStringExtra("place"), data.getStringExtra("date"), data.getStringExtra("time")));
            this.myAdapter.notifyItemInserted(this.eventsList.size());
            this.myAdapter.notifyItemRangeChanged(this.eventsList.size(), 1);
        }else if(resultCode == -1) {
            assert data != null;
            this.editingEvent.setTitle(data.getStringExtra("title"));
            this.editingEvent.setPlace(data.getStringExtra("place"));
            this.editingEvent.setDate(data.getStringExtra("date"));
            this.editingEvent.setTime(data.getStringExtra("time"));
            this.editingEvent = null;
            this.myAdapter.notifyItemChanged(editingEventIndex);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        super.onContextItemSelected(item);

        switch(item.getItemId()) {
            case 101:
                this.editingEventIndex = item.getGroupId();
                this.editingEvent = this.eventsList.get(item.getGroupId());
                Intent intent = new Intent(this, EditEventActivity.class);

                intent.putExtra("title", this.editingEvent.getTitle());
                intent.putExtra("place", this.editingEvent.getPlace());
                intent.putExtra("date", this.editingEvent.getDate());
                intent.putExtra("time", this.editingEvent.getTime());

                this.startActivityForResult(intent, 1);
                break;
            case 102:
                this.myAdapter.notifyItemRemoved(item.getGroupId());
                this.myAdapter.notifyItemRangeChanged(item.getGroupId(), this.eventsList.size() - item.getGroupId());
                this.eventsList.remove(item.getGroupId());
                break;
        }

        return true;
    }

    @Override
    protected void onStop() {
        this.writeData();
        super.onStop();
    }

    public void writeData() {
        File file = new File(this.getFilesDir(), "my_file.txt");

        try {
            PrintWriter printWriter = new PrintWriter(file);

            for(Event e : this.eventsList) {
                printWriter.println(e.toString());
            }

            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData() {
        try {
            FileInputStream fileInputStream = this.openFileInput("my_file.txt");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            while(bufferedReader.ready()) {
                String[] event = bufferedReader.readLine().split("_");
                this.eventsList.add(new Event(event[0], event[1], event[2], event[3]));
            }

            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}