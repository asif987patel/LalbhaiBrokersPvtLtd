package com.lalbhaibrokers.lalbhaibrokerspvtltd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    //Variables
    TextInputEditText mobileNumber, otp;
    TextView forgotPassword, errorMessage;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Hooks
        mobileNumber = (TextInputEditText) findViewById(R.id.mobile_no_editText);
        otp = (TextInputEditText) findViewById(R.id.otp_editText);
        forgotPassword = (TextView) findViewById(R.id.forgot_password_textView);
        errorMessage = (TextView) findViewById(R.id.error_message_textView);
        login = (Button) findViewById(R.id.login_button);
    }

    //method for login button
    public void loginMethod(View view) {
        if(mobileNumber.getText().toString().equals("") || otp.getText().toString().equals("")) {
            errorMessage.setText(R.string.no_info_provided);
            errorMessage.setVisibility(View.VISIBLE);
        }
        else {
            errorMessage.setVisibility(View.INVISIBLE);
            Toast.makeText(LoginActivity.this, mobileNumber.getText().toString(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, UserDashboard.class);
            startActivity(intent);
            finish();
        }
    }

    //method for forget password
    public void forgotPasswordMethod(View view) {
        Toast.makeText(LoginActivity.this, "Forgot password", Toast.LENGTH_SHORT).show();
    }
}