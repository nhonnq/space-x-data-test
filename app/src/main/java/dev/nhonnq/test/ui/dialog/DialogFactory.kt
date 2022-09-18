package dev.nhonnq.test.ui.dialog

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import dev.nhonnq.test.R

object DialogFactory {

    private var dialog: Dialog? = null

    fun showSimpleDialog(
        context: Context,
        title: String,
        message: String,
        listener: DialogInterface.OnClickListener? = null
    ) {
        val dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(context.getString(R.string.ok), listener)
            .setCancelable(false)
            .create()
        dialog.show()
    }

    fun showSimpleDialog(
        context: Context?,
        title: String,
        message: String,
        positiveButton: Int,
        negativeButton: Int,
        cancelable: Boolean,
        positiveListener: DialogInterface.OnClickListener? = null,
        negativeListener: DialogInterface.OnClickListener? = null
    ) {
        try {
            if (context == null || (context is Activity && context.isFinishing)) return
            if (dialog != null && dialog!!.isShowing) {
                dialog?.cancel()
            }
            if (negativeButton == -1) {
                dialog = AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(context.getString(positiveButton), positiveListener)
                    .setCancelable(cancelable)
                    .create()
            } else {
                dialog = AlertDialog.Builder(context)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(context.getString(positiveButton), positiveListener)
                    .setNegativeButton(context.getString(negativeButton), negativeListener)
                    .setCancelable(cancelable)
                    .create()
            }
            dialog?.show()
        } catch (ex: Exception) {
            ex.stackTrace
        }
    }

}