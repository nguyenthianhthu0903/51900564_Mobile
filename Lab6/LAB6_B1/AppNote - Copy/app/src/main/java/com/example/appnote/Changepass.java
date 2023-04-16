package com.example.appnote;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Changepass extends Fragment {

    EditText oldpass,newpass,confirmpass;
    Button changepass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.changepass,container,false);

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();

        newpass=view.findViewById(R.id.newpass);
        confirmpass=view.findViewById(R.id.confirmpass);

        changepass=view.findViewById(R.id.btnChangepass);
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(newpass.getText().toString().isEmpty() || confirmpass.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(), "Nhập mật đày đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!newpass.getText().toString().equals(confirmpass.getText().toString()))
                {
                    Toast.makeText(getActivity(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(newpass.getText().toString().equals(confirmpass.getText().toString()))
                {
                    user.updatePassword(newpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


        return view;
    }
}