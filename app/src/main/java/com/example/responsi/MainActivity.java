package com.example.responsi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.responsi.model.User;

public class MainActivity extends AppCompatActivity {
    Button inputloginbtn,inputregisterbtn;
    EditText inputemail,inputpassword;
    Spinner inputspinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputemail=(EditText) findViewById(R.id.email);
        inputpassword=(EditText) findViewById(R.id.password);
        inputspinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.usertipe, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        inputspinner.setAdapter(adapter);
        inputloginbtn =(Button) findViewById(R.id.loginbtn);
        inputloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputemail.getText().toString();
                String password = inputpassword.getText().toString();
                String item = inputspinner.getSelectedItem().toString();
                if (email.equals("admin@gmail.com")&& password.equals("admin909")&& item.equals("admin")) {
                    Intent intent = new Intent(MainActivity.this, Admin.class);
                    startActivity(intent);
                }else if (email.equals("user@gmail.com")&& password.equals("user909")&& item.equals("user")){
                    Intent intent = new Intent(MainActivity.this, Pengguna.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Gagal Login",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}