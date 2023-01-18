package com.example.responsi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.responsi.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Pembayaran extends AppCompatActivity {
    private TextView editjenis;
    private TextView editharga;
    private TextView editdurasi;
    private TextView editkategori;
    private TextView editnama;
    private Button btnSave;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private String id = "";
    private List<User> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pembayaran);
        editjenis = findViewById(R.id.jenis);
        editharga = findViewById(R.id.harga);
        editdurasi = findViewById(R.id.durasi);
        editkategori = findViewById(R.id.kategori);
        editnama = findViewById(R.id.nama);

        progressDialog = new ProgressDialog(Pembayaran.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Menyimpan...");

        Intent intent = getIntent();
        if (intent!=null){
            id = intent.getStringExtra("id");
            editjenis.setText(intent.getStringExtra("jenis"));
            editharga.setText(intent.getStringExtra("harga"));
            editdurasi.setText(intent.getStringExtra("durasi"));
            editkategori.setText(intent.getStringExtra("kategori"));
            editnama.setText(intent.getStringExtra("nama"));
        }
    }
}