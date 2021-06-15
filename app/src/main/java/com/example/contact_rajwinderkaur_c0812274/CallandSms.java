package com.example.contact_rajwinderkaur_c0812274;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CallandSms extends AppCompatActivity {

    private  static  final  int REQUEST_CODE_CALL = 1;
    public static final int REQUEST_CODE_SMS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calland_sms);

        TextView Name = findViewById(R.id.idTVName);
        TextView phone = findViewById(R.id.Phone);
        ImageView imageView = findViewById(R.id.Call);
        ImageView imgeView1 = findViewById(R.id.idIVMessage);


        Intent i = getIntent();
        String name = i.getStringExtra("Name");
        String Phone = i.getStringExtra("PhoneNumber");
        Name.setText(name);
        phone.setText(Phone);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = phone.getText().toString();
                Intent i = new Intent(Intent.ACTION_CALL,Uri.parse("phonenumber"+number));
                startActivity(i);

            }
        });
        imgeView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = phone.getText().toString();
                Intent i = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+ number));
                i.putExtra("Sms body","hello");
                startActivity(i);
            }
        });
    }
}






