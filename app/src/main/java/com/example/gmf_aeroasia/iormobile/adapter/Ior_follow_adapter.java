package com.example.gmf_aeroasia.iormobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gmf_aeroasia.iormobile.R;
import com.example.gmf_aeroasia.iormobile.model.occ_follow;

import java.util.ArrayList;

public class Ior_follow_adapter extends RecyclerView.Adapter<Ior_follow_adapter.MyViewHolder> {

    private Context Mctx;
    private ArrayList<occ_follow> occ_follow_list;
    String status ;

    public Ior_follow_adapter(Context Mctx, ArrayList<occ_follow> occ_follow_list) {
        this.Mctx = Mctx;
        this.occ_follow_list = occ_follow_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.from(parent.getContext()).inflate(R.layout.activity_item_follow, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final occ_follow info_occ_follow = occ_follow_list.get(position);

        holder.vh_follow_name.setText(info_occ_follow.follow_by_name);
        holder.vh_follow_by.setText(info_occ_follow.follow_by);
        holder.vh_follow_est.setText(info_occ_follow.follow_est_finish);
        holder.vh_follow_attch.setText(info_occ_follow.attachment);
        holder.vh_follow_desc.setText(info_occ_follow.follow_desc);
        holder.vh_follow_unit.setText("("+info_occ_follow.follow_by_unit+")");


        //holder.vh_deskripsi_berita.setText(info.deskripsi_berita);
      //  String fullUrl = "http://"+Mctx.getString(R.string.ip_default)+"/API_IOR/attachment/"+ info_occ_follow.attachment;

        //String fullUrl = "http://appro.probolinggokab.go.id/adminhumas/pict/" + info_occ.attachment;

//        Glide.with(Mctx)
//                .load(fullUrl)
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.drawable.ic_launcher_background)
//                .into(holder.iv_occ);
//        Toast toast = Toast.makeText(Mctx,infob.tanggal_kegiatan_b, Toast.LENGTH_LONG);
//        toast.show();

//        holder.view_container.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Mctx, DetailActivity.class);
//                i.putExtra("occ_id", info_occ.occ_id);
//                i.putExtra("ior_no", info_occ.occ_no);
//                i.putExtra("foto_report", info_occ.attachment);
//                i.putExtra("ior_subject", info_occ.occ_sub);
//                i.putExtra("ior_sendto", info_occ.occ_send_to);
//                i.putExtra("ior_reff", info_occ.occ_reff);
//                i.putExtra("ior_category", info_occ.occ_category);
//                i.putExtra("ior_sub_category", info_occ.occ_sub_category);
//                i.putExtra("ior_risk_index", info_occ.occ_risk_index);
//                i.putExtra("ior_created_by_name", info_occ.created_by_name);
//                i.putExtra("ior_estfinish", info_occ.occ_estfinish);
//                i.putExtra("ior_Insertby", info_occ.occ_Insertby);
//                i.putExtra("ior_detail", info_occ.occ_detail);
//                i.putExtra("ior_status", info_occ.occ_status);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Mctx.startActivity(i);
//            }
//        });

//        holder.btn_komen.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent komen = new Intent(Mctx, DetailActivity.class);
//                i.putExtra("occ_id", info_occ.occ_id);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Mctx.startActivity(komen);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        if (occ_follow_list != null) {
            return occ_follow_list.size();
        }
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView vh_follow_name,vh_follow_by,vh_follow_est,vh_follow_attch,vh_follow_desc,vh_follow_unit;
//        Button vh_status,btn_komen;
//        ImageView iv_occ;
//        LinearLayout view_container;
        //CardView view_containerp;


        public MyViewHolder(View itemView) {
            super(itemView);
            //view_containerp = itemView.findViewById(R.id.containerp);
            vh_follow_name = itemView.findViewById(R.id.tv_occ_follow_name);
            vh_follow_by = itemView.findViewById(R.id.tv_occ_follow_by);
            vh_follow_est = itemView.findViewById(R.id.tv_occ_follow_est);
            vh_follow_attch = itemView.findViewById(R.id.tv_occ_follow_attch);
            vh_follow_desc = itemView.findViewById(R.id.tv_occ_follow_desc);
            vh_follow_unit = itemView.findViewById(R.id.tv_occ_follow_unit);
            //vh_status = itemView.findViewById(R.id.bt_status);
            //btn_komen = itemView.findViewById(R.id.komenfollow);

           // view_container = itemView.findViewById(R.id.linearlayoutitem);

            //iv_occ = itemView.findViewById(R.id.image_report);
        }
    }

}
