package com.proudcoder.firebase_login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;

import android.telephony.SmsManager;

import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    private TextView textView;
    private String stringNumber = "+916379117963";//mynumber



 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS,
                        Manifest.permission.SEND_SMS},
                PackageManager.PERMISSION_GRANTED);
        textView = findViewById(R.id.textView);


    }
    public void buttonForward(View view){
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms"), null,null,null,null);
        cursor.moveToFirst();
        while (cursor!=null){
            String stringMessage = cursor.getString(12);
            if (stringMessage.contains("Taser")){

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        Intent intent = new Intent(MainActivity2.this,Noise.class);
                        startActivity(intent);

                    }
                },3000);
                SmsManager smsManagerSend = SmsManager.getDefault();
                smsManagerSend.sendTextMessage(stringNumber, null, stringMessage, null, null);
                textView.setText("Alert Message Sent to Saved Contact Number!");
                Toast.makeText(this,"Alert Message Sent to Saved Contact Number!",Toast.LENGTH_SHORT).show();;
                break;
            }
            textView.setText("Message NOT found");
            cursor.moveToNext();
        }
    }

public void Logout(View view){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(MainActivity2.this,"SignedOut",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity2.this,MainActivity.class);
        startActivity(intent);
}


public void ibutton(View view){

    Intent intent = new Intent(MainActivity2.this,aboutus.class);
    startActivity(intent);

}
public void Uploadactivity(View view){
    Intent intent = new Intent(MainActivity2.this,uploadtofirebase.class);
    startActivity(intent);

}
public void contactdetails(View view){
    Intent intent = new Intent(MainActivity2.this,SQLactivity.class);
    startActivity(intent);

}
    public void Geofencemainscreen(View view){
        Intent intent = new Intent(MainActivity2.this,MapsActivity.class);
        startActivity(intent);

    }
 public void checks(View view){
     Intent intent = new Intent();
     intent.setClassName("com.example.zyandeep.simplesosapp", "com.example.zyandeep.Neerja.MainActivity");
     startActivity(intent);
//     Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.example.zyandeep.simplesosapp");
//     if (launchIntent != null) {
//         startActivity(launchIntent);//null pointer check in case package name was not found
//     }
 }

    }



