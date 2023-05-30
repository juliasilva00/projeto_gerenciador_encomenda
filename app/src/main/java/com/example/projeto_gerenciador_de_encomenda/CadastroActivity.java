package com.example.projeto_gerenciador_de_encomenda;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projeto_gerenciador_de_encomenda.Model.Usuario;
import com.example.projeto_gerenciador_de_encomenda.Utils.ConfiguracaoBD;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;


public class CadastroActivity extends AppCompatActivity {

    Usuario usuario;
    FirebaseAuth autenticacao;
    EditText campoNome;
    EditText campoEmail;
    EditText campoSenha;
    Button botaoCadastrar;

    private void inicializar(){
        campoNome = findViewById(R.id.editTextNome);
        campoEmail = findViewById(R.id.editTextEmail);
        campoSenha = findViewById(R.id.editTextSenha);
        botaoCadastrar = findViewById(R.id.botaoCadastrar);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        inicializar();
    }

    public void validarCampos(View v){

        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        if(!nome.isEmpty()){
            if (!email.isEmpty()){
                if(!senha.isEmpty()){
                    usuario = new Usuario();
                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    usuario.setSenha(senha);
                    //cadastro
                    cadastrarUsuario();
                }else{
                    Toast.makeText(this, "Preencha a senha", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Preencha o email", Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "Preencha o nome", Toast.LENGTH_SHORT).show();
        }
    }

    private  void cadastrarUsuario() {

        autenticacao = ConfiguracaoBD.autenticacaoFirebase();

        autenticacao.createUserWithEmailAndPassword( usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this, "Cadastro Realizado Com Sucesso", Toast.LENGTH_SHORT).show();
                } else {
                    String exception = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e) {
                        exception = "Digite uma senha forte";
//                    } catch (FirebaseAuthInvalidCredentialsException e) {
//                        exception = "Digite um email válido";
//                    }
                    }catch (FirebaseAuthUserCollisionException e ){
                        exception = "Usuário já cadastrado";
                    } catch (Exception e){
                        exception = "Erro" + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this, exception, Toast.LENGTH_SHORT).show();


                }
            }
        });
    }
}