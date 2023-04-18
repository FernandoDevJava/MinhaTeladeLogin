package com.nubank.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import com.nubank.login.databinding.ActivityAplicacaoBinding
import com.nubank.login.databinding.ActivityCriarCadastroBinding

class Aplicacao : AppCompatActivity() {

    private lateinit var binding: ActivityAplicacaoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val SDK_xINT = Build.VERSION.SDK_INT
        if (SDK_xINT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        binding = ActivityAplicacaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idButtonSair.setOnClickListener {
            var intentButtonSair = Intent(applicationContext, MainActivity::class.java)
            startActivity(intentButtonSair)
        }
    }

}