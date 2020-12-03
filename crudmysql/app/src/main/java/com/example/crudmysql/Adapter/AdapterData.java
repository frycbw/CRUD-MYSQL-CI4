package com.example.crudmysql.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crudmysql.AddActivity;
import com.example.crudmysql.Model.DataModel;
import com.example.crudmysql.R;

import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData>{
    private Context ctx;
    private List<DataModel> listMhs;

    public AdapterData(Context ctx, List<DataModel> listMhs) {
        this.ctx = ctx;
        this.listMhs = listMhs;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.kolom_item,parent,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModel dm = listMhs.get(position);

        holder.tvId.setText(String.valueOf(dm.getId()));
        holder.tvNim.setText(dm.getNim());
        holder.tvNama.setText(dm.getNama());
        holder.tvAngkatan.setText(String.valueOf(dm.getAngkatan()));
    }

    @Override
    public int getItemCount() {
        return listMhs.size();
    }

    public class HolderData extends RecyclerView.ViewHolder{
        TextView tvId,tvNama,tvNim,tvAngkatan;


        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.tv_id);
            tvNim = itemView.findViewById(R.id.tv_nim);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvAngkatan = itemView.findViewById(R.id.tv_angkatan);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent goInput = new Intent(ctx, AddActivity.class);
                    goInput.putExtra("id",tvId.getText().toString());
                    goInput.putExtra("nim",tvNim.getText().toString());
                    goInput.putExtra("nama",tvNama.getText().toString());
                    goInput.putExtra("angkatan",tvAngkatan.getText().toString());
                    ctx.startActivity(goInput);
                }
            });
        }
    }
}


