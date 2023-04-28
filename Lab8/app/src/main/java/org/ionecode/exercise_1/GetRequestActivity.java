package org.ionecode.exercise_1;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class GetRequestActivity extends AppCompatActivity {

    class GetURL extends AsyncTask<String, String, String> {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        @Override
        protected String doInBackground(String... strings) {
            Request.Builder requestBuilder = new Request.Builder();
            requestBuilder.url(strings[0]);

            Request request = requestBuilder.build();
            try {
                Response response = this.okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "Wrong URL";
        }

        @Override
        protected void onPostExecute(String s) {
            GetRequestActivity.this.textView.setText(s);

            super.onPostExecute(s);
        }
    }

    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_get_request);

        this.textView = this.findViewById(R.id.textView);
    }

    public void getRequest(View view) {
        new GetURL().execute("https://translate.google.com/?hl=vi");
    }

}