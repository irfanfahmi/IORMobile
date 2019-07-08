package com.example.gmf_aeroasia.iormobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.example.gmf_aeroasia.iormobile.adapter.Ior_follow_adapter;
import com.example.gmf_aeroasia.iormobile.model.occ_follow;
import com.example.gmf_aeroasia.iormobile.service.MySingleton;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommentActivity extends AppCompatActivity {

    private RecyclerView rview ;
    private Toolbar toolbar;
    private EditText et_desc_follow;
    ImageButton btnSend;
    SharedPreferences sharedP;
    SharedPreferences.Editor shareEdit;
    final String PREF = "Keypref";
    final String KEY_NAME = "name";
    final String KEY_ID = "id";
    final String KEY_UNIT = "unit";
    final String KEY_USER = "username";
    String occId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        occId  = getIntent().getExtras().getString("occ_id");

        toolbar = (Toolbar) findViewById(R.id.toolbarid);
        et_desc_follow = (EditText) findViewById(R.id.edittext_follow);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

//getting the recyclerview from xml
        rview = findViewById(R.id.recylcerViewcoment);
        rview.setHasFixedSize(true);

        final LinearLayoutManager manager = new LinearLayoutManager(this);
        rview.setLayoutManager(manager);
        sharedP =   getSharedPreferences(PREF, Context.MODE_PRIVATE);
        shareEdit = sharedP.edit();

        String url = "http://"+getApplicationContext().getString(R.string.ip_default)+"/API_IOR/tampil_ior_occ_follow.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.contains("Tidak Ada Data")){
                    Log.d("CommentActivity", "onResponse: "+response);
                    Toast.makeText(getApplicationContext(), "Data Tidak Ada", Toast.LENGTH_SHORT).show();

                }else {
                ArrayList<occ_follow> occ_follow_list = new JsonConverter<occ_follow>().toArrayList(response, occ_follow.class);
                Ior_follow_adapter adapter = new Ior_follow_adapter(getApplicationContext(), occ_follow_list);
                rview.setLayoutManager(manager);
                rview.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(),
                            "Koneksi Timeout , Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(),
                            "Server Mengalami Masalah , Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(),
                            "Jaringan Mengalami Masalah, Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Gagal , Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                }

                error.printStackTrace();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("occ_id", occId);
                return hashMap;
            }


        }
                ;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


        btnSend = (ImageButton)findViewById(R.id.send_follow);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("CLICKED COMMENT");
                submitComment();
            }
        });

    }

    public String getprefuser(){
        sharedP = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        return sharedP.getString(KEY_USER,"");
    }
    public String getprefname(){
        sharedP = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        return sharedP.getString(KEY_NAME,"");

    }
    public String getprefunit(){
        sharedP = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        return sharedP.getString(KEY_UNIT,"");

    }

    public void submitComment() {



        final String comment = et_desc_follow.getText().toString();
        final String follow_by = getprefuser();
        final String follow_by_name = getprefname();
        final String follow_by_unit = getprefunit();
        final String commentTimestamp = String.valueOf(System.currentTimeMillis() / 1000);

        Log.d("CommentActivity", "submitComment: CEK ID "+occId);
        Log.d("CommentActivity", "submitComment: CEK followed By Name"+getprefname());
        Log.d("CommentActivity", "submitComment: CEK followed By Unit "+getprefunit());
        Log.d("CommentActivity", "submitComment: CEK followed By Date "+commentTimestamp);

        if (comment != null && !comment.equals("")) {

            String url = "http://"+getApplicationContext().getString(R.string.ip_default)+"/API_IOR/input_ior_occ_follow.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    et_desc_follow.setText("");
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                        Toast.makeText(getApplicationContext(),
                                "Koneksi Timeout , Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(getApplicationContext(),
                                "Server Mengalami Masalah , Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof NetworkError) {
                        Toast.makeText(getApplicationContext(),
                                "Jaringan Mengalami Masalah, Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),
                                "Gagal , Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                    }

                    error.printStackTrace();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("occ_id", occId);
                    hashMap.put("follow_desc", comment);
                    hashMap.put("follow_by", follow_by);
                    hashMap.put("follow_date", commentTimestamp);
                    hashMap.put("follow_by_name", follow_by_name);
                    hashMap.put("follow_by_unit", follow_by_unit);
                    return hashMap;
                }


            }
                    ;
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);

            finish();
            startActivity(getIntent());



        }

    }
}
