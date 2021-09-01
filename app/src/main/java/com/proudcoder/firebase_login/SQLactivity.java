package com.proudcoder.firebase_login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SQLactivity extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText editTextid,editName,editEmail,editmobile;
    Button buttonAdd,buttonUpdate,buttonDelete,buttonViewAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_q_lactivity);
        myDB = new DatabaseHelper(this);
        editTextid = findViewById(R.id.editText_id);
        editName = findViewById(R.id.editText_name);
        editEmail = findViewById(R.id.editText_email);
        editmobile = findViewById(R.id.editText_Mobilenumber);

        buttonAdd = findViewById(R.id.savebutton);
        buttonUpdate = findViewById(R.id.updatebutton);
        buttonDelete = findViewById(R.id.deletebutton);
        buttonViewAll = findViewById(R.id.viewbutton);
     AddData();
     updateData();
     viewAll();
     deleteData();
    }
    public void AddData(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInserted = myDB.insertData(editName.getText().toString(),
                        editEmail.getText().toString(),
                        editmobile.getText().toString());

                if(isInserted == true){
                    showMessage("Neerja ","Contact Saved");
                }else {
                    showMessage("Error!","Something Went Wrong!");
                }
            }
        });
    }
    public void updateData(){
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isUpdate = myDB.updateData(editTextid.getText().toString(),
                        editName.getText().toString(),
                        editEmail.getText().toString(),
                        editmobile.getText().toString());


                if(isUpdate==true){
                    showMessage("Neerja","Note Updated!");
                }else {
                    showMessage("Error!","Something Went Wrong!");
                }
            }
        });

    }
    public void viewAll(){
        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = myDB.getAllData();
//                Small Test
                if(cursor.getCount()==0){
                    showMessage("Error","Nothing Found In DataBase!");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (cursor.moveToNext()){
                    buffer.append("Priority : "+cursor.getString(0)+"\n");
                    buffer.append("Name: "+cursor.getString(1)+"\n");
                    buffer.append("Mobile: "+cursor.getString(2)+"\n");
                    buffer.append("Note: "+cursor.getString(3)+"\n\n");
                }
                showMessage("Saved Notes!",buffer.toString());
            }
        });

    }
    public void deleteData(){
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deletedRow = myDB.deleteData(editTextid.getText().toString());
                if(deletedRow.equals(String.valueOf(""))){
                    editTextid.setError("Enter Id");
                    return;
                }

                if(deletedRow>0){
                    showMessage("Alert!","Note Deleted");
                }else {
                    showMessage("Error","Something Went Wrong!");
                }
            }
        });
    }

    private void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void backbutton(View view){
        Intent intent = new Intent(SQLactivity.this,MainActivity2.class);
        startActivity(intent);
    }
}