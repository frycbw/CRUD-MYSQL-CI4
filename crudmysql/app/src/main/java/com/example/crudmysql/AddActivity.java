package com.example.crudmysql;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Space;
import android.widget.Toast;

import com.example.crudmysql.API.RetroServer;
import com.example.crudmysql.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {
    private EditText inNim,inNama,inAngkatan;
    private Button btnSimpan,btnHapus;
    private String nim,nama,angkatan;
    private int idmhs;
    private Space space;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        inNim = findViewById(R.id.in_nim);
        inNama = findViewById(R.id.in_nama);
        inAngkatan = findViewById(R.id.in_angkatan);
        btnSimpan = findViewById(R.id.btn_simpan);
        btnHapus = findViewById(R.id.btn_hapus);
        space = findViewById(R.id.spaces);

        Intent data = getIntent();
        String id = data.getStringExtra("id");
        if(id !=null){
            inNim.setText(data.getStringExtra("nim"));
            inNama.setText(data.getStringExtra("nama"));
            inAngkatan.setText(data.getStringExtra("angkatan"));
            idmhs = Integer.parseInt(id);
            btnHapus.setVisibility(View.VISIBLE);
            space.setVisibility(View.VISIBLE);
        }
        // Ketika button simpan di klik
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nim = inNim.getText().toString();
                nama = inNama.getText().toString();
                angkatan = inAngkatan.getText().toString();

                if(nim.trim().equals("")){
                    inNim.setError("NIM harus diisi");
                }else if(nama.trim().equals("")){
                    inNama.setError("Nama Harus Diisi");
                }else if(angkatan.trim().equals("")){
                    inAngkatan.setError("Angkatan harus diisi");
                }else{
                    if (id != null){
                        updateData();
                    }else{
                        createData();
                    }
                }
            }
        });
        //Ketika Button Hapus Di Klik
        btnHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertData();
            }
        });
    }

    //Function Buat Data
    public void createData(){
        Call<ResponseModel> simpanData = RetroServer.getMhsApi().ardCreateMahasiswa(nim,nama,Integer.parseInt(angkatan));

        simpanData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String message = response.body().getMessage();
                Toast.makeText(AddActivity.this, message, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(AddActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Funcion Perbarui Data
    private void updateData(){
        Call<ResponseModel> perbaruiData = RetroServer.getMhsApi().ardUpdateMahasiswa(
                idmhs,
                nim,
                nama,
                Integer.parseInt(angkatan));
        perbaruiData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Toast.makeText(AddActivity.this, "Berhasil Mengupdate", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(AddActivity.this, "Gagal Hubung Server", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Function Alert/Peringatan jika mau di hapus
    private void alertData(){
        AlertDialog.Builder dialogPesan = new AlertDialog.Builder(AddActivity.this);
        dialogPesan.setMessage("Yakin Untuk Menghapus ?");
        dialogPesan.setCancelable(true);

        dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteData();
                dialogInterface.dismiss();
                finish();
            }
        });

        dialogPesan.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        dialogPesan.show();
    }

    //Function Delete Data
    private void deleteData(){
        Call<ResponseModel> hapusData = RetroServer.getMhsApi().ardDeleteMahasiswa(idmhs);
        hapusData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String message = response.body().getMessage();
                Toast.makeText(AddActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(AddActivity.this, "Gagal Menghubungi Server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}