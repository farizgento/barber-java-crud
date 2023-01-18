package com.example.responsi.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.responsi.R;
import com.example.responsi.model.User;

import java.util.List;

public class PenggunaAdapter extends RecyclerView.Adapter<PenggunaAdapter.MyViewHolder> {
    private Context context;
    private List<User> list;
    private Dialog dialog;

    public interface Dialog{
        void onClick(int pos);
    }
    public void setDialog(PenggunaAdapter.Dialog dialog) {
        this.dialog = dialog;
    }

    public PenggunaAdapter(Context context, List<User> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PenggunaAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_user, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PenggunaAdapter.MyViewHolder holder, int position) {
        holder.jenis.setText(list.get(position).getJenis());
        holder.harga.setText(list.get(position).getHarga());
        holder.durasi.setText(list.get(position).getDurasi());
        holder.kategori.setText(list.get(position).getKategori());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView jenis, harga, durasi, kategori;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            jenis = itemView.findViewById(R.id.jenis);
            harga = itemView.findViewById(R.id.harga);
            durasi = itemView.findViewById(R.id.durasi);
            kategori = itemView.findViewById(R.id.kategori);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (dialog!=null){
                        dialog.onClick(getLayoutPosition());
                    }
                }
            });
        }
    }
}
