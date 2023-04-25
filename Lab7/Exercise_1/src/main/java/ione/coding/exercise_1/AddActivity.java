package ione.coding.exercise_1;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class AddActivity extends AppCompatActivity {

    EditText name, phoneNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user_phone);

        this.name = this.findViewById(R.id.name);
        this.phoneNumber = this.findViewById(R.id.phoneNumber);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.activity_add_user_phone_save, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save) {
            Intent intent = new Intent();
            intent.putExtra("name", this.name.getText().toString());
            intent.putExtra("phoneNumber", this.phoneNumber.getText().toString());

            this.setResult(MainActivity.RESULT_CODE_FOR_SAVE_USER_PHONE, intent);
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

}