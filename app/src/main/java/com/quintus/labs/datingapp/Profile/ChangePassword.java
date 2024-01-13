package com.quintus.labs.datingapp.Profile;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.quintus.labs.datingapp.Login.ForgotPassword;
import com.quintus.labs.datingapp.Login.Login;
import com.quintus.labs.datingapp.R;

import java.util.Objects;

public class ChangePassword extends AppCompatActivity {

    private static final String TAG = "ChangePasswordActivity";
    private Context mContext;
    private String passwordNew, passwordNew2;
    private EditText mPasswordNew, mPasswordNew2;
    private TextView loadingPleaseWait;
    private TextView changePasswordConfirmBtn;
    private FirebaseUser mUser;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mContext = ChangePassword.this;
        Log.d(TAG, "onCreate: started");


        mPasswordNew = findViewById(R.id.input_password_new);
        mPasswordNew2 = findViewById(R.id.reinput_password_new);
        changePasswordConfirmBtn = findViewById(R.id.btn_confirm_change_password);


        changePasswordConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPassword()){
                    changePassword(passwordNew);
                }
            }
        });
    }

    private boolean checkPassword() {
        passwordNew = mPasswordNew.getText().toString().trim();
        passwordNew2 = mPasswordNew2.getText().toString().trim();
        if (Objects.equals(passwordNew, passwordNew2)) {
            return true;
        } else {
            Toast.makeText(mContext, "Mật khẩu nhập lại không khớp.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private void changePassword(String newPassword) {
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        mUser.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(mContext, "Mật khẩu người dùng update thành công.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ChangePassword.this, SettingsActivity.class);
                            startActivity(intent);
                        } else {
                            //Show dialog re-Authenticate
                            showReAuthenticate();
                        }
                    }
                });
    }

    private void showReAuthenticate() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Sử dụng LayoutInflater để inflate giao diện từ tệp XML
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_re_authenticate, null);

        // Lấy các EditText từ giao diện của hộp thoại
        final EditText mEmail = dialogView.findViewById(R.id.input_reAuthen_email);
        final EditText mPassword = dialogView.findViewById(R.id.input_reAuthen_password);

        // Đặt giao diện của hộp thoại
        builder.setView(dialogView)
                .setTitle("Xác nhận tài khoản")
                .setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when OK button is clicked
                        String email = mEmail.getText().toString();
                        String password = mPassword.getText().toString();

                        if (checkInputs(email, password)) {
                            reAuthenticate(email, password);
                            // Đóng hộp thoại
                            dialog.dismiss();
                        }

                    }
                })
                .setNegativeButton("Trở lại", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something when Cancel button is clicked
                        dialog.dismiss();
                    }
                });
        // Hiển thị hộp thoại
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void reAuthenticate(String email, String password) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
        // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, password);

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(mContext, "Xác thực người dùng thành công.", Toast.LENGTH_SHORT).show();
                            changePassword(passwordNew);
                        } else {
                            Toast.makeText(mContext, "Tài khoản hoặc mật khẩu xác thực không chính xác. Vui lòng nhập lại..", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private boolean checkInputs(String email, String password) {
        Log.d(TAG, "checkInputs: checking inputs for null values.");
        if (email.equals("") || password.equals("")) {
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
