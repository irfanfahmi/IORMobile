package com.example.gmf_aeroasia.iormobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.gmf_aeroasia.iormobile.R;
import com.example.gmf_aeroasia.iormobile.detail_laporan.DetailActivity;
import com.example.gmf_aeroasia.iormobile.model.occ;

import java.util.ArrayList;

public class Ior_History_Adapter extends RecyclerView.Adapter<Ior_History_Adapter.MyViewHolder> {

    private Context Mctx;
    private ArrayList<occ> occ_history_list;
    String status ;
    SharedPreferences sharedP;
    SharedPreferences.Editor shareEdit;
    final String PREF = "Keypref";
    final String KEY_NAME = "name";
    final String KEY_ID = "id";
    final String KEY_UNIT = "unit";
    final String KEY_USER = "username";

    public Ior_History_Adapter(Context Mctx, ArrayList<occ> occ_history_list) {
        this.Mctx = Mctx;
        this.occ_history_list = occ_history_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.from(parent.getContext()).inflate(R.layout.activity_item_history, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }
    public String getprefunit(){
        sharedP = Mctx.getSharedPreferences(PREF, Context.MODE_PRIVATE);
        return sharedP.getString(KEY_UNIT,"");

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final occ info_occ = occ_history_list.get(position);

        String unit = getprefunit();
        final String unitdinas = unit.substring(0,2);
        if (info_occ.occ_status.equals("0")){
            status = "Waiting Verification";
            holder.vh_status.setBackgroundResource(R.drawable.rect_progress);
        }else if(info_occ.occ_status.equals("1")){
            status = "Open";
            holder.vh_status.setBackgroundResource(R.drawable.rect_yellow);
        }else if(info_occ.occ_status.equals("2")){
            status = "Progress";
            holder.vh_status.setBackgroundResource(R.drawable.rect_progress);
        }else if(info_occ.occ_status.equals("3")){
            status = "Closed";
            holder.vh_status.setBackgroundResource(R.drawable.rect_green);
        }else if(info_occ.occ_status.equals("4")){
            status = "Over Due";
            holder.vh_status.setBackgroundResource(R.drawable.rect_overdue);

        }else if(info_occ.occ_status.equals("5")){
            status = "Not OCC";
            holder.vh_status.setBackgroundResource(R.drawable.rect_overdue);
        }else if(info_occ.occ_status.equals("6")){
            if (!unitdinas.equalsIgnoreCase("TQ")){
                status = "Progress";
                holder.vh_status.setBackgroundResource(R.drawable.rect_progress);
            }else {
                status = "Waiting to close";
                holder.vh_status.setBackgroundResource(R.drawable.rect_progress);
            }
        }

        if (info_occ.created_by_name!=null){
            holder.vh_nama_.setText(info_occ.created_by_name);
        }else {
            holder.vh_nama_.setText("Hidden Reporter");
        }

        holder.vh_no_ior.setText(info_occ.occ_no);
        holder.vh_sendto.setText(info_occ.occ_send_to);
        holder.vh_status.setText(status);
        holder.vh_sub.setText(info_occ.occ_sub);
        holder.vh_tgl.setText(info_occ.occ_date);


        String fullUrl = "http://"+Mctx.getString(R.string.ip_default)+"/API_IOR/attachment/"+ info_occ.attachment;


        holder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Mctx, DetailActivity.class);
                i.putExtra("occ_id", info_occ.occ_id);
                i.putExtra("ior_no", info_occ.occ_no);
                i.putExtra("foto_report", info_occ.attachment);
                i.putExtra("ior_subject", info_occ.occ_sub);
                i.putExtra("ior_sendto", info_occ.occ_send_to);
                i.putExtra("ior_reff", info_occ.occ_reff);
                i.putExtra("ior_category", info_occ.occ_category);
                i.putExtra("ior_sub_category", info_occ.occ_sub_category);
                i.putExtra("ior_risk_index", info_occ.occ_risk_index);
                i.putExtra("ior_created_by_name", info_occ.created_by_name);
                i.putExtra("ior_estfinish", info_occ.occ_estfinish);
                i.putExtra("ior_Insertby", info_occ.occ_Insertby);
                i.putExtra("ior_detail", info_occ.occ_detail);
                i.putExtra("ior_status", info_occ.occ_status);
                i.putExtra("status_fab", "1");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Mctx.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (occ_history_list != null) {
            return occ_history_list.size();
        }
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView vh_nama_,vh_sub,vh_no_ior,vh_sendto,vh_tgl;
        Button vh_status;

        CardView view_container;


        public MyViewHolder(View itemView) {
            super(itemView);
            //view_containerp = itemView.findViewById(R.id.containerp);
            vh_nama_ = itemView.findViewById(R.id.tv_nama_pengirim_h);
            vh_sub = itemView.findViewById(R.id.tv_occ_subject_h);
            vh_no_ior = itemView.findViewById(R.id.tv_no_ior_h);
            vh_sendto = itemView.findViewById(R.id.tv_sendto_h);
            vh_tgl = itemView.findViewById(R.id.tv_tgl_occ_h);
            vh_status = itemView.findViewById(R.id.bt_status_h);
            view_container = itemView.findViewById(R.id.linearlayoutitem_h);

        }
    }

}
