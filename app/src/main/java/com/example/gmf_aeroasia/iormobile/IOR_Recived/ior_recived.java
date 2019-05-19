package com.example.gmf_aeroasia.iormobile.IOR_Recived;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.gmf_aeroasia.iormobile.R;
import com.example.gmf_aeroasia.iormobile.adapter.Ior_Recived_Adapter;
import com.example.gmf_aeroasia.iormobile.model.occ;
import com.example.gmf_aeroasia.iormobile.service.MySingleton;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;

public class ior_recived extends AppCompatActivity {
    private RecyclerView rview ;
    private Toolbar toolbar;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ior_recived);

        toolbar = (Toolbar) findViewById(R.id.toolbarid);
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
        rview = findViewById(R.id.recylcerViewr);
        rview.setHasFixedSize(true);

        final LinearLayoutManager manager = new LinearLayoutManager(this);
        rview.setLayoutManager(manager);

        String url = "http://"+getApplicationContext().getString(R.string.ip_default)+"/API_IOR/tampil_ior_recived.php";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<occ> occlist = new JsonConverter<occ>().toArrayList(response, occ.class);
//                Toast toast = Toast.makeText(getApplicationContext(), kegiatanpemerintahlist.toString(), Toast.LENGTH_LONG);
//                toast.show();
                Ior_Recived_Adapter adapter = new Ior_Recived_Adapter(getApplicationContext(), occlist);
                rview.setLayoutManager(manager);
                rview.setAdapter(adapter);
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
        });

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);



    }
}
