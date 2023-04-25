package ione.coding.exercise_1;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    EditText search;
    RecyclerView recyclerView;
    ArrayList<UserPhone> data;
    MyAdapter myAdapter;
    static int REQUEST_CODE_FOR_READ_CONTACTS_PERMISSION = 100;
    static int RESULT_CODE_FOR_SAVE_USER_PHONE = 200;
    static int REQUEST_CODE_FOR_WRITE_CONTACTS_PERMISSION = 300;
    static int REQUEST_CODE_FOR_CALL_PHONE_PERMISSION = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.search = this.findViewById(R.id.search);
        this.recyclerView = this.findViewById(R.id.recyclerView);
        this.data = new ArrayList<>();

        this.checkReadContactPermission();
        this.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void afterTextChanged(Editable editable) {
                if(MainActivity.this.search.getText().toString().equals("")) {
                    MainActivity.this.myAdapter.data = MainActivity.this.data;
                }else{
                    ArrayList<UserPhone> dataTemp = new ArrayList<>();
                    String match = MainActivity.this.search.getText().toString().toLowerCase(Locale.ROOT);
                    for(UserPhone userPhone : MainActivity.this.data) {
                        if(userPhone.name.toLowerCase(Locale.ROOT).contains(match) || userPhone.phoneNumber.toLowerCase(Locale.ROOT).contains(match)) {
                            dataTemp.add(userPhone);
                        }
                    }
                    MainActivity.this.myAdapter.data = dataTemp;
                }
                MainActivity.this.myAdapter.notifyDataSetChanged();
            }
        });
    }

    public void checkReadContactPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // When permission is not granted
            // Request permission
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_CONTACTS}, REQUEST_CODE_FOR_READ_CONTACTS_PERMISSION);
        }else{
            // When permission is granted
            // Get contact list
            this.getContactList();
        }
    }

    public void getContactList() {
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        String sort = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC";
        Cursor cursor = this.getContentResolver().query(uri, null, null, null, sort);

        if(cursor.getCount() > 0) {
            while(cursor.moveToNext()) {
                @SuppressLint("Range") String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Uri uriPhone = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " =?";
                Cursor cursorPhone = this.getContentResolver().query(uriPhone, null, selection, new String[] {id}, null);

                if(cursorPhone.moveToNext()) {
                    @SuppressLint("Range") String phoneNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    UserPhone userPhone = new UserPhone(name, phoneNumber);
                    this.data.add(userPhone);
                    cursorPhone.close();
                }
            }
            cursor.close();
        }

        this.myAdapter = new MyAdapter(this, this.data);
        this.recyclerView.setAdapter(this.myAdapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void checkWriteContactPermission(@NonNull UserPhone userPhone) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // When permission is not granted
            // Request permission
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_CONTACTS}, REQUEST_CODE_FOR_WRITE_CONTACTS_PERMISSION);
        }else{
            // When permission is granted
            // Add contact
            this.addContact(userPhone);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addContact(@NonNull UserPhone userPhone) {
        ArrayList<ContentProviderOperation> contentProviderOperations = new ArrayList<>();
        contentProviderOperations.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build()
        );
        // Add name
        contentProviderOperations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, userPhone.name)
                .build()
        );
        // Add number
        contentProviderOperations.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, userPhone.phoneNumber)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build()
        );

        try {
            this.getContentResolver().applyBatch(ContactsContract.AUTHORITY, contentProviderOperations);
        } catch (OperationApplicationException | RemoteException e) {
            e.printStackTrace();
        }

        this.data.add(userPhone);
        this.myAdapter.notifyDataSetChanged();
    }

    public void checkCallPhonePermission(String number) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // When permission is not granted
            // Request permission
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CALL_PHONE}, REQUEST_CODE_FOR_CALL_PHONE_PERMISSION);
        }else{
            // When permission is granted
            // Let call phone
            this.callPhone(number);
        }
    }

    public void callPhone(String number) {
        this.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number)));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_CODE_FOR_READ_CONTACTS_PERMISSION) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // When permission is granted
                // Get contact list
                this.getContactList();
            }else{
                // When permission is denied
                // Display toast for not working
                Toast.makeText(this, "Please grant this permission for this app to run!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.activity_main_add, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add) {
            this.startActivityForResult(new Intent(this, AddActivity.class), 1);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == 101) {
            UserPhone userPhone = this.data.get(item.getGroupId());
            this.checkCallPhonePermission(userPhone.phoneNumber);
        }

        return super.onContextItemSelected(item);  // return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_CODE_FOR_SAVE_USER_PHONE) {
            assert data != null;
            String name = data.getStringExtra("name");
            String phoneNumber = data.getStringExtra("phoneNumber");
            this.checkWriteContactPermission(new UserPhone(name, phoneNumber));
        }else{
            Toast.makeText(this, "Add user fail", Toast.LENGTH_SHORT).show();
        }
    }

}