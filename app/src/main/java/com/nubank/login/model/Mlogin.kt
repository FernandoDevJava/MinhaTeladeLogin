package com.nubank.login.model

import android.util.Log
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class Mlogin {
    private val client = OkHttpClient()

    companion object {
        val MEDIA_TYPE_MARKDOWN = "application/json".toMediaType()
    }

    fun login(json: JSONObject): String {
        val request = Request.Builder()
            .url("http://home.lsfcloud.com.br:8080/api/auth/login")
            .post(json.toString().trimMargin().toRequestBody(MEDIA_TYPE_MARKDOWN))
            .build()

        client.newCall(request).execute().use { response ->
            //response.code
            return response.body!!.string()
        }
    }

    fun createUser(json: JSONObject): Pair<String,String> {
        Log.d("testeJson", json.toString())
        val requestCreate = Request.Builder()
            .url("http://home.lsfcloud.com.br:8080/api/auth/cadastro")
            .post(json.toString().trimMargin().toRequestBody(MEDIA_TYPE_MARKDOWN))
            .build()

        client.newCall(requestCreate).execute().use { response ->
            return Pair(response.code.toString(), response.body.string())
        }
    }

    fun enviarEmail(json: JSONObject): Pair<String,String> {
        Log.d("testeJsonEnvioEmail", json.toString())
        val requestResetEmail = Request.Builder()
            .url("http://home.lsfcloud.com.br:8080/api/auth/recuperarsenha")
            .post(json.toString().trimMargin().toRequestBody(MEDIA_TYPE_MARKDOWN))
            .build()

        client.newCall(requestResetEmail).execute().use { response ->
            return Pair(response.code.toString(), response.body.string())
        }
    }

    fun resetarSenha(json: JSONObject): Pair<String,String> {
        Log.d("testeJsonEnvioEmail", json.toString())
        val requestResetarSenha = Request.Builder()
            .url("http://home.lsfcloud.com.br:8080/api/auth/recuperarsenhaToken")
            .patch(json.toString().trimMargin().toRequestBody(MEDIA_TYPE_MARKDOWN))
            .build()

        client.newCall(requestResetarSenha).execute().use { response ->
            return Pair(response.code.toString(), response.body.string())
        }
    }
}