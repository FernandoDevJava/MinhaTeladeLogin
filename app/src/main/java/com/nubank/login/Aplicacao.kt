package com.nubank.login

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import com.nubank.login.databinding.ActivityAplicacaoBinding
import com.nubank.login.model.Mlogin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class Aplicacao : AppCompatActivity() {

    private lateinit var binding: ActivityAplicacaoBinding
    private var context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val SDK_xINT = Build.VERSION.SDK_INT
        if (SDK_xINT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        binding = ActivityAplicacaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idButtonSair.setOnClickListener { texte() }

    }

    fun texte() {
        var logout = JSONObject()
        logout(logout)
    }

    fun logout(logout: JSONObject) {
        CoroutineScope(Dispatchers.IO).launch {
            val restLogout: Deferred<Pair<String, String>> = async {
                Mlogin().logout(json = logout)
            }

            val responseLogout = restLogout.await()

            withContext(Dispatchers.Main) {
               if (responseLogout.first == "200") {
                   Util.menssagemToast(context, context.getString(R.string.logout))
                   var intentButtonSair = Intent(applicationContext, MainActivity::class.java)
                   startActivity(intentButtonSair)
               } else {
                   var respostaErro = responseLogout.second

                   var jsonErro = JSONObject(respostaErro)

                   if (jsonErro.has("message")) {
                       var erro = Util.removeCaracteresErro(jsonErro.getString("message"))
                       Util.menssagemToast(context, erro)
                   } else {
                       Util.menssagemToast(context, context.getString(R.string.erro_geral))
                   }
               }
            }
        }

    }

}