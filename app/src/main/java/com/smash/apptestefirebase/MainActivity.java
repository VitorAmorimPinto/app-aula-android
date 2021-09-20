package com.smash.apptestefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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

//    Manipular usuários
    private FirebaseAuth usuario = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        referencia.child cria um novo nó ou acessa um já existente
//        o setValue atualiza ou cadastra o valor

    this.cadComIndentificadorUnico();


    }

    public void cadComIndentificadorUnico(){
        Pessoa p = new Pessoa();
        p.setNome("Vitor");
        p.setSobrenome("Amorim");
// O push permite criar um identificador unico para cada usuário
        pessoas.push().setValue(p);
    }
    public void deslogarUsuario(){
        usuario.signOut();
    }
    public void logarUsuario(){
//        É igual o de cadastro porém muda para funcão signInWithEmailAndPassword
        usuario.signInWithEmailAndPassword(
                "vitor232596@gmail.com","ja12345")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.i("LogUser", "Sucesso ao logar usuario!" );
                        }else{
                            Log.i("LogUser", "Erro ao logar usuario!" );
                        }
                    }
                });
    }
    public void pegaUsuario(){
//        Caso o usuario.getCurrentUser seja vazio significa que não existe usuario logado
        if( usuario.getCurrentUser() != null ){
            Log.i("CurrentUser", "Usuario logado!" );
        }else {
            Log.i("CurrentUser", "Usuario nao logado!" );
        }
    }
    public void criarUsuario(){
//       método para createUserWithEmailAndPassword serve para criacão de usuários
        usuario.createUserWithEmailAndPassword(
                "vitor232596@gmail.com","ja12345")
//                addOnCompleteListener é o ouvinte que informa o resultado do cadastro de usuarios
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Log.i("CreateUser", "Sucesso ao cadastrar usuario!" );
                        }else{
                            Log.i("CreateUser", "Erro ao cadastrar usuario!" );
                        }
                    }
                });
    }
    public void pegarDados(){
//        Solicitando Dados do nó pessoas

        //O listener é um ovinte que recebe do Firebase atualizações em tempo real
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

        pessoas.child("003").setValue(p);
    }
}