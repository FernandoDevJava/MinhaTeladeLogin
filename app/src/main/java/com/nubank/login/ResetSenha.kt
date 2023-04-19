package com.nubank.login

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.nubank.login.databinding.ActivityResetSenhaBinding
import com.nubank.login.model.Mlogin
import kotlinx.coroutines.*
import org.json.JSONObject

class ResetSenha : AppCompatActivity() {

    private lateinit var binding: ActivityResetSenhaBinding
    private var context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val SDK_cINT = Build.VERSION.SDK_INT
        if (SDK_cINT > 8) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        binding = ActivityResetSenhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idButtonEnviarToken.setOnClickListener { resetEmail() }

        binding.idTextPageToken.setOnClickListener {
            var intentteste = Intent(applicationContext, EmailToken::class.java)
            startActivity(intentteste)
        }
    }

    fun resetEmail() {
        binding.etEmailReset.addTextChangedListener(
            textListener(
                binding.etEmailReset, binding.itEmailReset
            )
        )
        //Limpa o erro quando o usuário começa a digitar
        if (binding.etEmailReset.text.toString().isEmpty()) {
            binding.itEmailReset.error = "Digite seu Email"
        } else {
            var resetEmail = JSONObject()

            resetEmail.put("email", binding.etEmailReset.text)

            CoroutineScope(Dispatchers.IO).launch {
                val rests: Deferred<Pair<String, String>> = async {
                    Mlogin().enviarEmail(json = resetEmail)
                }

                ProgressBarUtils.show(context)
                val respostaEmail = rests.await()

                withContext(Dispatchers.Main) {
                    if (respostaEmail.first == "200") {
                        Toast.makeText(
                            applicationContext,
                            "Email Enviado!",
                            Toast.LENGTH_LONG
                        ).show()
                        ProgressBarUtils.close(context)
                    } else {
                        var erroEnvioEmail = respostaEmail.second

                        var jsonErroEmail = JSONObject(erroEnvioEmail)

                        if (jsonErroEmail.has("message")) {
                            Toast.makeText(
                                applicationContext,
                                jsonErroEmail.getString("message").replace("[", "").replace("]", "")
                                    .replace("\"f", ""),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }





    private fun textListener(input: EditText, view: TextInputLayout): TextWatcher {
        return object : TextWatcher {

            override fun afterTextChanged(edt: Editable?) {}

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int,
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                view.isErrorEnabled = false
            }
        }
    }
}