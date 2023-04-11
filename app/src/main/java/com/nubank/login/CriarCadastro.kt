package com.nubank.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.nubank.login.model.Usuario

class CriarCadastro : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_criar_cadastro)

        var backPageLogin = findViewById<View>(R.id.id_textJaTenhoConta)

        backPageLogin.setOnClickListener {
            var intentBackPageLogin = Intent(applicationContext, MainActivity::class.java)
            startActivity(intentBackPageLogin)
        }

        val buttonCriarUsuario = findViewById<Button>(R.id.id_button_Criar_Conta)
        val editCriarUsuarioNome = findViewById<EditText>(R.id.id_edit_Nome)
        val editCriarUsuarioSobrenome = findViewById<EditText>(R.id.id_edit_Sobrenome)
        val editCriarUsuarioEmail = findViewById<EditText>(R.id.id_edit_EmailCriar)
        val editCriarUsuarioSenha = findViewById<EditText>(R.id.id_edit_SenhaCriar)

        buttonCriarUsuario.setOnClickListener {
            // criar um objeto usuário
            val usuario = Usuario()
            usuario.nome = editCriarUsuarioNome.text.toString()
            usuario.sobrenome = editCriarUsuarioSobrenome.text.toString()
            usuario.email = editCriarUsuarioEmail.text.toString()
            usuario.senha = editCriarUsuarioSenha.text.toString()

            // converter o usuario em json
            val gson = Gson()
            val usuarioJson = gson.toJson(usuario)

           
        }

        /*
        val request = Request.Builder()
                .url("\"aws.lsfcloud.com.br:8080/api/auth")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                for ((name, value) in response.headers) {
                    println("$name: $value")
                }

                Log.d("teste", response.body!!.string())
            }
         */
    }
}