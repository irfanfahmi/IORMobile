package com.example.gmf_aeroasia.iormobile.IOR_Send;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
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
import com.example.gmf_aeroasia.iormobile.R;
import com.example.gmf_aeroasia.iormobile.adapter.Ior_Send_Adapter;
import com.example.gmf_aeroasia.iormobile.model.occ;
import com.example.gmf_aeroasia.iormobile.service.MySingleton;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ior_send extends AppCompatActivity implements SearchView.OnQueryTextListener,SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView rview ;
    private Toolbar toolbar;
    String textSearch = null;
    SharedPreferences sharedP;
    SharedPreferences.Editor shareEdit;
    final String PREF = "Keypref";
    final String KEY_NAME = "name";
    final String KEY_ID = "id";
    final String KEY_UNIT = "unit";
    final String KEY_USER = "username";
    private SwipeRefreshLayout mSwipeRefreshLayout;
    TextView tvNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ior_send);


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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);

        mSwipeRefreshLayout = findViewById(R.id.swiperefresh_items);
        // Mengeset properti warna yang berputar pada SwipeRefreshLayout
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.primaryColor);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Handler untuk menjalankan jeda selama 5 detik
                new Handler().postDelayed(new Runnable() {
                    @Override public void run() {

                        // Berhenti berputar/refreshing
                        mSwipeRefreshLayout.setRefreshing(false);
                        getDataSend();

                    }
                }, 1000);
            }
        });

        rview = findViewById(R.id.recylcerViews);
        rview.setHasFixedSize(true);
        tvNoData = findViewById(R.id.tv_noData);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        rview.setLayoutManager(manager);
        sharedP =   getSharedPreferences(PREF, Context.MODE_PRIVATE);
        shareEdit = sharedP.edit();

        final String name = getprefname();

        String url = "http://"+getApplicationContext().getString(R.string.ip_default)+"/API_IOR/tampil_ior_send.php";
        final String text = textSearch;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<occ> occlist = new JsonConverter<occ>().toArrayList(response, occ.class);
                Log.d("ior_sand", "response : "+response);

                if (response.equalsIgnoreCase("")){
                    tvNoData.setVisibility(View.VISIBLE);
                    rview.setVisibility(View.GONE);

                }else {

                    tvNoData.setVisibility(View.GONE);
                    rview.setVisibility(View.VISIBLE);
                }
                Ior_Send_Adapter adapter = new Ior_Send_Adapter(getApplicationContext(), occlist);
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


    }

    @Override
    public void onRefresh() {
        getDataSend();
    }

    private void getDataSend(){
        mSwipeRefreshLayout.setRefreshing(true);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        rview.setLayoutManager(manager);

        sharedP =   getSharedPreferences(PREF, Context.MODE_PRIVATE);
        shareEdit = sharedP.edit();
        Log.d("ior_send", "onCreate: "+textSearch);

        final String name = getprefname();

        Log.d("ior_send", "Name: "+name);
        Log.d("ior_send", "Masuk Sini: "+name);

        String url = "http://"+getApplicationContext().getString(R.string.ip_default)+"/API_IOR/tampil_ior_send.php";
        final String text = textSearch;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<occ> occlist = new JsonConverter<occ>().toArrayList(response, occ.class);
                Log.d("ior_sand", "response : "+response);
                Log.d("ior_send", "Masuk Sini Res: "+response);

                if (response.equalsIgnoreCase("")){
                    tvNoData.setVisibility(View.VISIBLE);
                    rview.setVisibility(View.GONE);

                }else {

                    tvNoData.setVisibility(View.GONE);
                    rview.setVisibility(View.VISIBLE);
                }
                Ior_Send_Adapter adapter = new Ior_Send_Adapter(getApplicationContext(), occlist);
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
        mSwipeRefreshLayout.setRefreshing(false);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.searchview, menu);
        Log.d("cek","AASDASD");
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        textSearch = query;
        calldata(query);
        Log.d("cek ior send", "onQueryTextSubmit: "+query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        textSearch = newText;
        calldata(newText);
        Log.d("cek ior send", "onQueryTextChange: "+newText);
        return false;
    }

    private void calldata(final String key){
        final String name = getprefname();


        Log.d("cek ior recived", "calldata key: "+key+"  Name: "+name);
        String url = "http://"+getApplicationContext().getString(R.string.ip_default)+"/API_IOR/tampil_ior_send.php";
        final String text = textSearch;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<occ> occlist = new JsonConverter<occ>().toArrayList(response, occ.class);
                Ior_Send_Adapter adapter = new Ior_Send_Adapter(getApplicationContext(), occlist);
                final LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
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
                hashMap.put("key", key);
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

    }
}
