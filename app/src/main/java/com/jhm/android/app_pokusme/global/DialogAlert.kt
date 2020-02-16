package com.jhm.android.app_pokusme.global

import android.app.AlertDialog
import android.content.Context

class DialogAlert {
    companion object {
        fun showForcePositiveDialogAlert(context: Context, title: String, message: String, onPositive: (() -> Unit)? = null) {
            AlertDialog.Builder(context).run {
                setMessage(message)
                setTitle(title)
                setCancelable(false)
                setPositiveButton("확인") { _, _ -> onPositive?.let { it() } }
                show()
            }
        }
        
        fun showPositiveDialogAlert(context: Context, title: String, message: String, onPositive: (() -> Unit)? = null) {
            AlertDialog.Builder(context).run {
                setMessage(message)
                setTitle(title)
                setPositiveButton("확인") { _, _ -> onPositive?.let { it() } }
                show()
            }
        }
        
        fun showConfirmOrCancelDialogAlert(
            context: Context,
            title: String,
            message: String,
            onPositive: (() -> Unit)? = null,
            onNegative: (() -> Unit)? = null
        ) {
            AlertDialog.Builder(context).run {
                setMessage(message)
                setTitle(title)
                setPositiveButton("확인") { _, _ -> onPositive?.let { it() } }
                setNegativeButton("취소") { _, _ -> onNegative?.let { it() } }
                show()
            }
        }
    }
}