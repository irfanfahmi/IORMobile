package com.example.gmf_aeroasia.iormobile.detail_laporan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gmf_aeroasia.iormobile.CommentActivity;
import com.example.gmf_aeroasia.iormobile.R;
import com.example.gmf_aeroasia.iormobile.adapter.Ior_Recived_Adapter;
import com.example.gmf_aeroasia.iormobile.model.occ;
import com.example.gmf_aeroasia.iormobile.service.MySingleton;
import com.kosalgeek.android.json.JsonConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {
FloatingActionButton fab,fab_addFollow,fab_addPurpose,fab_addProgress,fab_addApprove,fab_addClose;
Animation FabOpen,FabClose,FabClockView,FabAntiClock;
TextView tv_addFollow,tv_addPurpose,tv_addProgress,tv_addApprove,tv_addClose;
boolean isOpen = false;

    SharedPreferences sharedP;
    SharedPreferences.Editor shareEdit;
    final String PREF = "Keypref";
    final String KEY_NAME = "name";
    final String KEY_ID = "id";
    final String KEY_UNIT = "unit";
    final String KEY_USER = "username";
    String occ_id,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan);

        //set data
       occ_id  = getIntent().getExtras().getString("occ_id");
        String foto  = getIntent().getExtras().getString("foto_report");
        String no  = getIntent().getExtras().getString("ior_no");
        String subject  = getIntent().getExtras().getString("ior_subject");
        String sendto  = getIntent().getExtras().getString("ior_sendto");
        String reff  = getIntent().getExtras().getString("ior_reff");
        String category  = getIntent().getExtras().getString("ior_category");
        String subCategory  = getIntent().getExtras().getString("ior_sub_category");
        String riskIndex  = getIntent().getExtras().getString("ior_risk_index");
        String createdByName  = getIntent().getExtras().getString("ior_created_by_name");
        String estfinish  = getIntent().getExtras().getString("ior_estfinish");
        String insertby  = getIntent().getExtras().getString("ior_Insertby");
        String detail  = getIntent().getExtras().getString("ior_detail");
        status  = getIntent().getExtras().getString("ior_status");
        String status_fab  = getIntent().getExtras().getString("status_fab");

        fab = (FloatingActionButton) findViewById(R.id.fab_menu);
        fab_addFollow = (FloatingActionButton) findViewById(R.id.fab_addFollow);
        tv_addFollow = (TextView) findViewById(R.id.tv_addFollow);
        fab_addPurpose = (FloatingActionButton) findViewById(R.id.fab_addPurpose);
        tv_addPurpose = (TextView) findViewById(R.id.tv_addPurpose);
        fab_addProgress = (FloatingActionButton) findViewById(R.id.fab_addProgress);
        tv_addProgress = (TextView) findViewById(R.id.tv_addProgress);
        fab_addApprove= (FloatingActionButton) findViewById(R.id.fab_addApprove);
        tv_addApprove = (TextView) findViewById(R.id.tv_addApprove);
        fab_addClose= (FloatingActionButton) findViewById(R.id.fab_addClose);
        tv_addClose = (TextView) findViewById(R.id.tv_addClose);

        sharedP =   getSharedPreferences(PREF, Context.MODE_PRIVATE);
        shareEdit = sharedP.edit();

        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabAntiClock = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlock);
        FabClockView = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);

        if (status_fab.contains("1")){
            fab.setVisibility(View.VISIBLE);
        }else {
            fab.setVisibility(View.GONE);
        }
        String unit = getprefunit();

        final String unitdinas = unit.substring(0,2);
        // Cek tombol progress berdasarkan Dinas TQ aja
        if (!unitdinas.equalsIgnoreCase("TQ")){
            // untuk Responsible
            Log.d("DetailActivity","RESPONSIBLE UNIT MASUK SINI");

            if (status.equalsIgnoreCase("1")){
                //status open hanya tombol progress
                Log.d("DetailActivity","Responsible : masuk sini 1");

                tv_addApprove.setVisibility(View.GONE);
                fab_addApprove.setVisibility(View.GONE);
                tv_addFollow.setVisibility(View.GONE);
                fab_addFollow.setVisibility(View.GONE);
                tv_addPurpose.setVisibility(View.GONE);
                fab_addPurpose.setVisibility(View.GONE);
                tv_addClose.setVisibility(View.GONE);
                fab_addClose.setVisibility(View.GONE);
            }else if(status.equalsIgnoreCase("2")) {
                //status progress hanya muncul purpose & add follow
                Log.d("DetailActivity","Responsible: masuk sini 2");
                tv_addApprove.setVisibility(View.GONE);
                fab_addApprove.setVisibility(View.GONE);
                tv_addProgress.setVisibility(View.GONE);
                fab_addProgress.setVisibility(View.GONE);
                tv_addClose.setVisibility(View.GONE);
                fab_addClose.setVisibility(View.GONE);
            }else if(status.equalsIgnoreCase("0")) {
                //status waiting tidak muncul apa2 nunggu verifikasi dulu
                Log.d("DetailActivity","Responsible: masuk sini 0");
                fab.setVisibility(View.GONE);
                tv_addApprove.setVisibility(View.GONE);
                fab_addApprove.setVisibility(View.GONE);
                tv_addProgress.setVisibility(View.GONE);
                fab_addProgress.setVisibility(View.GONE);
                tv_addFollow.setVisibility(View.GONE);
                fab_addFollow.setVisibility(View.GONE);
                tv_addPurpose.setVisibility(View.GONE);
                fab_addPurpose.setVisibility(View.GONE);
            }else if(status.equalsIgnoreCase("6")) {
                //status purpose hanya muncul add follow aja
                Log.d("DetailActivity","Responsible: masuk sini 6");
                tv_addApprove.setVisibility(View.GONE);
                fab_addApprove.setVisibility(View.GONE);
                tv_addProgress.setVisibility(View.GONE);
                fab_addProgress.setVisibility(View.GONE);
                tv_addPurpose.setVisibility(View.GONE);
                fab_addPurpose.setVisibility(View.GONE);
                tv_addClose.setVisibility(View.GONE);
                fab_addClose.setVisibility(View.GONE);
            }else if(status.equalsIgnoreCase("3")) {
                //status Closed hanya muncul add follow aja
                Log.d("DetailActivity","Responsible: masuk sini 3");
                fab.setVisibility(View.GONE);
                tv_addApprove.setVisibility(View.GONE);
                fab_addApprove.setVisibility(View.GONE);
                tv_addProgress.setVisibility(View.GONE);
                fab_addProgress.setVisibility(View.GONE);
                tv_addPurpose.setVisibility(View.GONE);
                fab_addPurpose.setVisibility(View.GONE);
                tv_addClose.setVisibility(View.GONE);
                fab_addClose.setVisibility(View.GONE);
            }else if(status.equalsIgnoreCase("5")) {
                //status Not OCC ga muncul apa2
                Log.d("DetailActivity","Responsible: masuk sini 5");
                fab.setVisibility(View.GONE);
                tv_addApprove.setVisibility(View.GONE);
                fab_addApprove.setVisibility(View.GONE);
                tv_addProgress.setVisibility(View.GONE);
                fab_addProgress.setVisibility(View.GONE);
                tv_addPurpose.setVisibility(View.GONE);
                fab_addPurpose.setVisibility(View.GONE);
                tv_addClose.setVisibility(View.GONE);
                fab_addClose.setVisibility(View.GONE);
            }
            Log.d("DetailActivity","Responsible: masuk sini all");
            tv_addApprove.setVisibility(View.GONE);
            fab_addApprove.setVisibility(View.GONE);
            tv_addClose.setVisibility(View.GONE);
            fab_addClose.setVisibility(View.GONE);

        }else {
            //untuk TQY
            Log.d("DetailActivity","TQY MASUK SINI");

            if (status.equalsIgnoreCase("0")){
                //kalo status waiting tombol approve aja tampil lainya gak
//                tv_addApprove.setVisibility(View.VISIBLE);
//                fab_addApprove.setVisibility(View.VISIBLE);
                Log.d("DetailActivity","TQY: masuk sini 0");
                tv_addFollow.setVisibility(View.GONE);
                fab_addFollow.setVisibility(View.GONE);
                tv_addPurpose.setVisibility(View.GONE);
                fab_addPurpose.setVisibility(View.GONE);
                tv_addProgress.setVisibility(View.GONE);
                fab_addProgress.setVisibility(View.GONE);
                tv_addClose.setVisibility(View.GONE);
                fab_addClose.setVisibility(View.GONE);
            }else if(status.equalsIgnoreCase("1")){
                Log.d("DetailActivity", "TQY: masuk sini 1");

                //kalo status Open tombol progress aja tampil lainya gak
                tv_addApprove.setVisibility(View.GONE);
                fab_addApprove.setVisibility(View.GONE);
                tv_addProgress.setVisibility(View.GONE);
                fab_addProgress.setVisibility(View.GONE);
                tv_addFollow.setVisibility(View.GONE);
                fab_addFollow.setVisibility(View.GONE);
                tv_addPurpose.setVisibility(View.GONE);
                fab_addPurpose.setVisibility(View.GONE);
                tv_addClose.setVisibility(View.GONE);
                fab_addClose.setVisibility(View.GONE);
            }else if(status.equalsIgnoreCase("2")){
                Log.d("DetailActivity", "TQY: masuk sini 2");
            //kalo status progress tombol purpose & follow aja tampil lainya gak
                tv_addApprove.setVisibility(View.GONE);
                fab_addApprove.setVisibility(View.GONE);
                tv_addProgress.setVisibility(View.GONE);
                fab_addProgress.setVisibility(View.GONE);
                tv_addClose.setVisibility(View.GONE);
                fab_addClose.setVisibility(View.GONE);
            }else if(status.equalsIgnoreCase("6")){
                Log.d("DetailActivity", "TQY: masuk sini 2");
                //kalo status purpose tombol purpose tampil lainya gak
                tv_addApprove.setVisibility(View.GONE);
                fab_addApprove.setVisibility(View.GONE);
                tv_addProgress.setVisibility(View.GONE);
                fab_addProgress.setVisibility(View.GONE);
                tv_addFollow.setVisibility(View.GONE);
                fab_addFollow.setVisibility(View.GONE);
                tv_addPurpose.setVisibility(View.GONE);
                fab_addPurpose.setVisibility(View.GONE);
            }else if(status.equalsIgnoreCase("3")) {
                //status Closed hanya muncul add follow aja
                Log.d("DetailActivity","TQY: masuk sini 3");
                fab.setVisibility(View.GONE);
                tv_addApprove.setVisibility(View.GONE);
                fab_addApprove.setVisibility(View.GONE);
                tv_addProgress.setVisibility(View.GONE);
                fab_addProgress.setVisibility(View.GONE);
                tv_addPurpose.setVisibility(View.GONE);
                fab_addPurpose.setVisibility(View.GONE);
                tv_addClose.setVisibility(View.GONE);
                fab_addClose.setVisibility(View.GONE);
            }
            else if(status.equalsIgnoreCase("5")) {
                //status Not IOR hanya muncul add follow aja
                Log.d("DetailActivity","TQY: masuk sini 5");
                fab.setVisibility(View.GONE);
                tv_addApprove.setVisibility(View.GONE);
                fab_addApprove.setVisibility(View.GONE);
                tv_addProgress.setVisibility(View.GONE);
                fab_addProgress.setVisibility(View.GONE);
                tv_addPurpose.setVisibility(View.GONE);
                fab_addPurpose.setVisibility(View.GONE);
                tv_addClose.setVisibility(View.GONE);
                fab_addClose.setVisibility(View.GONE);
            }

            Log.d("DetailActivity", "TQY: masuk sini all");

        }

        //buka tombol progres ketika status masih waiting progress

        fab_addClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setMessage("Close Laporan IOR ?")
                        .setNegativeButton("No",null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ///ngirim occ_id & status Closestatus default 3

                                String url = "http://" + getApplicationContext().getString(R.string.ip_default) + "/API_IOR/update_ior_status.php";
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("DetailActivity", "onResponse Approve : "+response);
                                        Toast.makeText(getApplicationContext(),
                                                "Close IOR "+response, Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(getIntent());
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
                                        hashMap.put("occ_id", occ_id);
                                        hashMap.put("occ_status", "3");
                                        return hashMap;
                                    }


                                }
                                        ;

                                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                        5000,
                                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


                                //batas
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        fab_addApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setIcon(R.drawable.ic_send_24dp);
                builder.setMessage("Approve Laporan IOR ?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                /// Kalo Di Tolak sama TQY ngirim occ_id & status waiting ke NOT OCC, status default 1

                                String url = "http://" + getApplicationContext().getString(R.string.ip_default) + "/API_IOR/update_ior_status.php";
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("DetailActivity", "onResponse Approve : "+response);
                                        Toast.makeText(getApplicationContext(),
                                                "Approve "+response, Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(getIntent());
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
                                        hashMap.put("occ_id", occ_id);
                                        hashMap.put("occ_status", "5");
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
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ///ngirim occ_id & status waiting ke open, status default 1

                                String url = "http://" + getApplicationContext().getString(R.string.ip_default) + "/API_IOR/update_ior_status.php";
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("DetailActivity", "onResponse Approve : "+response);
                                        Toast.makeText(getApplicationContext(),
                                                "Approve "+response, Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(getIntent());
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
                                        hashMap.put("occ_id", occ_id);
                                        hashMap.put("occ_status", "1");
                                        return hashMap;
                                    }


                                }
                                        ;

                                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                        5000,
                                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


                                //batas
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });

        fab_addProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setMessage("Update Status Progress ?")
                        .setNegativeButton("No",null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ///ngirim occ_id & status default progres 2

                                String url = "http://" + getApplicationContext().getString(R.string.ip_default) + "/API_IOR/update_ior_status.php";
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("DetailActivity", "onResponse Update Progress : "+response);
                                        Toast.makeText(getApplicationContext(),
                                                "Update Progress "+response, Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(getIntent());
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
                                        hashMap.put("occ_id", occ_id);
                                        hashMap.put("occ_status", "2");
                                        return hashMap;
                                    }


                                }
                                        ;

                                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                        5000,
                                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


                                //batas
                            }
                            });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();



            }
        });

        fab_addPurpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setMessage("Purpose To Close ?")
                        .setNegativeButton("No",null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ///ngirim occ_id & status default purpose to close 6

                                String url = "http://" + getApplicationContext().getString(R.string.ip_default) + "/API_IOR/update_ior_status.php";
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.d("DetailActivity", "onResponse Update Progress : "+response);
                                        Toast.makeText(getApplicationContext(),
                                                "Purpose To Close "+response, Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(getIntent());
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
                                        hashMap.put("occ_id", occ_id);
                                        hashMap.put("occ_status", "6");
                                        return hashMap;
                                    }


                                }
                                        ;

                                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                        5000,
                                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


                                //batas
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen){

                    tv_addFollow.startAnimation(FabClose);
                    tv_addPurpose.startAnimation(FabClose);
                    tv_addProgress.startAnimation(FabClose);
                    tv_addApprove.startAnimation(FabClose);
                    tv_addClose.startAnimation(FabClose);
                    fab_addProgress.startAnimation(FabClose);
                    fab_addFollow.startAnimation(FabClose);
                    fab_addPurpose.startAnimation(FabClose);
                    fab_addApprove.startAnimation(FabClose);
                    fab_addClose.startAnimation(FabClose);

                    //fab.startAnimation(FabAntiClock);
                    fab_addFollow.setClickable(false);
                    fab_addPurpose.setClickable(false);
                    fab_addProgress.setClickable(false);
                    fab_addApprove.setClickable(false);
                    fab_addClose.setClickable(false);
                    isOpen=false;

                }else {
                    tv_addFollow.startAnimation(FabOpen);
                    tv_addPurpose.startAnimation(FabOpen);
                    tv_addProgress.startAnimation(FabOpen);
                    tv_addApprove.startAnimation(FabOpen);
                    tv_addClose.startAnimation(FabOpen);
                    fab_addFollow.startAnimation(FabOpen);
                    fab_addPurpose.startAnimation(FabOpen);
                    fab_addProgress.startAnimation(FabOpen);
                    fab_addApprove.startAnimation(FabOpen);
                    fab_addClose.startAnimation(FabOpen);
                    //fab.startAnimation(FabClockView);
                    fab_addFollow.setClickable(true);
                    fab_addPurpose.setClickable(true);
                    fab_addProgress.setClickable(true);
                    fab_addApprove.setClickable(true);
                    fab_addClose.setClickable(true);
                    isOpen=true;

                }
            }
        });







        //set view
        ImageView tv_image = findViewById(R.id.iv_laporan);
        TextView tv_no = findViewById(R.id.tv_number_ior);
        TextView tv_title = findViewById(R.id.tv_title_ior);
        TextView tv_sendto = findViewById(R.id.tv_sendTo);
        TextView tv_reff = findViewById(R.id.tv_reff);
        TextView tv_category = findViewById(R.id.tv_category);
        TextView tv_sub_category = findViewById(R.id.tv_sub_category);
        TextView tv_risk_index = findViewById(R.id.tv_risk_index);
        TextView tv_reporter = findViewById(R.id.tv_reporter);
        TextView tv_est_finish = findViewById(R.id.tv_est_finish);
        TextView tv_insertBy = findViewById(R.id.tv_insertBy);
        TextView tv_desc = findViewById(R.id.tv_desc);
        TextView tv_stat = findViewById(R.id.tv_stat);
        LinearLayout btnstatus = findViewById(R.id.btn_status);

        if (status.equals("0")){
            status = "Waiting Verification";
            btnstatus.setBackgroundResource(R.drawable.rect_progress);
        }else if(status.equals("1")){
            status = "Open";
            btnstatus.setBackgroundResource(R.drawable.rect_yellow);
        }else if(status.equals("2")){
            status = "Progress";
            btnstatus.setBackgroundResource(R.drawable.rect_progress);
        }else if(status.equals("3")){
            status = "Closed";
            btnstatus.setBackgroundResource(R.drawable.rect_green);
        }else if(status.equals("4")){
            status = "Over Due";
            btnstatus.setBackgroundResource(R.drawable.rect_overdue);
        }else if(status.equals("5")){
            status = "Not OCC";
            btnstatus.setBackgroundResource(R.drawable.rect_overdue);
        }else if(status.equals("6")){
            if (!unitdinas.equalsIgnoreCase("TQ")){
                status = "Progress";
                btnstatus.setBackgroundResource(R.drawable.rect_progress);
            }else {
                status = "Waiting to close";
                btnstatus.setBackgroundResource(R.drawable.rect_progress);
            }

        }

        //set data ke view
        tv_no.setText(no);
        tv_title.setText(subject);
        tv_sendto.setText(sendto);
        tv_reff.setText(reff);
        tv_category.setText(category);
        tv_sub_category.setText(subCategory);
        tv_risk_index.setText(riskIndex);
        tv_reporter.setText(createdByName);
        tv_est_finish.setText(estfinish);
        tv_insertBy.setText(insertby);
        tv_desc.setText(detail);
        tv_stat.setText(status);

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background);

        String fullUrl = "http://"+getApplicationContext().getString(R.string.ip_default)+"/API_IOR/attachment/"+ foto;
        // set image using Glide
        Glide.with(this).load(fullUrl).apply(requestOptions).into(tv_image);

        fab_addFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent komen = new Intent(DetailActivity.this,CommentActivity.class);
                komen.putExtra("occ_id", occ_id);
                komen.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(komen);
            }
        });



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
}
