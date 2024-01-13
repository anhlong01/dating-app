package com.quintus.labs.datingapp.Login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.quintus.labs.datingapp.Main.MainActivity;
import com.quintus.labs.datingapp.R;

import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class ForgotPassword extends AppCompatActivity {
    private static final String TAG = "ForgotPasswordActivity";
    private Context mContext;
    private String email;
    private EditText mEmail;
    private TextView loadingPleaseWait;
    private Button btnSendEmail;

    private FirebaseAuth mAuth;


    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mContext = ForgotPassword.this;
        Log.d(TAG, "onCreate: started");

        btnSendEmail = findViewById(R.id.btn_send_email_forgot_pass);
        mEmail = findViewById(R.id.input_email_forgot_pass);

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = mEmail.getText().toString();
                if (checkInputs(email)) {
                    sendEmail(email);

                }
            }
        });
    }

    private void sendEmail(String email) {
        mAuth = FirebaseAuth.getInstance();

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mContext, "Email sent.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ForgotPassword.this, Login.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(mContext, "Email is not registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    private boolean checkInputs(String email) {
        Log.d(TAG, "checkInputs: checking inputs for null values.");
        if (email.equals("")) {
            Toast.makeText(mContext, "Các ô không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Below code checks if the email id is valid or not.
        if (!email.matches(emailPattern)) {
            Toast.makeText(getApplicationContext(), "Định dạng email không hợp lệ.", Toast.LENGTH_SHORT).show();
            return false;

        }
        return true;
    }
}
