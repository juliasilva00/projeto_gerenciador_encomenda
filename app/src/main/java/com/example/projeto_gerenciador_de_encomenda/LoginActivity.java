package com.example.projeto_gerenciador_de_encomenda;


import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.GetTokenResult;


public class LoginActivity extends AppCompatActivity {

    EditText campoSenha;
    EditText campoEmail;
    Button botaoLogin;

    private FirebaseAuth autenticacao;
    ;
    ;

    private void inicializar(){
        campoEmail = findViewById(R.id.editTextEmailLogin);
        campoSenha = findViewById(R.id.editTextSenhaLogin);
        botaoLogin = findViewById(R.id.botaoLogin);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        autenticacao = ConfiguracaoBD.autenticacaoFirebase();
        inicializar();
    }


    public void validarLogin(View v){
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();

        if(!email.isEmpty()){
            if(!senha.isEmpty()){
                Usuario usuario = new Usuario();
                usuario.setEmail(email);
                usuario.setSenha(senha);

                logar(usuario);

            }else {
                Toast.makeText(this, "Preencha a senha", Toast.LENGTH_SHORT).show();

            }
        }else {
            Toast.makeText(this, "Preencha o email", Toast.LENGTH_SHORT).show();

        }
    }

    private void logar(Usuario usuario) {
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirHome();
                    obterToken();
                } else {
                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        excecao = "Usuário não está cadastrado";
                    } catch (FirebaseAuthInvalidCredentialsException e ){
                        excecao = "Email ou senha incorretos";
                    } catch (Exception e){
                        excecao = "Erro ao logar:" + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_SHORT).show();

                }
            }
        });


    }

    private void abrirHome() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        //parametros da clase é basicamente de onde estamos -> para onde estamos indo
        startActivity(i);
    }

    public void obterToken() {
        if (autenticacao.getCurrentUser() != null){
            autenticacao.getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                @Override
                public void onComplete(@NonNull Task<GetTokenResult> task) {
                    if (task.isSuccessful()) {
                        String token = task.getResult().getToken();
                        // Faça algo com o token, como salvá-lo no SharedPreferences ou enviá-lo para o servidor
                    } else {
                        // Tratar erro ao obter o token
                        Exception exception = task.getException();
                        if (exception instanceof FirebaseAuthInvalidUserException) {
                            // O usuário não está autenticado, trate de acordo com sua lógica
                            Toast.makeText(LoginActivity.this, "Usuário não autenticado", Toast.LENGTH_SHORT).show();
                        } else {
                            // Outro erro ao obter o token, exiba uma mensagem de erro genérica
                            Toast.makeText(LoginActivity.this, "Erro ao obter o token", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }


    public void cadastrar(View v){
        Intent i = new Intent(this, CadastroActivity.class);
        startActivity(i);
    }
}