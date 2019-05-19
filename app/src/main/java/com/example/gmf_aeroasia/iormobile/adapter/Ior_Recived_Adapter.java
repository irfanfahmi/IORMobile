package com.example.gmf_aeroasia.iormobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gmf_aeroasia.iormobile.R;
import com.example.gmf_aeroasia.iormobile.model.occ;

import java.util.ArrayList;

public class Ior_Recived_Adapter extends RecyclerView.Adapter<Ior_Recived_Adapter.MyViewHolder> {

    private Context Mctx;
    private ArrayList<occ> occ_recived_list;


    public Ior_Recived_Adapter(Context Mctx, ArrayList<occ> occ_recived_list) {
        this.Mctx = Mctx;
        this.occ_recived_list = occ_recived_list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.from(parent.getContext()).inflate(R.layout.activity_item_recivedior, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final occ info_occ = occ_recived_list.get(position);
        holder.vh_nama_.setText(info_occ.created_by_name);
        holder.vh_no_ior.setText(info_occ.occ_no);
        holder.vh_sendto.setText(info_occ.occ_send_to);
        holder.vh_status.setText(info_occ.occ_status);
        holder.vh_sub.setText(info_occ.occ_sub);
        holder.vh_tgl.setText(info_occ.occ_date);

        //holder.vh_deskripsi_berita.setText(info.deskripsi_berita);
        String fullUrl = "http://"+Mctx.getString(R.string.ip_default)+"/API_IOR/attachment/"+ info_occ.attachment;

        //String fullUrl = "http://appro.probolinggokab.go.id/adminhumas/pict/" + info_occ.attachment;

        Glide.with(Mctx)
                .load(fullUrl)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.drawable.ic_launcher_background)
                .into(holder.iv_occ);
//        Toast toast = Toast.makeText(Mctx,infob.tanggal_kegiatan_b, Toast.LENGTH_LONG);
//        toast.show();
//        holder.view_containerp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(Mctx, pemerintah_detail_item.class);
//                i.putExtra("nama_kegiatanp", infop.nama_kegiatan_p);
//                i.putExtra("tanggal_kegiatanp", infop.tanggal_kegiatan_p);
//                i.putExtra("tempat_kegiatanp", infop.tempat_kegiatan_p);
//                i.putExtra("deskripsi_kegiatanp", infop.deskripsi_kegiatan_p);
//                i.putExtra("foto_kegiatanp", infop.foto_kegiatan_p);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                Mctx.startActivity(i);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        if (occ_recived_list != null) {
            return occ_recived_list.size();
        }
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView vh_nama_,vh_sub,vh_no_ior,vh_sendto,vh_tgl;
        Button vh_status;
        ImageView iv_occ;
        //CardView view_containerp;


        public MyViewHolder(View itemView) {
            super(itemView);
            //view_containerp = itemView.findViewById(R.id.containerp);
            vh_nama_ = itemView.findViewById(R.id.tv_nama_pengirim);
            vh_sub = itemView.findViewById(R.id.tv_occ_subject);
            vh_no_ior = itemView.findViewById(R.id.tv_no_ior);
            vh_sendto = itemView.findViewById(R.id.tv_sendto);
            vh_tgl = itemView.findViewById(R.id.tv_tgl_occ);
            vh_status = itemView.findViewById(R.id.bt_status);


            iv_occ = itemView.findViewById(R.id.image_report);
        }
    }

}
