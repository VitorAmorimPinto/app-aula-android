package com.smash.apptestefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
//   Pega a refência raiz dos nós do banco - para pegar um nó expecifico => o getReference("nome nó")
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
//passando nó "pessoas" como referencia
    DatabaseReference pessoas = referencia.child("Pessoas");
// para buscar determinado objeto devemos colocar o .child("id unico")
//    DatabaseReference pessoas = referencia.child("Pessoas").child("002");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        referencia.child cria um novo nó ou acessa um já existente
//        o setValue atualiza ou cadastra o valor

        this.pegarDados();



    }
    public void pegarDados(){
//        Solicitando Dados do nó pessoas
        //O listener é um ovinte que recebe do Fire base atualizações em tempo real
        pessoas.addValueEventListener(new ValueEventListener() {
//            Caso quando os dados chegam
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("FIREBASE",snapshot.getValue().toString());
            }
//          Caso quando ocorre um erro na entrega dos dados
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void exemploCadastro(){
        Pessoa p = new Pessoa();
        p.setNome("Vanderson");
        p.setSobrenome("Cunha");

        pessoas.child("002").setValue(p);
    }
}