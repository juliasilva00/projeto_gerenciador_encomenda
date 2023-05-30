package com.example.projeto_gerenciador_de_encomenda.Utils;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguracaoBD {

    private static FirebaseAuth auth;

    public static  FirebaseAuth autenticacaoFirebase(){
        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }

        return auth;
    }
}
