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
import com.google.firebase.database.Query;
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

//    this.cadComIndentificadorUnico();
    this.buscaFiltros();

    }
    public void buscaPorId(){
        DatabaseReference pessoaPesquisa = pessoas.child("-Mk3epqPvoU_UNUI0O8b");

//
        pessoaPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
//                Recuperando dados em formato de objeto
                /*Usuario dadosUsuario = dataSnapshot.getValue(Pessoa.class);
                Log.i("Dados usuario: ", " nome: " + dadosUsuario.getNome() + " idade: " + dadosUsuario.getIdade() );*/

                Log.i("Dados pessoa   : ", dataSnapshot.getValue().toString() );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void buscaFiltros(){
        DatabaseReference pessoaPesquisa = referencia.child("Pessoas");
//   Para fazer consultas no Firebase é necessario ordenar primeiro
//      Busca dado igual a... e ordenando por campo nome
//        Query usuarioPesquisa = pessoas.orderByChild("nome").equalTo("Priscila");

//     Ordena e exibe 3 usuários a partir do primeiro
//        Query usuarioPesquisa = pessoas.orderByKey().limitToFirst(3);

//     Ordena e exibe 3 usuários a partir do ultimo
//        Query usuarioPesquisa = pessoas.orderByKey().limitToLast(3);

//      Filtro do startAt(>=)
//        Query usuarioPesquisa = pessoas.orderByChild("idade").startAt(40);

//      Filtro do startAt(<=)
//        Query usuarioPesquisa = pessoas.orderByChild("idade").endAt(40);

//      Filtro entre dois valores(<=)
//        Query usuarioPesquisa = pessoas.orderByChild("idade").startAt(18).endAt(30);

//      Filtro de palavras
        Query usuarioPesquisa = pessoas.orderByChild("nome").startAt("V").endAt("V" + "\uf8ff");
        usuarioPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("Dados igual    : ", dataSnapshot.getValue().toString() );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void cadComIndentificadorUnico(){
        Pessoa p = new Pessoa();
        p.setNome("Vitor");
        p.setSobrenome("Amorim");
// O push permite criar um identificador unico para cada cadastro
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