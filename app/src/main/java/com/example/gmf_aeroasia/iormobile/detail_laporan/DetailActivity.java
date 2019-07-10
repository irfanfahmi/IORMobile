package com.example.gmf_aeroasia.iormobile.detail_laporan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gmf_aeroasia.iormobile.CommentActivity;
import com.example.gmf_aeroasia.iormobile.R;

public class DetailActivity extends AppCompatActivity {
FloatingActionButton fab,fab_addFollow,fab_addPurpose;
Animation FabOpen,FabClose,FabClockView,FabAntiClock;
TextView tv_addFollow,tv_addPurpose;
boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan);

        //set data
        final String occ_id  = getIntent().getExtras().getString("occ_id");
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
        String status  = getIntent().getExtras().getString("ior_status");
        String status_fab  = getIntent().getExtras().getString("status_fab");

        fab = (FloatingActionButton) findViewById(R.id.fab_menu);
        fab_addFollow = (FloatingActionButton) findViewById(R.id.fab_addFollow);
        tv_addFollow = (TextView) findViewById(R.id.tv_addFollow);
        fab_addPurpose = (FloatingActionButton) findViewById(R.id.fab_addPurpose);
        tv_addPurpose = (TextView) findViewById(R.id.tv_addPurpose);


        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabAntiClock = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlock);
        FabClockView = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);

        if (status_fab.contains("1")){
            fab.setVisibility(View.VISIBLE);
        }else {
            fab.setVisibility(View.GONE);
        }

        fab_addPurpose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setMessage("Under Maintenance !")
                        .setTitle("Infomation")
                        .setNegativeButton("Ok",null);
//                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                shareEdit.clear();
//                                shareEdit.commit();
//
//                                // After logout redirect user to Loing Activity
//                                Intent in = new Intent(MainActivity.this, LoginActivity.class);
//                                // Closing all the Activities
//                                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//                                // Add new Flag to start new Activity
//                                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//                                // Staring Login Activity
//                                MainActivity.this.startActivity(in);
//                            }
//                        }

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
                    fab_addFollow.startAnimation(FabClose);
                    fab_addPurpose.startAnimation(FabClose);
                    //fab.startAnimation(FabAntiClock);
                    fab_addFollow.setClickable(false);
                    fab_addPurpose.setClickable(false);
                    isOpen=false;

                }else {
                    tv_addFollow.startAnimation(FabOpen);
                    tv_addPurpose.startAnimation(FabOpen);
                    fab_addFollow.startAnimation(FabOpen);
                    fab_addPurpose.startAnimation(FabOpen);
                    //fab.startAnimation(FabClockView);
                    fab_addFollow.setClickable(true);
                    fab_addPurpose.setClickable(true);
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

        if (status.equals("1")){
            status = "Open";
        }else if(status.equals("3")){
            status = "Closed";
        }else if(status.equals("0")){
            status = "NCR";
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
}
