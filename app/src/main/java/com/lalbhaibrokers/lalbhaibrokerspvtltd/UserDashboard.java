package com.lalbhaibrokers.lalbhaibrokerspvtltd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class UserDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);
    }

//    when profile button is pressed
//    public void userProfileMethod (View view) {
//        Toast.makeText(getApplicationContext(), "User profile", Toast.LENGTH_SHORT).show();
//
//        Intent profileIntent = new Intent(getApplicationContext(), ProfileAndSettings.class);
//        startActivity(profileIntent);
//    }

//    when daily bill card is pressed
    public void dailyBillMethod (View view) {
        Toast.makeText(getApplicationContext(), "Daily Bill", Toast.LENGTH_SHORT).show();

        Intent dailyBillIntent = new Intent(getApplicationContext(), DailyBill.class);
        startActivity(dailyBillIntent);
    }

//    when quarterly invoice card is pressed
    public void quarterlyInvoiceMethod (View view) {
        Toast.makeText(getApplicationContext(), "Quarterly Invoice", Toast.LENGTH_SHORT).show();

        Intent quarterlyInvoiceIntent = new Intent(getApplicationContext(), QuarterlyInvoice.class);
        startActivity(quarterlyInvoiceIntent);
    }

}