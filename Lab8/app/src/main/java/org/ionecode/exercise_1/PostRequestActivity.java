package org.ionecode.exercise_1;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class PostRequestActivity extends AppCompatActivity {

    class PostUserToURL extends AsyncTask<String, Void, String> {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        String username;
        String password;

        public PostUserToURL(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected String doInBackground(String... strings) {
//            RequestBody requestBody = new MultipartBody.Builder()
//                    .addFormDataPart("username", this.username)
//                    .addFormDataPart("password", this.password)
//                    .setType(MultipartBody.FORM)
//                    .build();

            RequestBody requestBody = new FormBody.Builder()
                    .add("username", this.username)
                    .add("password", this.password)
                    .build();

            Request request = new Request.Builder()
                    .url(strings[0])
                    .post(requestBody)
                    .build();

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
            Log.e("TAG", "Response from url: " + s);

            super.onPostExecute(s);
        }

    }

    EditText username;
    EditText password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_post_request);

        this.username = this.findViewById(R.id.username);
        this.password = this.findViewById(R.id.password);
    }

    public void login(View view) {
        String username = this.username.getText().toString();
        String password = this.password.getText().toString();
        new PostUserToURL(username, password).execute("http://192.168.0.102/OKHTTP/postuser.php");
    }

}