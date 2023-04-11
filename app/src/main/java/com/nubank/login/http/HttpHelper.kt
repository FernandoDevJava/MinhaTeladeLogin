package com.nubank.login.http

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

class HttpHelper {

    fun post(json: String) : String {

        var cliente = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()


        // Definir URL do servidor
        val URL = "https://gestao-tarefas.onrender.com/auth"

        // Definir o cabeçalho
        val headerHttp = "application/json; charset=utf-8".toMediaTypeOrNull()

        // Criar um cliente que vai disparar a requisição
        val client = OkHttpClient()

        // Criar o Body da requisição
        val body = json.toRequestBody(headerHttp)

        // Criar a requisição http para o servidor
        var request = Request.Builder().url(URL).post(body).build()

        // Utilizar o client para fazer a requisição e receber a resposta
        val response = client.newCall(request).execute()

        return response.body.toString()

    }

}