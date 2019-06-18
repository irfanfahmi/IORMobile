package com.example.gmf_aeroasia.iormobile.detail_laporan;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gmf_aeroasia.iormobile.R;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan);

        //set data
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

    }
}
