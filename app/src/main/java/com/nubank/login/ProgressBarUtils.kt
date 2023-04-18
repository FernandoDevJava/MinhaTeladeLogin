package com.nubank.login

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater

object ProgressBarUtils {
    private var progressBar: AlertDialog? = null

    fun show(
        context: Context
    ) {
        (context as Activity).runOnUiThread {
            progressBar = createProgressBar(context)
            progressBar?.show()
        }
    }

    fun close(
        context: Context
    ) {
        (context as Activity).runOnUiThread {
            progressBar?.dismiss()
        }
    }

    fun getProgress(): AlertDialog? = progressBar

    private fun createProgressBar(
        context: Context
    ): AlertDialog? {
        val view =
            LayoutInflater.from(
                context
            ).inflate(
                R.layout.progress_bar,
                null
            )

        return AlertDialog.Builder(
            context
        ).setView(
            view
        ).setCancelable(
            false
        ).create()
    }
}