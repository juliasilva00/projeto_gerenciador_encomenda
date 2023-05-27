package com.example.projeto_gerenciador_de_encomenda;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projeto_gerenciador_de_encomenda.Model.Encomenda;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText dataEntregue,status,dataRecebida,complementoMorador,nomeMorador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(MainActivity.this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        FloatingActionButton add = findViewById(R.id.fab);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.criar_encomenda_dialogo, null);
                dataEntregue = view1.findViewById(R.id.dataEntregue);
                status = view1.findViewById(R.id.status);
                dataRecebida = view1.findViewById(R.id.dataRecebida);
                complementoMorador = view1.findViewById(R.id.complementoMorador);
                nomeMorador = view1.findViewById(R.id.nomeMorador);

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Add")
                        .setView(view1)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                    ProgressDialog dialog = new ProgressDialog(MainActivity.this);
                                    dialog.setMessage("Storing in Database...");
                                    dialog.show();
                                    Encomenda encomenda = new Encomenda();
                                    encomenda.setNomeMorador(nomeMorador.getText().toString());
                                    encomenda.setComplemento(complementoMorador.getText().toString());
                                    encomenda.setDataRecebida(dataRecebida.getText().toString());
                                    encomenda.setStatus(status.getText().toString());
                                    encomenda.setDataEntregue(dataEntregue.getText().toString());

                                database.getReference().child("encomendas").push().setValue(encomenda).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            dialog.dismiss();
                                            dialogInterface.dismiss();
                                            Toast.makeText(MainActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            dialog.dismiss();
                                            Toast.makeText(MainActivity.this, "There was an error while saving data", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
                alertDialog.show();
            }
        });

        TextView empty = findViewById(R.id.empty);

        RecyclerView recyclerView = findViewById(R.id.recycler);

        database.getReference().child("encomendas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Encomenda> arrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Encomenda encomenda = dataSnapshot.getValue(Encomenda.class);
                    Objects.requireNonNull(encomenda).setKey(dataSnapshot.getKey());
                    arrayList.add(encomenda);
                }

                if (arrayList.isEmpty()) {
                    empty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    empty.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }

                EncomendaAdapter adapter = new EncomendaAdapter(MainActivity.this, arrayList);
                recyclerView.setAdapter(adapter);

                adapter.setOnItemClickListener(new EncomendaAdapter.OnItemClickListener(){
                    @Override
                    public void onClick(Encomenda encomenda) {
                        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.criar_encomenda_dialogo, null);


                        dataEntregue = view.findViewById(R.id.dataEntregue);
                        status = view.findViewById(R.id.status);
                        dataRecebida = view.findViewById(R.id.dataRecebida);
                        complementoMorador = view.findViewById(R.id.complementoMorador);
                        nomeMorador = view.findViewById(R.id.nomeMorador);

                        nomeMorador.setText(encomenda.getNomeMorador());
                        status.setText(encomenda.getStatus());
                        dataEntregue.setText(encomenda.getDataEntregue());
                        dataRecebida.setText(encomenda.getDataRecebida());
                        complementoMorador.setText(encomenda.getComplemento());


                        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Edit")
                                .setView(view)
                                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                            progressDialog.setMessage("Saving...");
                                            progressDialog.show();
                                            Encomenda encomenda1 = new Encomenda();
                                            encomenda1.setNomeMorador(nomeMorador.getText().toString());
                                            encomenda1.setComplemento(complementoMorador.getText().toString());
                                            encomenda1.setDataRecebida(dataRecebida.getText().toString());
                                            encomenda1.setStatus(status.getText().toString());
                                            encomenda1.setDataEntregue(dataEntregue.getText().toString());

                                        database.getReference().child("encomendas").child(encomenda.getKey()).setValue(encomenda1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    progressDialog.dismiss();
                                                    dialogInterface.dismiss();
                                                    Toast.makeText(MainActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(MainActivity.this, "There was an error while saving data", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                    }
                                })
                                .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        progressDialog.setTitle("Deleting...");
                                        progressDialog.show();
                                        database.getReference().child("encomendas").child(encomenda.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                progressDialog.dismiss();
                                                Toast.makeText(MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                            }
                                        });
                                    }
                                }).create();
                        alertDialog.show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}