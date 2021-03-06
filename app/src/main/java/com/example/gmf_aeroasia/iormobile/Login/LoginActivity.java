package com.example.gmf_aeroasia.iormobile.Login;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gmf_aeroasia.iormobile.MainActivity;
import com.example.gmf_aeroasia.iormobile.MySingleton2;
import com.example.gmf_aeroasia.iormobile.R;
import com.example.gmf_aeroasia.iormobile.ReportGuestActivity;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    private long backPres;
    private Toast backToast;
    EditText e_user,e_pass;
    ProgressDialog dialogLoading;
    SharedPreferences sharedP;
    SharedPreferences.Editor shareEdit;
    public static final String PREF = "Keypref";
    public static final String KEY_NAME = "name";
    public static final String KEY_ID = "id";
    public static final String KEY_UNIT = "unit";
    public static final String KEY_USER = "username";
    private static final int requestCode = 100;

    Context mContext;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activiry);

        e_user = (EditText) findViewById(R.id.e_username);
        e_pass = (EditText) findViewById(R.id.e_password);

        Button btn_login = (Button) findViewById(R.id.btn_login);
        Button btn_login_guest = (Button) findViewById(R.id.btn_login_guest);
        Log.d("LoginActivity", " user login : " + getprefname());

        if (getprefname().length() != 0) {
            Intent kliklangsung = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(kliklangsung);
        } else {

        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 233);


        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1123);

                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.CAMERA},23312);

                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1241);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

        ActivityCompat.requestPermissions(LoginActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        ActivityCompat.requestPermissions(LoginActivity.this,
                new String[]{Manifest.permission.CAMERA},2);

        ActivityCompat.requestPermissions(LoginActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},3);



//        if (checkSelfPermission(Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//            requestPermissions(new String[]{Manifest.permission.CAMERA},requestCode);
//        }


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = e_user.getText().toString();
                final String pass = e_pass.getText().toString();

                Log.d("LoginActivity", " user : " + user.toString());
                Log.d("LoginActivity", " pass : " + pass.toString());
                if (!validasi()) {
                    Toast.makeText(getApplicationContext(), "Login Gagal", Toast.LENGTH_SHORT).show();


                } else {
                    dialogLoading = ProgressDialog.show(LoginActivity.this, "",
                            "Loading. Please wait...", true);


                    String url = "http://"+getApplicationContext().getString(R.string.ip_default)+"/API_IOR/login.php";

                    Log.d("LoginActivity", " URL API : " + url.toString());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.contains("Login Failed")){
                                Toast.makeText(getApplicationContext(), " Login Gagal, Coba kembali !", Toast.LENGTH_SHORT).show();
                                dialogLoading.hide();
                            }else {

                                String data = new String(response);
                                String[] separated = data.split(",");
                                shareEdit.putString(KEY_ID, separated[0]);
                                shareEdit.putString(KEY_NAME, separated[1]);
                                shareEdit.putString(KEY_UNIT, separated[2]);
                                shareEdit.putString(KEY_USER, separated[3]);
                                shareEdit.apply();
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish();
                                Toast.makeText(getApplicationContext(), "Selamat Datang "+getprefname().toString(), Toast.LENGTH_SHORT).show();
//

                                dialogLoading.hide();

                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                Toast.makeText(getApplicationContext(),
                                        "Periksa Koneksi Anda dan Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof ServerError) {
                                Toast.makeText(getApplicationContext(),
                                        "Server Mengalami Masalah , Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof NetworkError) {
                                Toast.makeText(getApplicationContext(),
                                        "Jaringan Mengalami Masalah, Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                            } else if (error instanceof AuthFailureError) {
                                Toast.makeText(getApplicationContext(),
                                        "Username atau Password Tidak tersedia, Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Login Gagal , Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                            }
                            dialogLoading.hide();
                            error.printStackTrace();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> data = new HashMap<>();
                            data.put("username", user);
                            data.put("password", pass);
                            return data;
                        }
                    };
                    //MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            5000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    MySingleton2.getmInstance(getBaseContext()).addToRequestque(stringRequest);
                }

                sharedP = getSharedPreferences(PREF, Context.MODE_PRIVATE);
                shareEdit = sharedP.edit();

//                Toast.makeText(getApplicationContext(), "Login Berhasil", Toast.LENGTH_SHORT).show();
//                Intent i= new Intent(LoginActivity.this,MainActivity.class);
//                startActivity(i);
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
    public String getprefname(){
        sharedP = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        return sharedP.getString(KEY_NAME,"");

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

    public boolean validasi() {
        boolean valid = true;

        final String email = e_user.getText().toString();
        final String pass = e_pass.getText().toString();

        if (email.isEmpty()) {
            e_user.setError("Username Harus Terisi");
            valid = false;
        } else {
            e_user.setError(null);
        }

        if (pass.isEmpty()) {
            e_pass.setError("Password Harus Terisi");
            valid = false;
        } else {
            e_pass.setError(null);
        }
        return valid;

    }


//    @Override
//
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        if (requestCode == requestCode) {
//
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
//
//            } else {
//
//                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
//
//            }
//
//        }}//end onRequestPermissionsResult
}
