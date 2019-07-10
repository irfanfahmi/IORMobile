package com.example.gmf_aeroasia.iormobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.example.gmf_aeroasia.iormobile.IOR_Non.ior_non;
import com.example.gmf_aeroasia.iormobile.IOR_Recived.ior_recived;
import com.example.gmf_aeroasia.iormobile.IOR_Send.ior_send;
import com.example.gmf_aeroasia.iormobile.Login.LoginActivity;
import com.example.gmf_aeroasia.iormobile.adapter.Ior_History_Adapter;
import com.example.gmf_aeroasia.iormobile.create_laporan.CreateIORActivity;
import com.example.gmf_aeroasia.iormobile.model.occ;
import com.example.gmf_aeroasia.iormobile.profil_pegawai.ProfilActivity;
import com.example.gmf_aeroasia.iormobile.service.MySingleton;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;
    Button btn_IOR_Recived;
    TextView nama_pegawai,nv_nama,nv_user,nv_unit;
    SharedPreferences sharedP;
    SharedPreferences.Editor shareEdit;
    final String PREF = "Keypref";
    final String KEY_NAME = "name";
    final String KEY_ID = "id";
    final String KEY_UNIT = "unit";
    final String KEY_USER = "username";
    private RecyclerView rview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);
        drawerToggle = setupDrawerToggle();
        toolbar = (Toolbar) findViewById(R.id.toolbarid);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav);
        View headerView = navigationView.getHeaderView(0);


        nama_pegawai = (TextView)findViewById(R.id.nama_pegawai);
        nv_nama = (TextView)headerView.findViewById(R.id.nav_nm_pegawai);
        nv_user = (TextView)headerView.findViewById(R.id.nav_user_pegawai);
        nv_unit = (TextView)headerView.findViewById(R.id.nav_unit_pegawai);
        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_IOR_Recived = (Button) findViewById(R.id.btn_ior_recived);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        btn_IOR_Recived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(MainActivity.this,ior_recived.class);
                startActivity(i);
            }
        });

        sharedP =   getSharedPreferences(PREF, Context.MODE_PRIVATE);
        shareEdit = sharedP.edit();

        nama_pegawai.setText(getprefname().toString());
        nv_nama.setText(getprefname().toString());
        nv_user.setText(getprefuser().toString());
        nv_unit.setText(getprefunit().toString());

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Click action
                Intent intent = new Intent(MainActivity.this, CreateIORActivity.class);
                startActivity(intent);
            }
        });

        rview = findViewById(R.id.recylcerViewh);
        rview.setHasFixedSize(true);
        final TextView tvNoData = findViewById(R.id.no_data);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        rview.setLayoutManager(manager);

        sharedP =   getSharedPreferences(PREF, Context.MODE_PRIVATE);
        shareEdit = sharedP.edit();

        final String name = getprefname();
        String url = "http://"+getApplicationContext().getString(R.string.ip_default)+"/API_IOR/tampil_history.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<occ> occlist = new JsonConverter<occ>().toArrayList(response, occ.class);
                Log.d("ior_history", "response : "+response);

                if (response.equalsIgnoreCase("")){
                    tvNoData.setVisibility(View.VISIBLE);
                    rview.setVisibility(View.GONE);

                }else {

                    tvNoData.setVisibility(View.GONE);
                    rview.setVisibility(View.VISIBLE);
                }
                Ior_History_Adapter adapter = new Ior_History_Adapter(getApplicationContext(), occlist);
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
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("name", name);
                return hashMap;
            }


        }
                ;
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


//
    }

    public String getprefname(){
        sharedP = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        return sharedP.getString(KEY_NAME,"");

    }
    public String getprefuser(){
        sharedP = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        return sharedP.getString(KEY_USER,"");

    }
    public String getprefunit(){
        sharedP = getSharedPreferences(PREF, Context.MODE_PRIVATE);
        return sharedP.getString(KEY_UNIT,"");

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:

                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the togg le state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.open,  R.string.close);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }

    public void kliklogout(MenuItem item) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Apakah Anda Yakin ?")
                    .setNegativeButton("No",null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            shareEdit.clear();
                            shareEdit.commit();

                            // After logout redirect user to Loing Activity
                            Intent in = new Intent(MainActivity.this, LoginActivity.class);
                            // Closing all the Activities
                            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                            // Add new Flag to start new Activity
                            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                            // Staring Login Activity
                            MainActivity.this.startActivity(in);
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
    }

    public void klikprofil(View view) {
        Intent intent = new Intent(MainActivity.this, ProfilActivity.class);
        startActivity(intent);
    }

    public void klikIORSEND(View view) {
        Intent intent = new Intent(MainActivity.this, ior_send.class);
        startActivity(intent);
    }

    public void klikIORNON(View view) {
        Intent intent = new Intent(MainActivity.this, ior_non.class);
        startActivity(intent);
    }
}
