package com.lalbhaibrokers.lalbhaibrokerspvtltd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
public class LoginActivity extends AppCompatActivity {

    //Variables
    TextInputEditText mobileNumber, otp;
    TextView forgotPassword, errorMessage;
    Button login;
    Button sendOtp;
    Button verifyOtp;
    String verificationCode;
    boolean isVerified;

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
        sendOtp = (Button) findViewById(R.id.send_otp_btn);
        verifyOtp = (Button) findViewById(R.id.verify_otp);
        
        verifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userCode = otp.getText().toString();
                if (!userCode.isEmpty()) {
                    verifyCode(userCode); //verifying the code Entered by user
                }
            }
        });
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
    
    //method for start the OTP process
    public void sendOtp(View view){
        if(mobileNumber.getText().toString().equals("")){
            errorMessage.setText("Please Enter phone number");
            errorMessage.setVisibility(View.VISIBLE);
        }else if(mobileNumber.getText().toString().length()==10){
            errorMessage.setText("PhoneNumber is Invalid"); //we can only accept phoneNumbers with 10 digits
            errorMessage.setVisibility(View.VISIBLE);
        }else{
            String phoneNum= "+91"+mobileNumber.getText().toString(); //we have to add country code in order to receive OTP

            //method that will send the OTP to given number
            PhoneAuthProvider.getInstance().verifyPhoneNumber( //sending message
                    phoneNum,        // Phone number to verify
                    60,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    TaskExecutors.MAIN_THREAD,    // Activity (for callback binding)
                    mCallbacks);        // OnVerificationStateChangedCallbacks
            Toast.makeText(context, "OTP send to "+phoneNum, Toast.LENGTH_SHORT).show();
            verifyOtp.setVisibility(View.VISIBLE);
        }
    }
    
    //method that verify the OTP received or not
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationCode = s;   //verification code that should be received by phoneNumber
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode(); //verification code that actually received by phoneNumber
            if (code != null) {
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(context, "Verification Faild: OTP not recived", Toast.LENGTH_SHORT).show();
        }
    };
    
    //verifying the OTP
    public void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, code); //comparing both verification code
        signin(credential);
    }

    //signing in the User to update in database
    private void signin(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Verification Complete", Toast.LENGTH_SHORT).show();
                    isVerified=true;
                    Intent intent = new Intent(context, UserDashboard.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(context, "Verification Faild: OTP wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
