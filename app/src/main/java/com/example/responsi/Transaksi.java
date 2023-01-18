package com.example.responsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.responsi.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transaksi extends AppCompatActivity {


    private TextView editjenis, editharga,editdurasi,editkategori;
    private EditText editnama;
    private Button btnSave;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private String id = "";
    private List<User> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);
        editjenis = findViewById(R.id.jenis);
        editharga = findViewById(R.id.harga);
        editdurasi = findViewById(R.id.durasi);
        editkategori = findViewById(R.id.kategori);
        editnama = findViewById(R.id.nama);
        btnSave = findViewById(R.id.btn_save);

        progressDialog = new ProgressDialog(Transaksi.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Menyimpan...");

        btnSave.setOnClickListener(v -> {
            if (editjenis.getText().length()>0 && editharga.getText().length()>0 && editdurasi.getText().length()>0 && editkategori.getText().length()>0 && editnama.getText().length()>0){
                saveData(editjenis.getText().toString(), editharga.getText().toString(),editdurasi.getText().toString(), editkategori.getText().toString(), editnama.getText().toString());
            }else{
                Toast.makeText(getApplicationContext(), "Silahkan isi semua data!", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        if (intent!=null){
            id = intent.getStringExtra("id");
            editjenis.setText(intent.getStringExtra("jenis"));
            editharga.setText(intent.getStringExtra("harga"));
            editdurasi.setText(intent.getStringExtra("durasi"));
            editkategori.setText(intent.getStringExtra("kategori"));
        }
    }

    private void saveData(String jenis, String harga, String durasi, String kategori, String nama){
        Map<String, Object> transaksi = new HashMap<>();
        transaksi.put("jenis", jenis);
        transaksi.put("harga", harga);
        transaksi.put("durasi", durasi);
        transaksi.put("kategori", kategori);
        transaksi.put("nama", nama);

        progressDialog.show();
        if (id!=null){
            db.collection("transaksis").document(id)
                    .set(transaksi)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Intent intent = new Intent(getApplicationContext(), Pembayaran.class);
                                intent.putExtra("id", id);
                                intent.putExtra("jenis", jenis);
                                intent.putExtra("harga", harga);
                                intent.putExtra("durasi", durasi);
                                intent.putExtra("kategori", kategori);
                                intent.putExtra("nama", nama);
                                startActivity(intent);

                            }else{
                                Toast.makeText(getApplicationContext(), "Gagal!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else {
            db.collection("transaksis")
                    .add(transaksi)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Intent intent = new Intent(getApplicationContext(), Pembayaran.class);
                            intent.putExtra("id", id);
                            intent.putExtra("jenis", jenis);
                            intent.putExtra("harga", harga);
                            intent.putExtra("durasi", durasi);
                            intent.putExtra("kategori", kategori);
                            intent.putExtra("nama", nama);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }
    }
}