package com.sisifus.praetorian.Classes;

import com.google.firebase.database.Exclude;

public class Usuario {

    private static String nome;
    private String email;
    private String senha;
    private String keyUser;
    private String uid;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getSenha() {
        return senha;
    }

    @Exclude
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getKeyUser() {
        return keyUser;
    }

    public void setKeyUser(String keyUser) {
        this.keyUser = keyUser;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}


