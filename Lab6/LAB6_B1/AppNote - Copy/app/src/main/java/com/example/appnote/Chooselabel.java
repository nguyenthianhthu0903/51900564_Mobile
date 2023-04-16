package com.example.appnote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.appnote.database.LabelDatabase;

import java.util.ArrayList;
import java.util.List;

public class Chooselabel extends AppCompatActivity {
    private ListView listView;
    private List<LabelItem> listItemLabel;
    String labelNamechoosen="";
    private LabelAdapterChoose adapter;
    int checkback=0;
    ArrayList<String>check=new ArrayList<>();

    private EditText inputLabel;
    private ImageView iconDoneLabel,refresh,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooselabel);

        inputLabel=findViewById(R.id.inputLabel);
        listView=findViewById(R.id.listViewLabel);

        iconDoneLabel=findViewById(R.id.iconDoneLabel);
        iconDoneLabel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addLabel();
            }
        });

        listItemLabel= LabelDatabase.getInstance(Chooselabel.this).labelDao().getListLabel();
        adapter=new LabelAdapterChoose(Chooselabel.this, android.R.layout.simple_list_item_multiple_choice,listItemLabel);
        Intent intent = getIntent();
        final String displayLabel=intent.getStringExtra("blackpink");
        String[] s=displayLabel.split(" - ");
        for(int i=0;i<s.length;i++)
        {
            check.add(s[i]);
        }
        for(int i=0;i<listItemLabel.size();i++)
        {
            if(check.contains(listItemLabel.get(i).getLabelName())==true)
            {
                listItemLabel.get(i).setCheckLabel(true);
            }
        }
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

        back=findViewById(R.id.icBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                for(int i=0;i<listItemLabel.size();i++)
                {
                    if(listItemLabel.get(i).checkLabel==true) {
                        labelNamechoosen = listItemLabel.get(i).getLabelName() + " - " + labelNamechoosen;
                    }
                }
                Intent intent = new Intent(Chooselabel.this, CreateNote.class);
                if(labelNamechoosen.length()!=0)
                    intent.putExtra("listLabelChoosen",labelNamechoosen);
                else
                    intent.putExtra("listLabelChoosen","NULL");
                setResult(RESULT_OK,intent);
                finish();


            }
        });
    }
    public void addLabel()
    {
        String newlable=inputLabel.getText().toString().trim();
        if(TextUtils.isEmpty(newlable))
        {
            Toast.makeText(Chooselabel.this,"Nhập tên nhãn",Toast.LENGTH_SHORT).show();
            return;
        }
        LabelItem newlabel=new LabelItem(newlable,false);

        LabelDatabase.getInstance(Chooselabel.this).labelDao().insertLabel(newlabel);
        Toast.makeText(Chooselabel.this,"Thành công",Toast.LENGTH_SHORT).show();

        inputLabel.setText("");
        listItemLabel=LabelDatabase.getInstance(Chooselabel.this).labelDao().getListLabel();
        adapter=new LabelAdapterChoose(Chooselabel.this, android.R.layout.simple_list_item_checked,listItemLabel);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

    }

}