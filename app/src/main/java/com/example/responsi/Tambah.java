package com.example.responsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Tambah extends AppCompatActivity {

    private EditText editjenis, editharga,editdurasi,editkategori;
    private Button btnSave;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);
        editjenis = findViewById(R.id.jenis);
        editharga = findViewById(R.id.harga);
        editdurasi = findViewById(R.id.durasi);
        editkategori = findViewById(R.id.kategori);
        btnSave = findViewById(R.id.btn_save);

        progressDialog = new ProgressDialog(Tambah.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Menyimpan...");

        btnSave.setOnClickListener(v -> {
            if (editjenis.getText().length()>0 && editharga.getText().length()>0 && editdurasi.getText().length()>0 && editkategori.getText().length()>0){
                saveData(editjenis.getText().toString(), editharga.getText().toString(),editdurasi.getText().toString(), editkategori.getText().toString());
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

    private void saveData(String jenis, String harga, String durasi, String kategori){
        Map<String, Object> user = new HashMap<>();
        user.put("jenis", jenis);
        user.put("harga", harga);
        user.put("durasi", durasi);
        user.put("kategori", kategori);

        progressDialog.show();
        if (id!=null){
            db.collection("users").document(id)
                    .set(user)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Berhasil!", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(getApplicationContext(), "Gagal!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }else {
            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "Berhasil!", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
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