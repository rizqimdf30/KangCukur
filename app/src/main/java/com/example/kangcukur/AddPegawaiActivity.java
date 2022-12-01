package com.example.kangcukur;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddPegawaiActivity extends AppCompatActivity {
    private ImageView profil;
    private TextInputEditText etNama,etUmur,etPengalaman,etHandphone;
    private MaterialButton addPegawai;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pegawai);
        profil = findViewById(R.id.profil_kangcukur);
        etNama = findViewById(R.id.etNama);
        etUmur = findViewById(R.id.etUmur);
        etPengalaman = findViewById(R.id.etPengalaman);
        etHandphone = findViewById(R.id.etHandphone);
        addPegawai = findViewById(R.id.btnAdd);

        profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        addPegawai.setOnClickListener(v-> {
            if (etNama.getText().length()>0 && etUmur.getText().length()>0 && etPengalaman.getText().length()>0 && etHandphone.getText().length()>0){
                upload(etNama.getText().toString(), etUmur.getText().toString(),etPengalaman.getText().toString(), etHandphone.getText().toString());
            }else{
                Toast.makeText(getApplicationContext(), "Silahkan isi data yang diperlukan!", Toast.LENGTH_SHORT).show();
            }
            Intent intent = new Intent(AddPegawaiActivity.this, MainAdminActivity.class);
            startActivity(intent);
        });
        Intent intent = getIntent();
        if (intent!=null){
            id = intent.getStringExtra("id");
            etNama.setText(intent.getStringExtra("nama"));
            etUmur.setText(intent.getStringExtra("umur"));
            etPengalaman.setText(intent.getStringExtra("pengalaman"));
            etHandphone.setText(intent.getStringExtra("nohp"));
            Glide.with(getApplicationContext()).load(intent.getStringExtra("profil")).into(profil);
        }
    }

    private void selectImage(){
        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddPegawaiActivity.this);
        builder.setTitle(getString(R.string.app_name));
        builder.setIcon(R.mipmap.ic_launcher_round);
        builder.setItems(items,(dialog,item)->{
            if(items[item].equals("Take Photo")){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,10);
            } else if(items[item].equals("Choose from Library")){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent,"Select image"),20);
            } else if(items[item].equals("Cancel")){
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 20 && resultCode == RESULT_OK && data!=null){
            final Uri path = data.getData();
            Thread thread  = new Thread(()->{
               try{
                   InputStream inputStream = getContentResolver().openInputStream(path);
                   Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                   profil.post(()->{
                       profil.setImageBitmap(bitmap);
                   });
               } catch(IOException e) {
                   e.printStackTrace();
                }
            });
            thread.start();
        }

        if(requestCode == 10 && resultCode == RESULT_OK){
            final Bundle extras = data.getExtras();
            Thread thread  = new Thread(()->{
                Bitmap bitmap = (Bitmap) extras.get("data");
                profil.post(()->{
                    profil.setImageBitmap(bitmap);
                });
            });
            thread.start();
        }
    }

    private void upload(String nama, String umur, String pengalaman, String nohp){
        profil.setDrawingCacheEnabled(true);
        profil.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) profil.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] data = baos.toByteArray();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference("image").child("IMG"+new Date().getTime()+".jpeg");
        UploadTask uploadTask = reference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getApplicationContext(), exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if(taskSnapshot.getMetadata()!=null){
                    if(taskSnapshot.getMetadata().getReference()!=null){
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if(task.getResult()!=null){
                                    saveData(nama,umur,pengalaman, nohp, task.getResult().toString());
                                } else {
                                    Toast.makeText(getApplicationContext(), "Gagal!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(getApplicationContext(), "Gagal!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Gagal!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void saveData(String nama, String umur, String pengalaman, String nohp, String profil){
        Map<String, Object> Pegawai = new HashMap<>();
        Pegawai.put("nama", nama);
        Pegawai.put("umur", umur);
        Pegawai.put("pengalaman", pengalaman);
        Pegawai.put("nohp", nohp);
        Pegawai.put("profil",profil);

        if(id!=null){
            db.collection("Pegawai").document(id)
                    .set(Pegawai)
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
        } else {
            db.collection("Pegawai")
                    .add(Pegawai)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "Berhasil!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}