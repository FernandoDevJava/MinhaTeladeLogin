package com.nubank.login

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.nubank.login.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var nextPageCreateUsers = findViewById<View>(R.id.id_text_CriarConta)

        nextPageCreateUsers.setOnClickListener {
            var intentnextPageCreateUsers = Intent(applicationContext, CriarCadastro::class.java)
            startActivity(intentnextPageCreateUsers)
        }

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)


    }

}