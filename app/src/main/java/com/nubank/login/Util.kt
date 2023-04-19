package com.nubank.login

import android.content.Context
import android.os.Build
import android.os.StrictMode
import android.widget.Toast

object Util {
    fun removeCaracteresErro(message: String):String{
        return message.replace("[","").replace("]","").replace("\"","")
    }

    fun url(): String{
        return "http://aws.lsfcloud.com.br:8080/api/"
    }

    fun menssagemToast(context: Context, mensagem: String){
        Toast.makeText(
            context,
            mensagem,
            Toast.LENGTH_LONG
        ).show()
    }

    fun verificaPermissaoInternet(){
        val SDK_INT = Build.VERSION.SDK_INT
        if (SDK_INT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder()
                .permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
    }
}