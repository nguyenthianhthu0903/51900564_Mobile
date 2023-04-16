package com.example.appnote;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity
{
    EditText emaillogin,passwordlogin;
    MaterialButton btnLogin;
    private TextView forgotpass,others;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);

        emaillogin=findViewById(R.id.emailuserlogin);
        passwordlogin=findViewById(R.id.passworduserlogin);
        btnLogin=findViewById(R.id.btnLogin);
        others=findViewById(R.id.others);
        others.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Login.this,Signup.class);
                startActivity(intent);
            }
        });
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        if(firebaseUser!=null)
        {
            finish();
            startActivity(new Intent(getApplicationContext(),Activity.class));
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emaillogin.getText().toString();
                String pass=passwordlogin.getText().toString();

                if(email.isEmpty()||pass.isEmpty())
                {
                    Toast.makeText(Login.this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                checkMail();
                            }
                            else
                            {
                                Toast.makeText(Login.this, "Tài khoản chưa được đăng ký", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }
    public void checkMail()
    {
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser.isEmailVerified()==true)
        {
            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(getApplicationContext(),HomeFragment.class));
        }
        else
        {
            Toast.makeText(this, "Vui lòng xác minh tài khoản", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }
}
