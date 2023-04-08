package org.ionecode.exercise_2;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;


public class AddEventActivity extends AppCompatActivity {

    EditText title, place, date, time;
    TextView textView1, textView1_, textView2, textView2_, textView3, textView3_, textView4, textView4_;
    String[] places = {"Ha Noi", "TPHCM", "Hue", "Thanh Hoa"};
    int[] hourSecond = {-1, -1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event_activity);

        this.title = this.findViewById(R.id.title);
        this.place = this.findViewById(R.id.place);
        this.date = this.findViewById(R.id.date);
        this.time = this.findViewById(R.id.time);
        this.textView1 = this.findViewById(R.id.textView1);
        this.textView1_ = this.findViewById(R.id.textView1_);
        this.textView2 = this.findViewById(R.id.textView2);
        this.textView2_ = this.findViewById(R.id.textView2_);
        this.textView3 = this.findViewById(R.id.textView3);
        this.textView3_ = this.findViewById(R.id.textView3_);
        this.textView4 = this.findViewById(R.id.textView4);
        this.textView4_ = this.findViewById(R.id.textView4_);

        this.title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                AddEventActivity.this.textView1_.setHint("");
                AddEventActivity.this.textView1.setHint("Name");
                if(AddEventActivity.this.place.getText().toString().equals("")) {
                    AddEventActivity.this.textView2.setHint("");
                }
                if(AddEventActivity.this.date.getText().toString().equals("")) {
                    AddEventActivity.this.textView3.setHint("");
                }
                if(AddEventActivity.this.time.getText().toString().equals("")) {
                    AddEventActivity.this.textView4.setHint("");
                }
            }
        });

        this.place.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                AddEventActivity.this.textView2_.setHint("");
                AddEventActivity.this.textView2.setHint("Place");
                if(AddEventActivity.this.title.getText().toString().equals("")) {
                    AddEventActivity.this.textView1.setHint("");
                }
                if(AddEventActivity.this.date.getText().toString().equals("")) {
                    AddEventActivity.this.textView3.setHint("");
                }
                if(AddEventActivity.this.time.getText().toString().equals("")) {
                    AddEventActivity.this.textView4.setHint("");
                }
            }
        });
        this.place.setOnClickListener(new View.OnClickListener() {
            String text;

            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(AddEventActivity.this)
                        .setTitle("Pick a place")
                        .setSingleChoiceItems(AddEventActivity.this.places, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                text = AddEventActivity.this.places[i];
                            }
                        })
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                AddEventActivity.this.place.setText(text);
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });

        this.date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                AddEventActivity.this.textView3_.setHint("");
                AddEventActivity.this.textView3.setHint("Date");
                if(AddEventActivity.this.title.getText().toString().equals("")) {
                    AddEventActivity.this.textView1.setHint("");
                }
                if(AddEventActivity.this.place.getText().toString().equals("")) {
                    AddEventActivity.this.textView2.setHint("");
                }
                if(AddEventActivity.this.time.getText().toString().equals("")) {
                    AddEventActivity.this.textView4.setHint("");
                }
            }
        });
        this.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        now.set(Calendar.YEAR, year);
                        now.set(Calendar.MONTH, monthOfYear);
                        now.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String today = DateFormat.getDateInstance(DateFormat.FULL).format((now.getTime()));AddEventActivity.this.date.setText(today);
                    }
                    },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show(AddEventActivity.this.getSupportFragmentManager(), "Pick a date");
            }
        });

        this.time.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                AddEventActivity.this.textView4_.setHint("");
                AddEventActivity.this.textView4.setHint("Time");
                if(AddEventActivity.this.title.getText().toString().equals("")) {
                    AddEventActivity.this.textView1.setHint("");
                }
                if(AddEventActivity.this.place.getText().toString().equals("")) {
                    AddEventActivity.this.textView2.setHint("");
                }
                if(AddEventActivity.this.date.getText().toString().equals("")) {
                    AddEventActivity.this.textView3.setHint("");
                }
            }
        });
        this.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        AddEventActivity.this.hourSecond[0] = i;
                        AddEventActivity.this.hourSecond[1] = i1;
                        String time = String.format(Locale.getDefault(), "%02d:%02d", i, i1);
                        AddEventActivity.this.time.setText(time);
                    }
                };
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddEventActivity.this, onTimeSetListener, AddEventActivity.this.hourSecond[0], AddEventActivity.this.hourSecond[1], true);
                timePickerDialog.setTitle("Pick a time");
                timePickerDialog.show();
            }
        });

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.option_menu_add_event, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.save:
                if(this.title.getText().toString().equals("")) {
                    this.textView1.setHint("Name");
                    this.textView1_.setHint("Please enter event name");
                    Toast.makeText(this, "Must give enough information", Toast.LENGTH_SHORT).show();
                    break;
                }

                if(this.place.getText().toString().equals("")) {
                    this.textView2.setHint("Place");
                    this.textView2_.setHint("Please enter place name");
                    Toast.makeText(this, "Must give enough information", Toast.LENGTH_SHORT).show();
                    break;
                }

                if(this.date.getText().toString().equals("")) {
                    this.textView3.setHint("Date");
                    this.textView3_.setHint("Please enter date");
                    Toast.makeText(this, "Must give enough information", Toast.LENGTH_SHORT).show();
                    break;
                }

                if(this.time.getText().toString().equals("")) {
                    this.textView4.setHint("Time");
                    this.textView4_.setHint("Please enter time");
                    Toast.makeText(this, "Must give enough information", Toast.LENGTH_SHORT).show();
                    break;
                }

                Intent intent = new Intent();

                intent.putExtra("title", this.title.getText().toString());
                intent.putExtra("place", this.place.getText().toString());
                intent.putExtra("date", this.date.getText().toString());
                intent.putExtra("time", this.time.getText().toString());

                this.setResult(Activity.RESULT_FIRST_USER, intent);
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}