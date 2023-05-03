package com.nubank.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.nubank.login.databinding.ActivityResetSenhaBinding
import com.nubank.login.model.Mlogin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject

class ResetSenha : AppCompatActivity() {

    private lateinit var binding: ActivityResetSenhaBinding
    private var context: Context = this
    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Util.verificaPermissaoInternet()

        binding = ActivityResetSenhaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.idButtonEnviarToken.setOnClickListener { resetEmail() }

        binding.idTextPageToken.setOnClickListener {
            val intentTenhoToken = Intent(applicationContext, EmailToken::class.java)
            startActivity(intentTenhoToken)
        }
        binding.idButtonBack.setOnClickListener {
            val backResetSenha = Intent(applicationContext, MainActivity::class.java)
            startActivity(backResetSenha)
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
            val resetEmail = JSONObject()

            resetEmail.put("email", binding.etEmailReset.text)

            requisicaoEnviarEmail(resetEmail)
        }
    }

    fun requisicaoEnviarEmail(resetEmail: JSONObject) {
        ProgressBarUtils.show(context)
        CoroutineScope(Dispatchers.IO).launch {
            val rests: Deferred<Pair<String, String>> = async {
                Mlogin().enviarEmail(json = resetEmail)
            }

            val respostaEmail = rests.await()

            withContext(Dispatchers.Main) {
                ProgressBarUtils.close(context)
                if (respostaEmail.first != "erro") {
                    if (respostaEmail.first == "200") {
                        Util.menssagemToast(context, context.getString(R.string.envio_token))

                        var objTempo = JSONObject(respostaEmail.second)
                        var tempo = objTempo.getString("expires_in")

                        timer = object : CountDownTimer((tempo.toInt() * 1000).toLong(), 1000) {
                            override fun onTick(millisUntilFinished: Long) {
                                val timeResult =
                                    "${
                                        (millisUntilFinished / 1000 / 60).toString()
                                            .padStart(2, '0')
                                    }:" + "" +
                                            "${
                                                (millisUntilFinished / 1000 % 60).toString()
                                                    .padStart(2, '0')
                                            } "
                                binding.idTextCronometro.text = "$timeResult"
                            }

                            override fun onFinish() {
                                binding.idTextCronometro.setText(R.string.token_expirado)
                                binding.idButtonEnviarToken.setText(R.string.reenviar_token)
                            }
                        }.start()
                    } else {
                        val respostaErro = respostaEmail.second

                        val jsonErro = JSONObject(respostaErro)

                        if (jsonErro.has("message")) {
                            val erro = Util.removeCaracteresErro(jsonErro.getString("message"))
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