package com.jhm.android.app_pokusme.global

import android.app.AlertDialog
import android.content.Context

class DialogAlert {
    companion object {
        fun showConfirmDialogAlert(context: Context, title: String, message: String, setClickListener: (() -> Unit)? = null) {
            AlertDialog.Builder(context).run {
                setMessage(message)
                setTitle(title)
                
                setPositiveButton("확인") { _, _ ->
                    setClickListener?.let { it() }
                }
                
                show()
            }
        }
    }
}