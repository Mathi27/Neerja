package com.proudcoder.firebase_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
      private EditText Email;
      private EditText Password;
//      private Button Register;
//      private Button Login;
//      private Button Logout;
      private FirebaseAuth mAuth;
//      Make sure to check email and password are Not Empty OR Null
   
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Toast.makeText(MainActivity.this,"Aldready Loged In",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);


        mAuth = FirebaseAuth.getInstance();

    }

    public void onRegister(View view){
        final String myEmail = Email.getText().toString();
        final String mypassword = Password.getText().toString();
        mAuth.createUserWithEmailAndPassword(myEmail, mypassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("TAG", "createUserWithEmail:success");
                            Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_SHORT).show();
//                            FirebaseUser user = mAuth.getCurrentUser();
                                                            

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this,"Failure",Toast.LENGTH_SHORT).show();
//                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
//                            Toast.makeText(MainActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();

                        }
                    }

                        // ...
                });

    }
    public void onLogin(View view){
        final String myEmail = Email.getText().toString();
        final String mypassword = Password.getText().toString();
        mAuth.signInWithEmailAndPassword(myEmail, mypassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                          // Sign in success, update UI with the signed-in user's information
                            Log.i("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(MainActivity.this,"Auth Success",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,MainActivity2.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.i("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this,"Auth Failed",Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

}