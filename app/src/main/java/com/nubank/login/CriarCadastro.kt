package com.nubank.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.nubank.login.databinding.ActivityCriarCadastroBinding
import com.nubank.login.model.Mlogin
import kotlinx.coroutines.*
import org.json.JSONObject

class CriarCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityCriarCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val SDK_zINT = Build.VERSION.SDK_INT
        if (SDK_zINT > 8) {
            val policy = ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }

        binding = ActivityCriarCadastroBinding.inflate((layoutInflater))
        setContentView(binding.root)

        binding.etbtnCriar.setOnClickListener { create() }

        binding.ettextJaTenhoConta.setOnClickListener {
            var intentMainActivity = Intent(applicationContext, MainActivity::class.java)
            startActivity(intentMainActivity)
        }

    }


    fun create() {
        binding.itNome.addTextChangedListener(
            textListener(
                binding.itNome, binding.etNome
            )
        )
        binding.itSobrenome.addTextChangedListener(
            textListener(
                binding.itSobrenome, binding.etSobrenome
            )
        )
        binding.etEmailCriar.addTextChangedListener(
            textListener(
                binding.etEmailCriar, binding.itEmailCriar
            )
        )
        binding.etSenhaCriar.addTextChangedListener(
            textListener(
                binding.etSenhaCriar, binding.itSenhaCriar
            )
        )

        //Limpa o erro quando o usuário começa a digitar
        if (binding.itNome.text.toString().isEmpty()) {
            binding.etNome.error = "Digite seu Nome"
        } else if (binding.itSobrenome.text.toString().isEmpty()) {
            binding.etSobrenome.error = "Digite seu Sobrenome"
        } else if (binding.etEmailCriar.text.toString().isEmpty()) {
            binding.itEmailCriar.error = "Digite seu E-mail"
        } else if (binding.etSenhaCriar.text.toString().isEmpty()) {
            binding.itSenhaCriar.error = "Digite sua Senha"
        } else {
            var create = JSONObject()

            create.put("nome", binding.itNome.text)
            create.put("sobrenome", binding.itSobrenome.text)
            create.put("email", binding.etEmailCriar.text)
            create.put("senha", binding.etSenhaCriar.text)

            CoroutineScope(Dispatchers.IO).launch {
                val rest: Deferred<String?> = async {
                    Mlogin().createUser(json = create)
                }

                val responses = rest.await().toString()


                withContext(Dispatchers.Main) {
                    Log.d("testeRsResponse", responses)
                    var respostacreate = JSONObject(responses)
                    if (responses.isEmpty()) {
                        Toast.makeText(
                            applicationContext, "Usuário criado com sucesso!", Toast.LENGTH_SHORT
                        ).show()
                    } else if (respostacreate.has("statusCode")) {
                        Toast.makeText(
                            applicationContext,
                            respostacreate.getString("message").replace("[", "").replace("]", "")
                                .replace("\"f", ""),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        if (respostacreate.has("token")) {
                            var token = respostacreate.getString("token")
                            Toast.makeText(applicationContext, token, Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Email já Cadastrado!",
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