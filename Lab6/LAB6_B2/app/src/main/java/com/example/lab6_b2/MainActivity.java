package com.example.lab6_b2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
    EditText content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        content=findViewById(R.id.view);
    }

    public void writeIn(View view)
    {
        File myFile=new File(getFilesDir(),"myFile.txt");
        try {
            PrintWriter writer=new PrintWriter(myFile);
            writer.println(content.getText().toString());
            writer.close();
        }catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void readIn(View view) {
        try {
            FileInputStream fis=openFileInput("myFile.txt");
            BufferedReader br=new BufferedReader(new InputStreamReader(fis));
            String line=br.readLine();
            content.setText(line);
            fis.close();
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeEx(View view)
    {
        File myFile=new File(getExternalFilesDir(null),"myExternal.txt");
        try {
            PrintWriter writer=new PrintWriter(myFile);
            writer.println(content.getText().toString()+myFile.getAbsolutePath());
            writer.close();
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void readEx(View view) {
        File myFile=new File(getExternalFilesDir(null),"myExternal.txt");
        try {
            BufferedReader reader=new BufferedReader(new FileReader(myFile));
            String line=reader.readLine();
            content.setText(line);
            reader.close();
        }catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}