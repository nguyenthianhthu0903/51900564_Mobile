package com.example.appnote;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.appnote.database.NotesDatabase;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import yuku.ambilwarna.AmbilWarnaDialog;

public class CreateNote extends AppCompatActivity
{
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    private EditText titleNote,subtitleNote,mainNote;
    private TextView dateNote;
    private ImageView iconSaveNote;

    //Pin
    private ImageView notePin;
    Boolean clicked = true;
    int resultPin = 0;

    //picture
    private String selectedPicturePath;
    private ImageView addImage;
    private ImageView pictureInNote;
    private static final int REQUEST_CODE_STORAGE_PERMISSION=1;
    private static final int REQUEST_CODE_SELECT_IMAGE=2;

    //update
    private NoteManager already; //ghi chú được click vào

    //label
    TextView labelContent;
    String namelabel="";
    private static final int REQUEST_CODE_LABEL = 10;

    //video
    VideoView videoView;
    ImageView iconAddVideo;
    int SELECT_VIDEO_REQUEST=100;
    String selectedVideoPath;
    MediaController mc;

    //Set color
    private CoordinatorLayout coordinatorLayout;
    private ImageView chooseColor;
    private int mDeaufaultColor;
    private String selectedColorNote="";
    private ImageView colorLeftSubtitle;
    private ImageView chooseLabel;

    //Thông  báo
    private ImageView addAlarm;
    String timeChoose;
    String dateChoose;
    ImageView iconDate,iconTime;
    TextView timeAndDateAlarm;
    Boolean clickeAlarm = true;
    int resultAlarm = 0;

    //Password
    ImageView iconLock;
    Boolean clickLock=true;
    int resultLock=0;
    EditText inputPass,passNew;
    String pass="";
    String passAfter="";

    //Delete
    ImageView deleteItem;

    //Audio
    TextView timePlay;
    Button play;
    SeekBar seekbar;
    String pathAudio="";
    String duration;
    MediaPlayer mediaPlayer;
    ScheduledExecutorService timer;
    public static final int PICK_FILE =99;
    ImageView iconAudio;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        ImageView iconBack=findViewById(R.id.iconBack);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                onBackPressed();
            }
        });

        //FIREBASE
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        titleNote=findViewById(R.id.noteTitle);
        subtitleNote=findViewById(R.id.subNote);
        mainNote=findViewById(R.id.inputNote);
        dateNote=findViewById(R.id.dateNote);
        iconSaveNote=findViewById(R.id.saveNote);

        //pin
        notePin=findViewById(R.id.pinNote);
        notePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clicked)
                {
                    clicked = false;
                    notePin.setImageResource(R.drawable.ic_pinred);
                    resultPin=1;
                    Toast.makeText(getApplicationContext(),"Ghim ghi chú",Toast.LENGTH_SHORT).show();

                } else {
                    clicked = true;
                    notePin.setImageResource(R.drawable.ic_notpin);
                    resultPin=0;
                    Toast.makeText(getApplicationContext(),"Bỏ ghim ghi chú",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //label
        chooseLabel=findViewById(R.id.iconChooseLabel);
        labelContent=findViewById(R.id.labelContent);
        labelContent.setVisibility(GONE);

        chooseLabel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent=new Intent(CreateNote.this,Chooselabel.class);
                intent.putExtra("blackpink",labelContent.getText().toString());
                startActivityForResult(intent,REQUEST_CODE_LABEL);
            }
        });


        //Video
        selectedVideoPath="";
        videoView=findViewById(R.id.videoInNote);
        iconAddVideo=findViewById(R.id.iconAddVideo);
        mc=new MediaController(CreateNote.this);
        videoView.setMediaController(mc);


        iconAddVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pickIntent = new Intent(Intent.ACTION_PICK);
                pickIntent.setType("video/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent,"Select Video"),SELECT_VIDEO_REQUEST);
                startActivityForResult(pickIntent,SELECT_VIDEO_REQUEST);
            }
        });

        //image
        selectedPicturePath="";
        pictureInNote=findViewById(R.id.pictureInNote);
        addImage=findViewById(R.id.iconAddImg);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(CreateNote.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_STORAGE_PERMISSION);
                }
                else {
                    selectPicture();
                }
            }
        });

        //Audio

        //color
        coordinatorLayout=findViewById(R.id.screenCreateNote);
        chooseColor=findViewById(R.id.iconChooseColor);
        colorLeftSubtitle=findViewById(R.id.colorLeftSubtitle);

        mDeaufaultColor= ContextCompat.getColor(this,R.color.colorPrimary);
        chooseColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });

        //ngày giờ note
        String time=new SimpleDateFormat("EEE, dd MMMM yyyy HH:mm a",Locale.getDefault()).format(new Date());
        dateNote.setText(time);

        selectedColorNote="-463802";

        createNotificationChannel();

        //Thông báo
        timeAndDateAlarm=findViewById(R.id.timeAndDateAlarm);
        addAlarm=findViewById(R.id.addAlarm);

        iconDate=findViewById(R.id.chooseDate);

        iconDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });
        iconTime=findViewById(R.id.chooseTime);
        iconTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime();
            }
        });

        iconSaveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });

        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (clickeAlarm)
                {
                    clickeAlarm = false;
                    addAlarm.setImageResource(R.drawable.ic_alarm);
                    resultAlarm=1;
                    Toast.makeText(getApplication(),"Thời gian nhắc nhở: "+dateChoose+"  "+timeChoose,Toast.LENGTH_SHORT).show();
                    setAlarm(mainNote.getText().toString(),dateChoose,timeChoose);
                    timeAndDateAlarm.setText(dateChoose+"  "+timeChoose);
                }
                else
                {
                    clickeAlarm = true;
                    addAlarm.setImageResource(R.drawable.notalarm);
                    resultAlarm=0;
                    setAlarm("","","");
                    timeAndDateAlarm.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"Tắt nhắc nhở",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //PASSWORD
        iconLock=findViewById(R.id.iconLock);
//        inputPass.setText("NULL");
        iconLock.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (clickLock)
                {
                    clickLock = false;
                    iconLock.setImageResource(R.drawable.lockok);
                    resultLock=1;
                    AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Tạo mật khẩu cho ghi chú");
                    builder.setIcon(R.drawable.key);
                    inputPass=new EditText(view.getContext());
                    builder.setView(inputPass);

                    builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which)
                        {
                            pass=inputPass.getText().toString();
                            Toast.makeText(view.getContext(),"Đặt mật khẩu thành công",Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            clickLock = true;
                            iconLock.setImageResource(R.drawable.lock);
                            resultLock=0;
                            Toast.makeText(getApplicationContext(),"Tắt mật khẩu",Toast.LENGTH_SHORT).show();
                        }
                    });
                    AlertDialog ad=builder.create();
                    ad.show();
                }
                else
                {
                    clickLock = true;
                    iconLock.setImageResource(R.drawable.lock);
                    resultLock=0;
                    Toast.makeText(getApplicationContext(),"Tắt mật khẩu",Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Delete
        deleteItem=findViewById(R.id.deleteNote);
        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                AlertDialog dialog=new AlertDialog.Builder(view.getContext())
                        .setTitle("Xóa ghi chú")
                        //button
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {

                                @SuppressLint("StaticFieldLeak")
                                class DeleteNoteTask extends AsyncTask<Void,Void,Void>
                                {

                                    @Override
                                    protected Void doInBackground(Void... voids)
                                    {
                                        NotesDatabase.getNotesDatabase(getApplicationContext()).daonote()
                                                .deleteNote(already);

//                                        if(getApplicationContext() instanceof MainActivity)
//                                        {
//                                            ((MainActivity) getApplicationContext()).listRemoved.add(already);
//                                        }
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Void unused)
                                    {
                                        super.onPostExecute(unused);
                                        Intent intent=new Intent();
                                        intent.putExtra("noteDeleted",true);
                                        setResult(RESULT_OK,intent);
                                        finish();
                                    }
                                }
                                new DeleteNoteTask().execute();
                            }
                        })
                        .setNegativeButton("Cancel",null)//không đồng ý
                        .create();
                dialog.show();
            }
        });

        //AUDIO
        seekbar=findViewById(R.id.seekbar1);
        timePlay=findViewById(R.id.textView3);
        iconAudio=findViewById(R.id.iconSound);
        iconAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("audio/*");
                startActivityForResult(intent, PICK_FILE);
            }
        });
        play=findViewById(R.id.buttonPlay);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()){
                        mediaPlayer.pause();
                        play.setText("PLAY");
                        timer.shutdown();
                    } else {
                        mediaPlayer.start();
                        play.setText("PAUSE");

                        timer = Executors.newScheduledThreadPool(1);
                        timer.scheduleAtFixedRate(new Runnable() {
                            @Override
                            public void run() {
                                if (mediaPlayer != null) {
                                    if (!seekbar.isPressed()) {
                                        seekbar.setProgress(mediaPlayer.getCurrentPosition());
                                    }
                                }
                            }
                        }, 10, 10, TimeUnit.MILLISECONDS);
                    }
                }
            }
        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null){
                    int millis = mediaPlayer.getCurrentPosition();
                    long total_secs = TimeUnit.SECONDS.convert(millis, TimeUnit.MILLISECONDS);
                    long mins = TimeUnit.MINUTES.convert(total_secs, TimeUnit.SECONDS);
                    long secs = total_secs - (mins*60);
                    timePlay.setText(mins + ":" + secs + " / " + duration);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (mediaPlayer != null) {
                    mediaPlayer.seekTo(seekbar.getProgress());
                }
            }
        });
        play.setEnabled(false);

        //CẬP NHẬT LẠI
        if(getIntent().getBooleanExtra("ViewOrUpdate",false))
        {
            already=(NoteManager) getIntent().getSerializableExtra("note");
            setViewOrUpdate();
        }
    }

    //SELECT TIME
    public void selectTime()
    {
        Calendar calendar=Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR);
        int minutes=calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minutes)
            {
                timeChoose=FormatTime(hour,minutes);
            }
        },hour,minutes,false);
        timePickerDialog.show();
    }

    //SELECT DATE
    public void selectDate()
    {
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
//                dateChoose=String.format("%d/%d/%d",dayofMonth,month+1,year);
                dateChoose=day+"-"+(month+1)+"-"+year;            }
        },year,month,day);
        datePickerDialog.show();
    }

    //THÔNG BÁO
    public void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence name="LemubitReminderChannel";
            String descreption="Channel for Lemubit Reminder";
            int importance= NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel=new NotificationChannel("notifylemubit",name,importance);
            channel.setDescription(descreption);

            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
//        Notification notification=
    }
    public String FormatTime(int hour, int minute) {

        String time;
        time = "";
        String formattedMinute;

        if (minute / 10 == 0) {
            formattedMinute = "0" + minute;
        } else {
            formattedMinute = "" + minute;
        }


        if (hour == 0) {
            time = "12" + ":" + formattedMinute + " AM";
        } else if (hour < 12) {
            time = hour + ":" + formattedMinute + " AM";
        } else if (hour == 12) {
            time = "12" + ":" + formattedMinute + " PM";
        } else {
            int temp = hour - 12;
            time = temp + ":" + formattedMinute + " PM";
        }


        return time;
    }

    private void submit()
    {
        setAlarm("",dateChoose,timeChoose);
    }


    //EDIT AND UPDATE
    private void setViewOrUpdate()
    {
        subtitleNote.setText(already.getSubTitle());
        titleNote.setText(already.getTitle());
        mainNote.setText(already.getNoteMain());

        //NHAC NHO
        timeAndDateAlarm.setText(already.getEventDate()+"  "+already.getEventTime());
        if(timeAndDateAlarm.getText().toString().equals("null  null"))
        {
            timeAndDateAlarm.setText("");
            timeAndDateAlarm.setHint("Hẹn nhắc nhở");

        }
        labelContent.setText(already.getLabel());
        String time=new SimpleDateFormat("EEE, dd MMMM yyyy HH:mm a",Locale.getDefault()).format(new Date());
        dateNote.setText(time);
        selectedColorNote=already.getColor();
        colorLeftSubtitle.setColorFilter(Integer.parseInt(already.getColor()));
        pass=already.getPassword();

        if(already.getPinned()==1)
        {
            notePin.setImageResource(R.drawable.ic_pinred);
            clicked=false;
            resultPin=1;
        }
        else
        {
            notePin.setImageResource(R.drawable.ic_notpin);
            clicked=true;
        }

        if(already.getImagePath()!=null && !already.getImagePath().trim().isEmpty())
        {
            pictureInNote.setImageBitmap(BitmapFactory.decodeFile(already.getImagePath()));
            pictureInNote.setVisibility(View.VISIBLE);
            selectedPicturePath=already.getImagePath();
        }

        if(already.getVideoPath()!=null && !already.getVideoPath().trim().isEmpty())
        {
            videoView.setVideoURI(Uri.parse(already.getVideoPath()));
            videoView.setVisibility(View.VISIBLE);
            selectedVideoPath=already.getVideoPath();
            videoView.start();
        }
        //    NotesDatabase.getNotesDatabase(getApplicationContext()).daonote().insertNode(already);
        if(already.getAlarm()==1)
        {
            addAlarm.setImageResource(R.drawable.ic_alarm);
            clickeAlarm=false;
            resultAlarm=1;
        }
        else
        {
            addAlarm.setImageResource(R.drawable.notalarm);
            clickeAlarm=true;
        }

        if(already.getLock()==1)
        {
            iconLock.setImageResource(R.drawable.lockok);
            clickLock=false;
            resultLock=1;
            iconLock.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view)
                {
                    AlertDialog dialog=new AlertDialog.Builder(view.getContext())
                            .setTitle("Đổi mật khẩu")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i)
                                {
                                    AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                                    builder.setTitle("Đổi mật khẩu");
                                    builder.setIcon(R.drawable.key);

                                    passNew=new EditText(view.getContext());
                                    builder.setView(passNew);

                                    builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int which)
                                        {
                                            pass=passNew.getText().toString();
                                            already.setPassword(pass);
                                            Toast.makeText(view.getContext(),"Thay đổi mật khẩu thành công",Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                    builder.setNegativeButton("Hủy",null);
                                    AlertDialog ad=builder.create();
                                    ad.show();
                                }
                            })
                            .setNegativeButton("Cancel",null)//không đồng ý
                            .create();
                    dialog.show();
                    return false;
                }
            });
        }
        else
        {
            iconLock.setImageResource(R.drawable.lock);
            clickLock=true;
        }
    }


    public void openColorPicker()
    {
        AmbilWarnaDialog colorPicker=new AmbilWarnaDialog(this, mDeaufaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color)
            {
                mDeaufaultColor=color;
                colorLeftSubtitle.setColorFilter(color);
                selectedColorNote=String.valueOf(mDeaufaultColor);
                Toast.makeText(CreateNote.this, "Color is changed!", Toast.LENGTH_SHORT).show();
            }
        });
        colorPicker.show();

    }

    private void selectPicture()
    {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivityForResult(intent,REQUEST_CODE_SELECT_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUEST_CODE_STORAGE_PERMISSION && grantResults.length>0)
        {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                selectPicture();
            }
            else
            {
                Toast.makeText(this, "Permission Dennied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_SELECT_IMAGE && resultCode==RESULT_OK)
        {
            if(data!=null)
            {
                Uri selectedImageUri=data.getData();
                if(selectedImageUri!=null)
                {
                    try{
                        InputStream inputStream=getContentResolver().openInputStream(selectedImageUri);
                        Bitmap bitmap= BitmapFactory.decodeStream(inputStream);
                        pictureInNote.setImageBitmap(bitmap);
                        pictureInNote.setVisibility(View.VISIBLE);
                        selectedPicturePath=getPathFromUri(selectedImageUri);
                    }
                    catch (Exception exception)
                    {
                        Toast.makeText(this,exception.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
        if (requestCode == 1)
        {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                mainNote.setText(text.get(0));
            }
        }
        if(requestCode==REQUEST_CODE_LABEL && resultCode==Chooselabel.RESULT_OK)
        {
            namelabel=data.getStringExtra("listLabelChoosen");
            namelabel=namelabel.substring(0,namelabel.length()-3);
            labelContent.setText(namelabel);
            if(!labelContent.getText().toString().equals("N"))
                labelContent.setVisibility(View.VISIBLE);
            else
                labelContent.setVisibility(GONE);
        }
        if(requestCode==SELECT_VIDEO_REQUEST)
        {
            Uri videoUri=data.getData();
            videoView.setVisibility(View.VISIBLE);
            videoView.setVideoURI(videoUri);
            selectedVideoPath=getPathFromUri(videoUri);
            videoView.start();
        }
        if (requestCode == PICK_FILE && resultCode == RESULT_OK) {
            if (data != null) {
                Uri uri = data.getData();
                createMediaPlayer(uri);
            }
        }

    }
    public void createMediaPlayer(Uri uri){
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
        try {
            mediaPlayer.setDataSource(getApplicationContext(), uri);
            mediaPlayer.prepare();

            pathAudio=getNameFromUri(uri);
            play.setEnabled(true);

            int millis = mediaPlayer.getDuration();
            long total_secs = TimeUnit.SECONDS.convert(millis, TimeUnit.MILLISECONDS);
            long mins = TimeUnit.MINUTES.convert(total_secs, TimeUnit.SECONDS);
            long secs = total_secs - (mins*60);
            duration = mins + ":" + secs;
            timePlay.setText("00:00 / " + duration);
            seekbar.setMax(millis);
            seekbar.setProgress(0);

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    releaseMediaPlayer();
                }
            });
        } catch (IOException e){

        }
    }
    @SuppressLint("Range")
    public String getNameFromUri(Uri uri){
        String fileName = "";
        Cursor cursor = null;
        cursor = getContentResolver().query(uri, new String[]{
                MediaStore.Images.ImageColumns.DATA
        }, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
        }
        if (cursor != null) {
            cursor.close();
        }
        return fileName;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    public void releaseMediaPlayer(){
        if (timer != null) {
            timer.shutdown();
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        play.setEnabled(false);
        timePlay.setText("00:00 / 00:00");
        seekbar.setMax(100);
        seekbar.setProgress(0);
    }

    private String getPathFromUri(Uri uri)
    {
        String filePath;
        Cursor cursor=getContentResolver().query(uri,null,null,null,null);
        if(cursor==null)
        {
            filePath=uri.getPath();
        }
        else {
            cursor.moveToFirst();
            int index=cursor.getColumnIndex("_data");
            filePath=cursor.getString(index);
            cursor.close();
        }
        return filePath;
    }

    private void setAlarm(String text, String date, String time)
    {
        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent=new Intent(CreateNote.this,ReminderBroadcast.class);
        intent.putExtra("contentNoti", text);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String dateandtime = date + " " + timeChoose;
        DateFormat formatter = new SimpleDateFormat("d-M-yyyy hh:mm");
        try {
            Date date1 = formatter.parse(dateandtime);
            am.set(AlarmManager.RTC_WAKEUP, date1.getTime(), pendingIntent);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //Save note
    private void saveNote()
    {
        if(titleNote.getText().toString().equals(""))
        {
            Toast.makeText(this, "Bạn chưa nhập chủ đề ghi chú", Toast.LENGTH_SHORT).show();
            return;
        }
        if(mainNote.getText().toString().equals(""))
        {
            Toast.makeText(this, "Bạn chưa nhập nội dung ghi chú", Toast.LENGTH_SHORT).show();
            return;
        }

        final NoteManager note=new NoteManager();
        note.setTitle(titleNote.getText().toString());
        note.setSubTitle(subtitleNote.getText().toString());
        note.setDateTime(dateNote.getText().toString());
        note.setNoteMain(mainNote.getText().toString());
        note.setColor(selectedColorNote);
        note.setImagePath(selectedPicturePath);
        note.setPinned(resultPin);
        note.setAlarm(resultAlarm);
        note.setLock(resultLock);
        note.setPassword(pass);
        note.setVideoPath(selectedVideoPath);

        //Thong bao
        note.setEventDate(dateChoose);
        note.setEventTime(timeChoose);
        note.setMessNoti(mainNote.getText().toString());

        //label
        note.setLabel(namelabel);


        if(resultPin==1)
            notePin.setImageResource(R.drawable.ic_pinred);
        else
            notePin.setImageResource(R.drawable.ic_notpin);

        if(resultAlarm==1)
            addAlarm.setImageResource(R.drawable.ic_alarm);
        else
            addAlarm.setImageResource(R.drawable.notalarm);

        //PASSWORD
        if(resultLock==1)
            iconLock.setImageResource(R.drawable.lockok);
        else
            iconLock.setImageResource(R.drawable.lock);

        // FIREBASE
        DocumentReference documentReference=firebaseFirestore.collection("notes").document(firebaseUser.getUid()).collection("myNote").document();
        Map<String,Object> noteFire=new HashMap<>();
        noteFire.put("id",note.getId());
        noteFire.put("title",titleNote.getText().toString());
        noteFire.put("subtitle",subtitleNote.getText().toString());
        noteFire.put("dateNote",dateNote.getText().toString());
        noteFire.put("mainNote",mainNote.getText().toString());
        noteFire.put("color",selectedColorNote);
        noteFire.put("pathImg",selectedPicturePath);
        noteFire.put("pin",resultPin);
        noteFire.put("alarm",resultAlarm);
        noteFire.put("lock",resultLock);
        noteFire.put("passNote",pass);
        noteFire.put("pathVideo",selectedVideoPath);
        noteFire.put("dateAlarm",dateChoose);
        noteFire.put("timeAlarm",timeChoose);
        noteFire.put("messNoti",mainNote.getText().toString());
        noteFire.put("label",namelabel);
        documentReference.set(noteFire).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(CreateNote.this, "Tạo ghi chú thành công", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreateNote.this, "Tạo ghi chú lỗi", Toast.LENGTH_SHORT).show();
            }
        });


        if(already!=null)
        {
            note.setId(already.getId());
        }


        @SuppressLint("StaticFieldLeak")
        class saveNoteTask extends AsyncTask<Void,Void,Void>
        {
            @Override
            protected Void doInBackground(Void... voids)
            {
                NotesDatabase.getNotesDatabase(getApplicationContext()).daonote().insertNode(note);
                return null;
            }
            @Override
            protected void onPostExecute(Void avoid) {
                super.onPostExecute(avoid);
                Intent intent=new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        }
        new saveNoteTask().execute();
    }

}