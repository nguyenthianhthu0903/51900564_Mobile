package com.example.lab10b2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    String province;
    TextView temp,city,cloud,pressure,humidity,wind,dateNow;
    ImageView status;
    AlertDialog actions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getID();
        getProvince("Saigon");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.province,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.province:
                showAlertDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getID()
    {
        city=findViewById(R.id.province);
        temp=findViewById(R.id.temperature);
        cloud=findViewById(R.id.cloud);
        humidity=findViewById(R.id.humidity);
        wind=findViewById(R.id.wind);
        pressure=findViewById(R.id.pressure);
        dateNow=findViewById(R.id.viewDay);
        status=findViewById(R.id.status);
    }
    private void showAlertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose an Option");
        String[] options = { "Thanh Pho Ho Chi Minh","Ha Noi", "Can Tho","Da Nang","Dong Nai","Binh Duong","Vinh Long","Quang Ninh","Thai Binh","Phu Tho" };
        builder.setItems(options, actionListener);
        builder.setNegativeButton("Cancel", null);
        actions = builder.create();
        actions.show();
    }
    DialogInterface.OnClickListener actionListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    getProvince("Saigon");
                    break;
                case 1:
                    getProvince("Hanoi");
                    break;
                case 2:
                    getProvince("Can Tho");
                    break;
                case 3:
                    getProvince("Da Nang");
                    break;
                case 4:
                    getProvince("Dong Nai");
                    break;
                case 5:
                    getProvince("Binh Duong");
                    break;
                case 6:
                    getProvince("Vinh Long");
                    break;
                case 7:
                    getProvince("Quang Ninh");
                    break;
                case 8:
                    getProvince("Thai Binh");
                    break;
                case 9:
                    getProvince("Phu Tho");
                    break;
                default:
                    break;
            }
        }
    };
    
    public void getProvince(String provice)
    {
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        String url="https://api.openweathermap.org/data/2.5/weather?q="+provice+"&appid=d0f87503d441d9c85a90c501e682c62c&units=metric";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    String cityNameJs=jsonObject.getString("name");
                    String dayJs=jsonObject.getString("dt");
                    long l=Long.valueOf(dayJs);
                    Date date=new Date(l*1000L);
                    SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
                    String DayAfter= simpleDateFormat.format(date);

                    city.setText(cityNameJs);
                    dateNow.setText(DayAfter);

                    JSONArray jsonArrayWeather=jsonObject.getJSONArray("weather");
                    JSONObject jsonObjectWeather=jsonArrayWeather.getJSONObject(0);
                    String statusJs=jsonObjectWeather.getString("main");
                    String iconJs=jsonObjectWeather.getString("icon");

                    Picasso.with(MainActivity.this).load("http://openweathermap.org/img/wn/"+iconJs+".png").into(status);

                    JSONObject jsonObjectMain=jsonObject.getJSONObject("main");
                    String temJs=jsonObjectMain.getString("temp");
                    Double a=Double.valueOf(temJs);
                    String temJSAf=String.valueOf(a.intValue());
                    String pressJs=jsonObjectMain.getString("pressure");
                    String humiJs=jsonObjectMain.getString("humidity");


                    pressure.setText(pressJs+"\n"+"Pressure");
                    temp.setText(temJSAf);
                    humidity.setText(humiJs+"\n"+"Humidity");

                    JSONObject jsonObjectWind=jsonObject.getJSONObject("wind");
                    String windJs=jsonObjectWind.getString("speed");
                    wind.setText(windJs+"\n"+"Wind");

                    JSONObject jsonObjectCloud=jsonObject.getJSONObject("clouds");
                    String cloudJs=jsonObjectCloud.getString("all");
                    cloud.setText(cloudJs+"\n"+"Cloud");



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}