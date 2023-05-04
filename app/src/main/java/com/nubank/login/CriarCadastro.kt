package com.nubank.login

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import com.nubank.login.databinding.ActivityCriarCadastroBinding
import com.nubank.login.model.Mlogin
import kotlinx.coroutines.*
import org.json.JSONObject

class CriarCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityCriarCadastroBinding
    private var context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Util.verificaPermissaoInternet()

        binding = ActivityCriarCadastroBinding.inflate((layoutInflater))
        setContentView(binding.root)

        binding.etbtnCriar.setOnClickListener { create() }

        binding.ettextJaTenhoConta.setOnClickListener {
            var intentMainActivity = Intent(applicationContext, MainActivity::class.java)
            startActivity(intentMainActivity)
        }
        binding.idButtonBack.setOnClickListener {
            var backLogin = Intent(applicationContext, MainActivity::class.java)
            startActivity(backLogin)
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
        binding.etSenhaVerificar.addTextChangedListener(
            textListener(
                binding.etSenhaVerificar, binding.itSenhaVerificar
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
        } else if (binding.etSenhaVerificar.text.toString().isEmpty()) {
            binding.itSenhaVerificar.error = "Confirme sua Senha"
        } else if (binding.etSenhaVerificar.text.toString() != binding.etSenhaCriar.text.toString()) {
            binding.itSenhaVerificar.error = "Senhas Diferentes"
        } else {
            var create = JSONObject()

            create.put("nome", binding.itNome.text)
            create.put("sobrenome", binding.itSobrenome.text)
            create.put("email", binding.etEmailCriar.text)
            create.put("senha", binding.etSenhaCriar.text)
            create.put("senha", binding.etSenhaVerificar.text)

            requisicaoCadastro(create)
        }
    }

    fun requisicaoCadastro(create: JSONObject) {
        ProgressBarUtils.show(context)
        CoroutineScope(Dispatchers.IO).launch {
            val rest: Deferred<Pair<String, String>> = async {
                Mlogin().createUser(json = create)
            }

            val responses = rest.await()

            withContext(Dispatchers.Main) {
                ProgressBarUtils.close(context)
                if (responses.first != "erro") {
                    if (responses.first == "201") {
                        Util.menssagemToast(context, context.getString(R.string.usuario_criado))
                        var intentCreateUser = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intentCreateUser)
                    } else {
                        var respostaErro = responses.second

                        var jsonErro = JSONObject(respostaErro)

                        if (jsonErro.has("message")) {
                            var erro = Util.removeCaracteresErro(jsonErro.getString("message"))
                            Util.menssagemToast(context, erro)
                        } else {
                            Util.menssagemToast(context, context.getString(R.string.erro_geral))
                        }
                    }
                } else {
                    Util.menssagemToast(context, context.getString(R.string.erro_requisicao))
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