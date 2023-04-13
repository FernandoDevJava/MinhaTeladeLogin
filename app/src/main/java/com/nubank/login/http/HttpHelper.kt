package com.nubank.login.http

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.net.URL

class HttpHelper {

    fun post(json: String) {

        val client = OkHttpClient().newBuilder()
            .build()
        val headerHttp = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = json.toRequestBody(headerHttp)
        val URL = "http://home.lsfcloud.com.br:8080/api/auth"
        val request: Request = Request.Builder().url(URL).post(body)
            .url("http://home.lsfcloud.com.br:8080/api/auth")
            .method("POST", body)
            .addHeader("Content-Type", "application/json")
            .build()
        val response: Response = client.newCall(request).execute()


        //conexões
        // ec2-15-229-8-134.sa-east-1.compute.amazonaws.com
        // aws.lsfcloud.com.br:8080/api/auth/login


        /*val client = OkHttpClient()

        val request = Request.Builder()
            .url("http://ec2-15-229-8-134.sa-east-1.compute.amazonaws.com:8080/api/auth")
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            for ((name, value) in response.headers) {
                println("$name: $value")
            }

            Log.d("teste", response.body!!.string())*/


        /////////////////////////////////////////////////////////////////////////////

        /* // Definir URL do servidor
        val URL = "http://ec2-15-229-8-134.sa-east-1.compute.amazonaws.com:8080/api/auth"

        // Definir o cabeçalho
        val headerHttp = "application/json; charset=utf-8".toMediaTypeOrNull()

        // Criar um cliente que vai disparar a requisição
        val client = OkHttpClient()

        // Criar o Body da requisição
        val body = json.toRequestBody(headerHttp)

        // Criar a requisição http para o servidor
        val request = Request.Builder().url(URL).post(body).build()

        // Utilizar o client para fazer a requisição e receber a resposta
        val response = client.newCall(request).execute()

        return response.body.toString()*/

        /*Log.d("teste", response.body!!.string())*/

    }

    private fun URL(mediaType: MediaType?, s: String): URL {
        TODO("Not yet implemented")
    }

}

private fun Any.method(method: String, body: URL): Request.Builder {
    TODO("Not yet implemented")
}
