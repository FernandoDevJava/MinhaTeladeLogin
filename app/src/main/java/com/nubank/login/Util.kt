package com.nubank.login

import android.content.Context
import android.os.Build
import android.os.StrictMode
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast


object Util {

    var CPF_MASK = "###.###.###-##"
    var CELULAR_MASK = "(##) #####-####"
    var CEP_MASK = "#####-###"

    fun unmask(s: String): String? {
        return s.replace("[.]".toRegex(), "").replace("[-]".toRegex(), "")
            .replace("[/]".toRegex(), "").replace("[(]".toRegex(), "")
            .replace("[)]".toRegex(), "").replace(" ".toRegex(), "")
            .replace(",".toRegex(), "")
    }

    fun isASign(c: Char): Boolean {
        return c == '.' || c == '-' || c == '/' || c == '(' || c == ')' || c == ',' || c == ' '
    }

    fun mask(mask: String, text: String): String? {
        var i = 0
        var mascara: String? = ""
        for (m in mask.toCharArray()) {
            if (m != '#') {
                mascara += m
                continue
            }
            mascara += try {
                text[i]
            } catch (e: Exception) {
                break
            }
            i++
        }
        return mascara
    }

    fun insert(mask: String, ediTxt: EditText): TextWatcher? {
        return object : TextWatcher {
            var isUpdating = false
            var old = ""
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val str = Util.unmask(s.toString())
                var mascara = ""
                if (isUpdating) {
                    if (str != null) {
                        old = str
                    }
                    isUpdating = false
                    return
                }
                var index = 0
                for (i in 0 until mask.length) {
                    val m = mask[i]
                    if (m != '#') {
                        if (str != null) {
                            if (index == str.length && str.length < old.length) {
                                continue
                            }
                        }
                        mascara += m
                        continue
                    }
                    mascara += try {
                        str?.get(index)
                    } catch (e: Exception) {
                        break
                    }
                    index++
                }
                if (mascara.length > 0) {
                    var last_char = mascara[mascara.length - 1]
                    var hadSign = false
                    if (str != null) {
                        while (isASign(last_char) && str.length == old.length) {
                            mascara = mascara.substring(0, mascara.length - 1)
                            last_char = mascara[mascara.length - 1]
                            hadSign = true
                        }
                    }
                    if (mascara.length > 0 && hadSign) {
                        mascara = mascara.substring(0, mascara.length - 1)
                    }
                }
                isUpdating = true
                ediTxt.setText(mascara)
                ediTxt.setSelection(mascara.length)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {}
        }
    }

    fun removeCaracteresErro(message: String):String{
        return message.replace("[","").replace("]","").replace("\"","")
    }

    fun url(): String{
        return "https://springjwt.lsfcloud.com.br/api/"
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