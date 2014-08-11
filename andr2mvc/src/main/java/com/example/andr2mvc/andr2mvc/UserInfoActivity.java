package com.example.andr2mvc.andr2mvc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class UserInfoActivity extends Activity {

    private EditText txtNickname;
    private EditText txtEmail;
    private EditText txtPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_info);

       Button mSaveCloseButton = (Button) findViewById(R.id.buttonSaveClose);

       Intent intent=getIntent();

       String nickname=intent.getStringExtra("nickname");

       txtNickname = (EditText) findViewById(R.id.editTextNickname);

       txtNickname.setText(nickname);

       String email=intent.getStringExtra("email");

       txtEmail = (EditText) findViewById(R.id.editTextEmail);

       txtEmail.setText(email);

       String phone=intent.getStringExtra("phone");

       txtPhone = (EditText) findViewById(R.id.editTextPhone);

       txtPhone.setText(phone);


       mSaveCloseButton.setOnClickListener(new View.OnClickListener() {






            @Override
            public void onClick(View view) {
                //attemptLogin();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
