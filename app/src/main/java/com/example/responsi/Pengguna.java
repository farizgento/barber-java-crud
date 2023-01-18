package com.example.responsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.responsi.adapter.PenggunaAdapter;
import com.example.responsi.adapter.UserAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.responsi.model.User;

import java.util.ArrayList;
import java.util.List;
public class Pengguna extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<User> list = new ArrayList<>();
    private PenggunaAdapter penggunaAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengguna);
        recyclerView = findViewById(R.id.recycler_view);

        progressDialog = new ProgressDialog(Pengguna.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Mengambil data...");
        penggunaAdapter = new PenggunaAdapter(getApplicationContext(), list);
        penggunaAdapter.setDialog(new PenggunaAdapter.Dialog() {
            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Buat pesanan"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(Pengguna.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                Intent intent = new Intent(getApplicationContext(), Transaksi.class);
                                intent.putExtra("id", list.get(pos).getId());
                                intent.putExtra("jenis", list.get(pos).getJenis());
                                intent.putExtra("harga", list.get(pos).getHarga());
                                intent.putExtra("durasi", list.get(pos).getDurasi());
                                intent.putExtra("kategori", list.get(pos).getKategori());
                                startActivity(intent);
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(penggunaAdapter);

        getData();
    }
    public void getData(){
        progressDialog.show();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document : task.getResult()){
                                User user = new User(document.getString("jenis"), document.getString("harga"), document.getString("durasi"), document.getString("kategori"));
                                user.setId(document.getId());
                                list.add(user);
                            }
                            penggunaAdapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(getApplicationContext(), "Data gagal di ambil!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

}