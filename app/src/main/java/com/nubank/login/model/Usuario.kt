package com.nubank.login.model

class Usuario {

    var nome = ""
    var sobrenome = ""
    var email = ""
    var senha = ""

    override fun toString(): String {
        return "Usuario(nome='$nome', sobrenome='$sobrenome', email='$email', senha='$senha')"
    }



}