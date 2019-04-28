package com.example.gmf_aeroasia.iormobile.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.gmf_aeroasia.iormobile.MainActivity;
import com.example.gmf_aeroasia.iormobile.R;
import com.example.gmf_aeroasia.iormobile.ReportGuestActivity;


public class LoginActivity extends AppCompatActivity {
    private long backPres;
    private Toast backToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activiry);

        Button btn_login = (Button) findViewById(R.id.btn_login);
        Button btn_login_guest = (Button) findViewById(R.id.btn_login_guest);


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
                Intent i= new Intent(LoginActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

        btn_login_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Login Guest Berhasil", Toast.LENGTH_SHORT).show();

                Intent i= new Intent(LoginActivity.this,ReportGuestActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {


        if(backPres + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.finish();
            return;
        }else {
            backToast =  Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPres = System.currentTimeMillis();
    }
}
